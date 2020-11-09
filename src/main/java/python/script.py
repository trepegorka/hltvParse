import pickle
import sys
from sklearn.calibration import CalibratedClassifierCV
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline
from sklearn import model_selection
from sklearn import svm
import pandas as pd


def toFixed(numObj, digits=2):
    return f"{numObj:.{digits}f}"


def rebuilder(arraytoDrop):
    df = pd.read_csv("hltv2CSV.csv")

    X = df.drop(arraytoDrop, 1)

    Y = df['winner']

    imputer = SimpleImputer(missing_values=0, strategy='mean')
    imputer.fit(X)
    X = imputer.transform(X)

    X_train, X_test, Y_train, Y_test = model_selection.train_test_split(X, Y, test_size=0.2, random_state=7)
    model = make_pipeline(StandardScaler(), svm.LinearSVC(max_iter=10000000))
    clf = CalibratedClassifierCV(model)
    clf.fit(X_train, Y_train)

    # saving
    model_file = 'final_model_rebuilded.sav'
    pickle.dump(clf, open(model_file, 'wb'))


"""
1 KDRatioAttitude
2 headshotAttitude
3 damagePerRoundAttitude
4 assistsPerRoundAttitude
5 impactAttitude
6 kastAttitude
7 openingKillRatioAttitude
8 rating3mAttitude
"""


def new_model_drops():
    arrayToDrop = ['id', 'ratingVStop5Attitude',
                   'ratingVStop10Attitude', 'ratingVStop20Attitude',
                   'ratingVStop30Attitude', 'ratingVStop50Attitude',
                   'totalKillsAttitude', 'mapsPlayedAttitude',
                   'rankingDifference', 'winner']
    if sys.argv[2] == 0:
        arrayToDrop.append('headshotAttitude')
    if sys.argv[3] == 0:
        arrayToDrop.append('damagePerRoundAttitude')
    if sys.argv[4] == 0:
        arrayToDrop.append('assistsPerRoundAttitude')
    if sys.argv[5] == 0:
        arrayToDrop.append('impactAttitude')
    if sys.argv[6] == 0:
        arrayToDrop.append('kastAttitude')
    if sys.argv[7] == 0:
        arrayToDrop.append('openingKillRatioAttitude')
    if sys.argv[8] == 0:
        arrayToDrop.append('rating3mAttitude')

    rebuilder(arrayToDrop)


# load model
Xnew = [[sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6],
         sys.argv[7], sys.argv[8]]]
if sys.argv[1] and sys.argv[2] and sys.argv[3] and sys.argv[4] and sys.argv[5] and sys.argv[6] and sys.argv[7] and \
        sys.argv[8] != 0:
    loaded_model = pickle.load(open('final_model.sav', 'rb'))
else:
    loaded_model = pickle.load(open('final_model_rebuilded.sav'))
probability = loaded_model.predict_proba(Xnew)
result = loaded_model.predict(Xnew)
arr = probability[0]
print(toFixed(arr[0]), "VS", toFixed(arr[1]))

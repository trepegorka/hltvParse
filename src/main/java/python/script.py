import pickle
import sys
from sklearn.calibration import CalibratedClassifierCV
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline
from sklearn import model_selection
from sklearn import svm
import pandas as pd

x1 = sys.argv[1]
x2 = sys.argv[2]
x3 = sys.argv[3]
x4 = sys.argv[4]
x5 = sys.argv[5]
x6 = sys.argv[6]
x7 = sys.argv[7]
x8 = sys.argv[8]

Xnew = [[x1, x2, x3, x4, x5, x6, x7, x8]]


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


def new_model_drops():
    arrayToDrop = ['id', 'ratingVStop5Attitude',
                   'ratingVStop10Attitude', 'ratingVStop20Attitude',
                   'ratingVStop30Attitude', 'ratingVStop50Attitude',
                   'totalKillsAttitude', 'mapsPlayedAttitude',
                   'rankingDifference', 'winner']
    if x2 == 0.0:
        arrayToDrop.append('headshotAttitude')
        Xnew[0].remove(x2)
    if x3 == 0.0:
        arrayToDrop.append('damagePerRoundAttitude')
        Xnew[0].remove(x3)
    if x4 == 0.0:
        arrayToDrop.append('assistsPerRoundAttitude')
        Xnew[0].remove(x4)
    if x5 == 0.0:
        arrayToDrop.append('impactAttitude')
        Xnew[0].remove(x5)
    if x6 == 0.0:
        arrayToDrop.append('kastAttitude')
        Xnew[0].remove(x6)
    if x7 == 0.0:
        arrayToDrop.append('openingKillRatioAttitude')
        Xnew[0].remove(x7)
    if x8 == 0.0:
        arrayToDrop.append('rating3mAttitude')
        Xnew[0].remove(x8)

    rebuilder(arrayToDrop)


# load model
if x2 != 0.0 and x3 != 0.0 and x4 != 0.0 and x5 != 0.0 and x6 != 0.0 and x7 != 0.0 and x8 != 0.0:
    loaded_model = pickle.load(open('final_model.sav', 'rb'))
else:
    new_model_drops()
    loaded_model = pickle.load(open('final_model_rebuilded.sav', 'rb'))
probability = loaded_model.predict_proba(Xnew)
result = loaded_model.predict(Xnew)
arr = probability[0]
print(toFixed(arr[0]), ":", toFixed(arr[1]))

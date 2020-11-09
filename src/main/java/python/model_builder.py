from sklearn.calibration import CalibratedClassifierCV
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline
from sklearn import model_selection
from sklearn import svm
import pandas as pd
import pickle

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

df = pd.read_csv("hltv2CSV.csv")

X = df.drop(['id', 'ratingVStop5Attitude',
             'ratingVStop10Attitude', 'ratingVStop20Attitude',
             'ratingVStop30Attitude', 'ratingVStop50Attitude',
             'totalKillsAttitude', 'mapsPlayedAttitude',
             'rankingDifference', 'winner'], 1)

Y = df['winner']

imputer = SimpleImputer(missing_values=0, strategy='mean')
imputer.fit(X)
X = imputer.transform(X)

X_train, X_test, Y_train, Y_test = model_selection.train_test_split(X, Y, test_size=0.2, random_state=7)
model = make_pipeline(StandardScaler(), svm.LinearSVC(max_iter=10000000))
clf = CalibratedClassifierCV(model)
clf.fit(X_train, Y_train)

# saving
model_file = 'final_model.sav'
pickle.dump(clf, open(model_file, 'wb'))

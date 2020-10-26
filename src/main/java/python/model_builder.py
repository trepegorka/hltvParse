import pandas as pd
from sklearn.tree import DecisionTreeClassifier  # Import Decision Tree Classifier
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

import pickle

#########
col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
             'impactAttitude',
             'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
             'ratingVStop10Attitude',
             'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
             'mapsPlayedAttitude',
             'rankingDifference', 'winner']

pima = pd.read_csv("hltv2CSV.csv")
pima.columns = col_names
X = pima[['headshotAttitude', 'impactAttitude', 'openingKillRatioAttitude', 'rating3mAttitude',
          'ratingVStop10Attitude', 'ratingVStop20Attitude', 'totalKillsAttitude', 'rankingDifference']]  # Features
Y = pima.winner  # Target variable
X_train, X_test, Y_train, Y_test = train_test_split(X, Y, test_size=0.1, random_state=4)
tree = DecisionTreeClassifier(criterion="entropy", max_depth=7)
tree = tree.fit(X_train, Y_train)
# tests
Y_pred = tree.predict(X_test)
#########

# saving
saved_model = 'final_model.sav'
pickle.dump(tree, open(saved_model, 'wb'))

# load
Xnew = [[1.891, 0.875, 1.223, 1.122, 1.113, 1.171, 1.162, 33]]
load_lr_model = pickle.load(open('final_model.sav', 'rb'))
Ynew = load_lr_model.predict_proba(Xnew)
res = load_lr_model.predict(Xnew)
print(Ynew)
print(res)

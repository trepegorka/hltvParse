from sklearn.calibration import CalibratedClassifierCV
from sklearn.impute import SimpleImputer
from sklearn.preprocessing import StandardScaler
from sklearn.pipeline import make_pipeline
from sklearn import model_selection
from sklearn import svm
import pandas as pd
import pickle

"""
1. KDRatioAttitude', 
2 'headshotAttitude',
3 'damagePerRoundAttitude',
4 'assistsPerRoundAttitude',
5 'impactAttitude', 
6 'kastAttitude',
7 'openingKillRatioAttitude', 
8 'rating3mAttitude', 
9 'ratingVStop5Attitude', 
10 'ratingVStop10Attitude', 
11 'ratingVStop20Attitude', 
12 'ratingVStop30Attitude', 
13 'ratingVStop50Attitude']
"""

df = pd.read_csv("hltv2CSV.csv")

X = df.drop(['id', 'totalKillsAttitude',
             'mapsPlayedAttitude',
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

# feature_cols = ['KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
#                 'impactAttitude',
#                 'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
#                 'ratingVStop10Attitude',
#                 'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
#                 'mapsPlayedAttitude',
#                 'rankingDifference']
#
# arrays = []
#
# for i in range(0, 16):
#     arr = [feature_cols[i]]
#     arrays.append(arr)
#     for j in range(i + 1, 16):
#         arr = [feature_cols[i], feature_cols[j]]
#         arrays.append(arr)
#         for k in range(j + 1, 16):
#             arr = [feature_cols[i], feature_cols[j], feature_cols[k]]
#             arrays.append(arr)
#             for m in range(k + 1, 16):
#                 arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m]]
#                 arrays.append(arr)
#                 for n in range(m + 1, 16):
#                     arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n]]
#                     arrays.append(arr)
#                     for o in range(n + 1, 16):
#                         arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
#                                feature_cols[o]]
#                         arrays.append(arr)
#                         for p in range(o + 1, 16):
#                             arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
#                                    feature_cols[o], feature_cols[p]]
#                             arrays.append(arr)
#                             for r in range(p + 1, 16):
#                                 arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
#                                        feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r]]
#                                 arrays.append(arr)
#                                 for s in range(r + 1, 16):
#                                     arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
#                                            feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
#                                            feature_cols[s]]
#                                     arrays.append(arr)
#                                     for t in range(s + 1, 16):
#                                         arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
#                                                feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
#                                                feature_cols[s], feature_cols[t]]
#                                         arrays.append(arr)
#                                         for a in range(t + 1, 16):
#                                             arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
#                                                    feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
#                                                    feature_cols[s], feature_cols[t], feature_cols[a]]
#                                             arrays.append(arr)
#                                             for b in range(a + 1, 16):
#                                                 arr = [feature_cols[i], feature_cols[j], feature_cols[k],
#                                                        feature_cols[m], feature_cols[n], feature_cols[o],
#                                                        feature_cols[p], feature_cols[r], feature_cols[s],
#                                                        feature_cols[t], feature_cols[a], feature_cols[b]]
#                                                 arrays.append(arr)
#                                                 for c in range(b + 1, 16):
#                                                     arr = [feature_cols[i], feature_cols[j], feature_cols[k],
#                                                            feature_cols[m], feature_cols[n], feature_cols[o],
#                                                            feature_cols[p], feature_cols[r], feature_cols[s],
#                                                            feature_cols[t], feature_cols[a], feature_cols[b],
#                                                            feature_cols[c]]
#                                                     arrays.append(arr)
#                                                     for d in range(c + 1, 16):
#                                                         arr = [feature_cols[i], feature_cols[j], feature_cols[k],
#                                                                feature_cols[m], feature_cols[n], feature_cols[o],
#                                                                feature_cols[p], feature_cols[r], feature_cols[s],
#                                                                feature_cols[t], feature_cols[a], feature_cols[b],
#                                                                feature_cols[c], feature_cols[d]]
#                                                         arrays.append(arr)
#                                                         for e in range(d + 1, 16):
#                                                             arr = [feature_cols[i], feature_cols[j], feature_cols[k],
#                                                                    feature_cols[m], feature_cols[n], feature_cols[o],
#                                                                    feature_cols[p], feature_cols[r], feature_cols[s],
#                                                                    feature_cols[t], feature_cols[a], feature_cols[b],
#                                                                    feature_cols[c], feature_cols[d], feature_cols[e]]
#                                                             arrays.append(arr)
#                                                             for f in range(e + 1, 16):
#                                                                 arr = [feature_cols[i], feature_cols[j],
#                                                                        feature_cols[k], feature_cols[m],
#                                                                        feature_cols[n], feature_cols[o],
#                                                                        feature_cols[p], feature_cols[r],
#                                                                        feature_cols[s], feature_cols[t],
#                                                                        feature_cols[a], feature_cols[b],
#                                                                        feature_cols[c], feature_cols[d],
#                                                                        feature_cols[e], feature_cols[f]]
#                                                                 arrays.append(arr)
#
# for array in arrays:
#     if len(array) < 6:
#         arrays.remove(array)
#
# print(arrays[9])

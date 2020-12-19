import pandas as pd
from sklearn import preprocessing
from sklearn import metrics
from sklearn.calibration import CalibratedClassifierCV
from sklearn.ensemble import ExtraTreesClassifier
from sklearn.feature_selection import RFE
from sklearn import model_selection
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression
from sklearn.impute import SimpleImputer
from sklearn.pipeline import make_pipeline

col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
             'impactAttitude',
             'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
             'ratingVStop10Attitude',
             'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
             'mapsPlayedAttitude',
             'rankingDifference', 'mapPicker', 'winner']

feature_cols = ['KDRatioAttitude',
                'headshotAttitude',
                'damagePerRoundAttitude',
                'assistsPerRoundAttitude',
                'impactAttitude',
                'kastAttitude',
                'openingKillRatioAttitude',
                'rating3mAttitude',
                'ratingVStop5Attitude',
                'ratingVStop10Attitude',
                'ratingVStop20Attitude',
                'ratingVStop30Attitude',
                'ratingVStop50Attitude',
                'totalKillsAttitude',
                'mapsPlayedAttitude',
                'rankingDifference',
                'mapPicker']

arrays = []

for i in range(0, 17):
    arr = [feature_cols[i]]
    arrays.append(arr)
    for j in range(i + 1, 17):
        arr = [feature_cols[i], feature_cols[j]]
        arrays.append(arr)
        for k in range(j + 1, 17):
            arr = [feature_cols[i], feature_cols[j], feature_cols[k]]
            arrays.append(arr)
            for m in range(k + 1, 17):
                arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m]]
                arrays.append(arr)
                for n in range(m + 1, 17):
                    arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n]]
                    arrays.append(arr)
                    for o in range(n + 1, 17):
                        arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
                               feature_cols[o]]
                        arrays.append(arr)
                        for p in range(o + 1, 17):
                            arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
                                   feature_cols[o], feature_cols[p]]
                            arrays.append(arr)
                            for r in range(p + 1, 17):
                                arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                       feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r]]
                                arrays.append(arr)
                                for s in range(r + 1, 17):
                                    arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                           feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                           feature_cols[s]]
                                    arrays.append(arr)
                                    for t in range(s + 1, 17):
                                        arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                               feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                               feature_cols[s], feature_cols[t]]
                                        arrays.append(arr)
                                        for a in range(t + 1, 17):
                                            arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                                   feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                                   feature_cols[s], feature_cols[t], feature_cols[a]]
                                            arrays.append(arr)
                                            for b in range(a + 1, 17):
                                                arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                       feature_cols[m], feature_cols[n], feature_cols[o],
                                                       feature_cols[p], feature_cols[r], feature_cols[s],
                                                       feature_cols[t], feature_cols[a], feature_cols[b]]
                                                arrays.append(arr)
                                                for c in range(b + 1, 17):
                                                    arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                           feature_cols[m], feature_cols[n], feature_cols[o],
                                                           feature_cols[p], feature_cols[r], feature_cols[s],
                                                           feature_cols[t], feature_cols[a], feature_cols[b],
                                                           feature_cols[c]]
                                                    arrays.append(arr)
                                                    for d in range(c + 1, 17):
                                                        arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                               feature_cols[m], feature_cols[n], feature_cols[o],
                                                               feature_cols[p], feature_cols[r], feature_cols[s],
                                                               feature_cols[t], feature_cols[a], feature_cols[b],
                                                               feature_cols[c], feature_cols[d]]
                                                        arrays.append(arr)
                                                        for e in range(d + 1, 17):
                                                            arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                                   feature_cols[m], feature_cols[n], feature_cols[o],
                                                                   feature_cols[p], feature_cols[r], feature_cols[s],
                                                                   feature_cols[t], feature_cols[a], feature_cols[b],
                                                                   feature_cols[c], feature_cols[d], feature_cols[e]]
                                                            arrays.append(arr)
                                                            for f in range(e + 1, 17):
                                                                arr = [feature_cols[i], feature_cols[j],
                                                                       feature_cols[k], feature_cols[m],
                                                                       feature_cols[n], feature_cols[o],
                                                                       feature_cols[p], feature_cols[r],
                                                                       feature_cols[s], feature_cols[t],
                                                                       feature_cols[a], feature_cols[b],
                                                                       feature_cols[c], feature_cols[d],
                                                                       feature_cols[e], feature_cols[f]]
                                                                arrays.append(arr)
                                                                for q in range(f + 1, 17):
                                                                    arr = [feature_cols[i], feature_cols[j],
                                                                           feature_cols[k], feature_cols[m],
                                                                           feature_cols[n], feature_cols[o],
                                                                           feature_cols[p], feature_cols[r],
                                                                           feature_cols[s], feature_cols[t],
                                                                           feature_cols[a], feature_cols[b],
                                                                           feature_cols[c], feature_cols[d],
                                                                           feature_cols[e], feature_cols[f],
                                                                           feature_cols[q]]
                                                                arrays.append(arr)

pima = pd.read_csv("hltv3Map.csv")
pima.columns = col_names
X = pima[feature_cols]
y = pima.winner

pima2 = pd.read_csv("hltv2Map.csv")
pima2.columns = col_names
X_test = pima2[feature_cols]
y_test = pima2.winner

# normalize the data attributes
normalized_X = preprocessing.normalize(X)
# standardize the data attributes
standardized_X = preprocessing.scale(X)


# # оценка входных столбцов
# model = ExtraTreesClassifier()
# model.fit(X, y)
# # display the relative importance of each attribute
# print(model.feature_importances_)


# переборка. ХЗ как работает
# model = LogisticRegression()
# # create the RFE model and select 3 attributes
# rfe = RFE(model, 3)
# rfe = rfe.fit(X, y)
# # summarize the selection of the attributes
# print(rfe.support_)
# print(rfe.ranking_)

model = LogisticRegression(max_iter=1000000)

model.fit(X, y)
# make predictions
expected = y_test
predicted = model.predict(X_test)
# summarize the fit of the model
print(metrics.classification_report(expected, predicted))
print(metrics.confusion_matrix(expected, predicted))
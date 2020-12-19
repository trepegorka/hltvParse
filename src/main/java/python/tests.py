import pandas as pd
from sklearn.impute import SimpleImputer
from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn import preprocessing
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn import svm
from sklearn.svm import SVC

col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
             'impactAttitude',
             'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
             'ratingVStop10Attitude',
             'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
             'mapsPlayedAttitude',
             'rankingDifference', 'mapPick', 'winner']

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
                'mapPick']

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


def count_linearsvc(array, random_st):
    pima = pd.read_csv("hltv2MAPTEST.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    imputer = SimpleImputer(missing_values=0, strategy='mean')
    imputer.fit(X)
    X = imputer.transform(X)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    # linearSVC = make_pipeline(StandardScaler(), svm.LinearSVC(max_iter=10000000))
    linearSVC = svm.LinearSVC(max_iter=10000000)

    linearSVC.fit(X_train, y_train)

    pred = linearSVC.predict(X_test)

    return accuracy_score(y_test, pred)


def searcherForest(array):
    pima = pd.read_csv("hltv2.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner
    clf = RandomForestClassifier()
    # X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=3)
    parametrs = {
        # 'n_estimators': range(1, 200, 1),
        # 'criterion': ["gini", "entropy"],
        # 'max_depth': range(1, 30, 1),
        # 'min_samples_leaf': range(1, 50, 1),
        # 'min_samples_split': range(1, 50, 1),

        # //5
        'n_estimators': range(1, 40, 1),
        'criterion': ["gini", "entropy"],
        'max_depth': range(1, 6, 1),
        'min_samples_leaf': range(1, 10, 1),
        'min_samples_split': range(2, 10, 1),
    }
    grid = GridSearchCV(clf, parametrs, cv=5)
    grid.fit(X, y)
    return grid.best_params_


def searcherSVC(array):
    pima = pd.read_csv("hltv2.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=41)
    parametrs = [
        {'C': [1, 10, 100, 1000], 'kernel': ['linear', 'rbf', 'sigmoid'],
         'gamma': [1, 0.1, 0.01, 0.001, 0.0001], 'coef0': range(1, 50, 1), 'tol': range(1, 50, 1)}
        ,
        {'C': [1, 10, 100, 1000], 'kernel': ['poly'], 'degree': range(1, 50, 1),
         'gamma': [1, 0.1, 0.01, 0.001, 0.0001], 'coef0': range(1, 50, 1), 'tol': range(1, 50, 1)}
    ]

    grid = GridSearchCV(SVC(), parametrs, refit=True, verbose=2)
    grid.fit(X_train, y_train)
    return grid.best_params_

# arrays[17]
def main():
    file = open('modelmax.txt', 'a')

    for arr_n in range(15, len(arrays)):
        print("arr=", arr_n, sep=" ")
        average_acc1 = 0.0
        for r_state in range(1, 10):
            average_acc1 = average_acc1 + count_linearsvc(arrays[arr_n], r_state)
        average_acc1 = average_acc1 / 9
        file.write(str(average_acc1) + '\tlinearsvc ' + '\tn_arr: ' + str(arr_n) + '\n')
        file.flush()
    file.close()

main()

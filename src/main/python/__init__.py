import csv

import pandas as pd
from sklearn.naive_bayes import GaussianNB
from sklearn.neighbors import KNeighborsClassifier
from sklearn.tree import DecisionTreeClassifier  # Import Decision Tree Classifier
from sklearn.model_selection import train_test_split, GridSearchCV  # Import train_test_split function
from sklearn import metrics  # Import scikit-learn metrics module for accuracy calculation
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split

feature_cols = [
    'KDRatioAttitude',
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
    'rankingDifference'
]

arrays = []

for i in range(0, 16):
    arr = [feature_cols[i]]
    arrays.append(arr)
    for j in range(i + 1, 16):
        arr = [feature_cols[i], feature_cols[j]]
        arrays.append(arr)
        for k in range(j + 1, 16):
            arr = [feature_cols[i], feature_cols[j], feature_cols[k]]
            arrays.append(arr)
            for m in range(k + 1, 16):
                arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m]]
                arrays.append(arr)
                for n in range(m + 1, 16):
                    arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n]]
                    arrays.append(arr)
                    for o in range(n + 1, 16):
                        arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
                               feature_cols[o]]
                        arrays.append(arr)
                        for p in range(o + 1, 16):
                            arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m], feature_cols[n],
                                   feature_cols[o], feature_cols[p]]
                            arrays.append(arr)
                            for r in range(p + 1, 16):
                                arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                       feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r]]
                                arrays.append(arr)
                                for s in range(r + 1, 16):
                                    arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                           feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                           feature_cols[s]]
                                    arrays.append(arr)
                                    for t in range(s + 1, 16):
                                        arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                               feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                               feature_cols[s], feature_cols[t]]
                                        arrays.append(arr)
                                        for a in range(t + 1, 16):
                                            arr = [feature_cols[i], feature_cols[j], feature_cols[k], feature_cols[m],
                                                   feature_cols[n], feature_cols[o], feature_cols[p], feature_cols[r],
                                                   feature_cols[s], feature_cols[t], feature_cols[a]]
                                            arrays.append(arr)
                                            for b in range(a + 1, 16):
                                                arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                       feature_cols[m], feature_cols[n], feature_cols[o],
                                                       feature_cols[p], feature_cols[r], feature_cols[s],
                                                       feature_cols[t], feature_cols[a], feature_cols[b]]
                                                arrays.append(arr)
                                                for c in range(b + 1, 16):
                                                    arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                           feature_cols[m], feature_cols[n], feature_cols[o],
                                                           feature_cols[p], feature_cols[r], feature_cols[s],
                                                           feature_cols[t], feature_cols[a], feature_cols[b],
                                                           feature_cols[c]]
                                                    arrays.append(arr)
                                                    for d in range(c + 1, 16):
                                                        arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                               feature_cols[m], feature_cols[n], feature_cols[o],
                                                               feature_cols[p], feature_cols[r], feature_cols[s],
                                                               feature_cols[t], feature_cols[a], feature_cols[b],
                                                               feature_cols[c], feature_cols[d]]
                                                        arrays.append(arr)
                                                        for e in range(d + 1, 16):
                                                            arr = [feature_cols[i], feature_cols[j], feature_cols[k],
                                                                   feature_cols[m], feature_cols[n], feature_cols[o],
                                                                   feature_cols[p], feature_cols[r], feature_cols[s],
                                                                   feature_cols[t], feature_cols[a], feature_cols[b],
                                                                   feature_cols[c], feature_cols[d], feature_cols[e]]
                                                            arrays.append(arr)
                                                            for f in range(e + 1, 16):
                                                                arr = [feature_cols[i], feature_cols[j],
                                                                       feature_cols[k], feature_cols[m],
                                                                       feature_cols[n], feature_cols[o],
                                                                       feature_cols[p], feature_cols[r],
                                                                       feature_cols[s], feature_cols[t],
                                                                       feature_cols[a], feature_cols[b],
                                                                       feature_cols[c], feature_cols[d],
                                                                       feature_cols[e], feature_cols[f]]
                                                                arrays.append(arr)


def count_tree(array, depth):
    col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
                 'impactAttitude',
                 'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
                 'ratingVStop10Attitude',
                 'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
                 'mapsPlayedAttitude',
                 'rankingDifference', 'winner']
    # read file
    pima = pd.read_csv("hltvCSV.csv")
    pima.columns = col_names

    # столбцы для x это array

    # внедрение
    X = pima[array]  # Features
    y = pima.winner  # Target variable

    # Split dataset into training set and test set
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=1)

    # Create Decision Tree classifer object а также создание knn объекта модели
    tree = DecisionTreeClassifier()

    # 72%
    #tree = DecisionTreeClassifier(criterion="entropy", max_depth=depth)

    # тренировка модели
    tree = tree.fit(X_train, y_train)

    # тесты
    y_pred = tree.predict(X_test)

    return "Accuracy tree:", metrics.accuracy_score(y_test, y_pred)


def count_gnb(array):
    col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
                 'impactAttitude',
                 'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
                 'ratingVStop10Attitude',
                 'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
                 'mapsPlayedAttitude',
                 'rankingDifference', 'winner']
    # read file
    pima = pd.read_csv("hltvCSV.csv")
    pima.columns = col_names

    # столбцы для x это array

    # внедрение
    X = pima[array]  # Features
    y = pima.winner  # Target variable

    # Split dataset into training set and test set
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=1)

    # Create Decision Tree classifer object а также создание knn объекта модели
    gnb = GaussianNB()


    #tree = DecisionTreeClassifier(criterion="entropy", max_depth=depth)

    # тренировка модели
    gnb = gnb.fit(X_train, y_train)

    # тесты
    y_pred = gnb.predict(X_test)

    return "Accuracy tree:", metrics.accuracy_score(y_test, y_pred)

def count_knn(array, neighbors):
    col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
                 'impactAttitude',
                 'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
                 'ratingVStop10Attitude',
                 'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
                 'mapsPlayedAttitude',
                 'rankingDifference', 'winner']
    # read file
    pima = pd.read_csv("hltvCSV.csv")
    pima.columns = col_names

    # столбцы для x это array

    # внедрение
    X = pima[array]  # Features
    y = pima.winner  # Target variable

    # Split dataset into training set and test set
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.25, random_state=2)

    # Create Decision Tree classifer object а также создание knn объекта модели

    # 68%
    knn = KNeighborsClassifier(n_neighbors=neighbors)

    # тренировка моделей
    knn.fit(X_train, y_train)

    # тесты
    knn_pred = knn.predict(X_test)
    return "Accuracy knn:", accuracy_score(y_test, knn_pred)


def main():
    # ['headshotAttitude', 'assistsPerRoundAttitude', 'impactAttitude', 'openingKillRatioAttitude', 'ratingVStop5Attitude', 'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude', 'mapsPlayedAttitude', 'rankingDifference']
    accuracy_array = [];
    n_operation = 1;
    # for n_depth in range(1, 16):
    for mas in range(0, len(arrays)):
        accuracy_array.append(count_gnb(arrays[mas]))
        print('Operation №', n_operation)
        n_operation = n_operation+1


    # Initialize max with first element of array.
    max = accuracy_array[0];
    ibd=0
    for ib in range(0, len(accuracy_array)):
        if (accuracy_array[ib] > max):
            max = accuracy_array[ib];
            ibd = ib


    print("Largest element present in given array: " + str(max));
    print(ibd)


main()

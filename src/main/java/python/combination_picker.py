import pandas as pd
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.neighbors import KNeighborsClassifier
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn.ensemble import RandomForestClassifier
from sklearn import svm
from sklearn.tree import DecisionTreeClassifier
from sklearn.svm import SVC
from sklearn.ensemble import VotingClassifier


col_names = ['id', 'KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
             'impactAttitude',
             'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
             'ratingVStop10Attitude',
             'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
             'mapsPlayedAttitude',
             'rankingDifference', 'winner']

feature_cols = ['KDRatioAttitude', 'headshotAttitude', 'damagePerRoundAttitude', 'assistsPerRoundAttitude',
                'impactAttitude',
                'kastAttitude', 'openingKillRatioAttitude', 'rating3mAttitude', 'ratingVStop5Attitude',
                'ratingVStop10Attitude',
                'ratingVStop20Attitude', 'ratingVStop30Attitude', 'ratingVStop50Attitude', 'totalKillsAttitude',
                'mapsPlayedAttitude',
                'rankingDifference']

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


def count_svc(array, random_st):
    pima = pd.read_csv("hltv2.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    imputer = SimpleImputer(missing_values=0, strategy='mean')
    imputer.fit(X)
    X = imputer.transform(X)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    svc = make_pipeline(StandardScaler(), svm.SVC(max_iter=10000000))

    svc.fit(X_train, y_train)

    pred = svc.predict(X_test)

    return accuracy_score(y_test, pred)


def count_linearsvc(array, random_st):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    linearSVC = make_pipeline(StandardScaler(), svm.LinearSVC(max_iter=10000000))

    linearSVC.fit(X_train, y_train)

    pred = linearSVC.predict(X_test)

    return accuracy_score(y_test, pred)


def count_knn(array, random_st, neighbors, weight):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    knn = KNeighborsClassifier(n_neighbors=neighbors, weights=weight)

    knn.fit(X_train, y_train)
    knn_pred = knn.predict(X_test)

    return accuracy_score(y_test, knn_pred)


def count_pipe_knn(array, random_st, neighbors, weight):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    knn = make_pipeline(StandardScaler(), KNeighborsClassifier(n_neighbors=neighbors, weights=weight))

    knn.fit(X_train, y_train)
    knn_pred = knn.predict(X_test)

    return accuracy_score(y_test, knn_pred)


def count_ensemble_forest_pipe(array, random_st, estimators):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    ensemble = make_pipeline(StandardScaler(), RandomForestClassifier(estimators))

    ensemble.fit(X_train, y_train)
    ensemble_pred = ensemble.predict(X_test)

    return accuracy_score(y_test, ensemble_pred)


def count_ensemble_forest(array, random_st, estimators):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    ensemble = RandomForestClassifier(estimators)

    ensemble.fit(X_train, y_train)
    ensemble_pred = ensemble.predict(X_test)

    return accuracy_score(y_test, ensemble_pred)


def count_pipe_ensemble_voting(array, random_st, neighbors, depth):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    tree = DecisionTreeClassifier(max_depth=depth)
    knn = KNeighborsClassifier(n_neighbors=neighbors)
    svc = SVC(kernel='rbf', probability=True)
    voting = VotingClassifier(estimators=[('dt', tree), ('knn', knn), ('svc', svc)],
                              voting='soft')

    tree.fit(X_train, y_train)
    knn.fit(X_train, y_train)
    svc.fit(X_train, y_train)

    voting.fit(X_train, y_train)
    ensemble_pred = voting.predict(X_test)

    return accuracy_score(y_test, ensemble_pred)


def count_pipe_ensemble_voting_pipe(array, random_st, neighbors, depth):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    tree = make_pipeline(StandardScaler(), DecisionTreeClassifier(max_depth=depth))
    knn = make_pipeline(StandardScaler(), KNeighborsClassifier(n_neighbors=neighbors))
    svc = make_pipeline(StandardScaler(), SVC(kernel='rbf', probability=True))
    voting = make_pipeline(StandardScaler(), VotingClassifier(estimators=[('dt', tree), ('knn', knn), ('svc', svc)],
                                                              voting='soft'))

    tree.fit(X_train, y_train)
    knn.fit(X_train, y_train)
    svc.fit(X_train, y_train)

    voting.fit(X_train, y_train)
    ensemble_pred = voting.predict(X_test)

    return accuracy_score(y_test, ensemble_pred)


def main():
    file = open('modelmax.txt', 'a')

    for arr_n in range(0, len(arrays)):
        # count_svc and count_linearsvc
        print("arr=", arr_n, sep=" ")
        average_acc = 0.0
        average_acc1 = 0.0
        for r_state in range(1, 10):
            average_acc = average_acc + count_svc(arrays[arr_n], r_state)
            average_acc1 = average_acc1 + count_linearsvc(arrays[arr_n], r_state)
        average_acc = average_acc / 9
        average_acc1 = average_acc1 / 9
        file.write(str(average_acc) + '\tsvc ' + '\tn_arr: ' + str(arr_n) + '\n')
        file.write(str(average_acc1) + '\tlinearsvc ' + '\tn_arr: ' + str(arr_n) + '\n')
        file.flush()

        # knn and pipe knn
        for neighbors in range(1, 10):
            average_acc2 = 0.0
            average_acc3 = 0.0
            average_acc4 = 0.0
            average_acc5 = 0.0
            for r_state in range(1, 10):
                average_acc2 = average_acc2 + count_knn(arrays[arr_n], r_state, neighbors, 'uniform')
                average_acc3 = average_acc3 + count_knn(arrays[arr_n], r_state, neighbors, 'distance')
                average_acc4 = average_acc4 + count_pipe_knn(arrays[arr_n], r_state, neighbors, 'uniform')
                average_acc5 = average_acc5 + count_pipe_knn(arrays[arr_n], r_state, neighbors, 'distance')
            average_acc2 = average_acc2 / 9
            average_acc3 = average_acc3 / 9
            average_acc4 = average_acc4 / 9
            average_acc5 = average_acc5 / 9
            file.write(str(average_acc2) + '\tknn' + '\tn_arr: ' + str(arr_n) + '\tneighbors = ' + str(neighbors)
                       + '\tuniform' + '\n')
            file.write(str(average_acc3) + '\tknn' + '\tn_arr: ' + str(arr_n) + '\tneighbors = ' + str(neighbors)
                       + '\tdistance' + '\n')
            file.write(str(average_acc4) + '\tpipe_knn' + '\tn_arr: ' + str(arr_n) + '\tneighbors = ' + str(neighbors)
                       + '\tuniform' + '\n')
            file.write(str(average_acc5) + '\tpipe_knn' + '\tn_arr: ' + str(arr_n) + '\tneighbors = ' + str(neighbors)
                       + '\tdistance' + '\n')
            file.flush()

        # forest and forest pipe
        for estimator in range(1, 20):
            average_acc6 = 0.0
            average_acc7 = 0.0
            for r_state in range(1, 10):
                average_acc6 = average_acc6 + count_ensemble_forest(arrays[arr_n], r_state, estimator)
                average_acc7 = average_acc7 + count_ensemble_forest_pipe(arrays[arr_n], r_state, estimator)
            average_acc6 = average_acc6 / 9
            average_acc7 = average_acc7 / 9
            file.write(
                str(average_acc6) + '\tforest' + '\tn_arr: ' + str(arr_n) + '\testimator = ' + str(estimator) + '\n')
            file.write(
                str(average_acc7) + '\tforest_pipe' + '\tn_arr: ' + str(arr_n) + '\testimator = ' + str(
                    estimator) + '\n')
            file.flush()

        # # voting and voting pipe
        # for dep in range(3, 8):
        #     for neib in range(2, 10):
        #         average_acc8 = 0.0
        #         average_acc9 = 0.0
        #         for r_state in range(1, 10):
        #             average_acc8 = average_acc8 + count_pipe_ensemble_voting(arrays[arr_n], r_state, neib, dep)
        #             average_acc9 = average_acc9 + count_pipe_ensemble_voting_pipe(arrays[arr_n], r_state, neib, dep)
        #         average_acc8 = average_acc8 / 9
        #         average_acc9 = average_acc9 / 9
        #         file.write(str(average_acc8) + '\tvoting' + '\tn_arr: ' + str(arr_n) + '\tneibghors = ' + str(
        #             neib) + '\tdepth =' + str(dep) + '\n')
        #         file.write(str(average_acc9) + '\tvoting_pipe' + '\tn_arr: ' + str(arr_n) + '\tneibghors = ' + str(
        #             neib) + '\tdepth =' + str(dep) + '\n')
    file.close()

# main()
print(arrays[102])

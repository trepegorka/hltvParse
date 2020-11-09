import pandas as pd
from sklearn.impute import SimpleImputer
from sklearn.metrics import accuracy_score
from sklearn.model_selection import train_test_split
from sklearn.pipeline import make_pipeline
from sklearn.preprocessing import StandardScaler
from sklearn import svm

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

def count_linearsvc(array, random_st):
    pima = pd.read_csv("hltv2CSV.csv")
    pima.columns = col_names

    X = pima[array]
    y = pima.winner

    imputer = SimpleImputer(missing_values=0, strategy='mean')
    imputer.fit(X)
    X = imputer.transform(X)

    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2, random_state=random_st)

    linearSVC = make_pipeline(StandardScaler(), svm.LinearSVC(max_iter=10000000))

    linearSVC.fit(X_train, y_train)

    pred = linearSVC.predict(X_test)

    return accuracy_score(y_test, pred)


def main():
    file = open('modelmax.txt', 'a')

    for arr_n in range(7, 8):
        print("arr=", arr_n, sep=" ")
        average_acc1 = 0.0
        for r_state in range(1, 10):
            average_acc1 = average_acc1 + count_linearsvc(arrays[arr_n], r_state)
        average_acc1 = average_acc1 / 9
        file.write(str(average_acc1) + '\tlinearsvc ' + '\tn_arr: ' + str(arr_n) + '\n')
        file.flush()

    file.close()


main()
# a = 0
# for arr in arrays:
#     if arr == ['KDRatioAttitude',
#                'headshotAttitude',
#                'damagePerRoundAttitude',
#                'assistsPerRoundAttitude',
#                'impactAttitude',
#                'kastAttitude',
#                'openingKillRatioAttitude',
#                'rating3mAttitude']:
#         print(a)
#     a = a + 1

# print(arrays[7])
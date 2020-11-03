import pickle
import sys

"""
1. KDRatioAttitude 
2. headshotAttitude 
3. damagePerRoundAttitude
4. assistsPerRoundAttitude
5. impactAttitude
6. kastAttitude
7. openingKillRatioAttitude
8. rating3mAttitude
9. ratingVStop5Attitude
10. ratingVStop20Attitude
11. mapsPlayedAttitude

Output like : [[0.33 0.67]] , 1 = right team won? 
"""
# load model
Xnew = [[sys.argv[1], sys.argv[2], sys.argv[3], sys.argv[4], sys.argv[5], sys.argv[6],
         sys.argv[7], sys.argv[8], sys.argv[9], sys.argv[10], sys.argv[11]]]
loaded_model = pickle.load(open('final_model.sav', 'rb'))
probability = loaded_model.predict_proba(Xnew)
result = loaded_model.predict(Xnew)
if result == [1]:
    result = sys.argv[13]
elif result == [2]:
    result = sys.argv[12]
print(sys.argv[12], "\tVS\t", sys.argv[13], "\nMap: ", sys.argv[14], "\nProbability: ", probability, "\nResult: ",
      result)

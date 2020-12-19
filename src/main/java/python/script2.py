import pickle
import sys
import numpy as np

x1 = sys.argv[1]
x2 = sys.argv[2]
x3 = sys.argv[3]
x4 = sys.argv[4]
x5 = sys.argv[5]
x6 = sys.argv[6]
x7 = sys.argv[7]
x8 = sys.argv[8]
x9 = sys.argv[9]
x10 = sys.argv[10]
x11 = sys.argv[11]
x12 = sys.argv[12]
x13 = sys.argv[13]
x14 = sys.argv[14]
x15 = sys.argv[15]
x16 = sys.argv[16]
x17 = sys.argv[17]

Xnew = [[x1, x2, x3, x4, x5, x6, x7, x8, x9, x10, x11, x12, x13, x14, x15, x16, x17]]


def toFixed(numObj, digits=2):
    return f"{numObj:.{digits}f}"


# load model
loaded_model = pickle.load(open('final_model.sav', 'rb'))
probability = loaded_model.predict_proba(Xnew)
# result = loaded_model.predict(Xnew)
arr = probability[0]
print(toFixed(arr[0]), ":", toFixed(arr[1]))

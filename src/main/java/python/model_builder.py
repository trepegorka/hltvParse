import pandas as pd
import pickle
from sklearn.preprocessing import StandardScaler
from sklearn.calibration import CalibratedClassifierCV
from sklearn.impute import SimpleImputer
from sklearn.linear_model import LogisticRegression
from sklearn.pipeline import make_pipeline

df = pd.read_csv("hltv2.csv")

X = df.drop(['id', 'winner'], 1)

y = df['winner']

imputer = SimpleImputer(missing_values=0, strategy='mean')
imputer.fit(X)
X = imputer.transform(X)

model = make_pipeline(StandardScaler(), LogisticRegression(max_iter=1000000))
clf = CalibratedClassifierCV(model)
clf.fit(X, y)

# saving
model_file = 'final_model.sav'
pickle.dump(clf, open(model_file, 'wb'))



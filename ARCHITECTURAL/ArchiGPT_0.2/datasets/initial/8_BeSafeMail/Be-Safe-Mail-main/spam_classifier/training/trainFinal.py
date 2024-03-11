import pandas as pd

data = pd.read_csv('spam.csv', encoding='latin-1')
labels = data['v1'].values
texts = data['v2'].values


#from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB

# #app = Flask(__name__)

# Caricamento del modello preaddestrato
vectorizer = CountVectorizer()
classifier = MultinomialNB()



# texts = [
#         "Congratulations! You've won a free trip to Hawaii.",
#         "Hi, just wanted to remind you about our meeting tomorrow.",
#         "Click this link to buy the latest gadgets at discounted prices.",
#         "Don't forget to submit your report by the end of the day.",
#     ]

# labels = ["spam", "non-spam", "spam", "non-spam"]

# Vettorizzazione del testo
X = vectorizer.fit_transform(texts)

# Addestramento del classificatore
classifier.fit(X, labels)

X_new = vectorizer.transform(["Congratulations!!!!"])
prediction = classifier.predict(X_new)[0]
probabilities = classifier.predict_proba(X_new)[0]


# Restituisci la classificazione come risposta JSON
response = {
        'classification': prediction,
        'spam_probability': probabilities[1],  # Probabilità di essere spam
    }
print(response)
import joblib

# Salva il modello addestrato come file joblib
joblib.dump(classifier, 'spam_classifier_model.joblib')

# Salva anche il vettorizzatore
joblib.dump(vectorizer.vocabulary_, 'spam_classifier_vectorizer.joblib')

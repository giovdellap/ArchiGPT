from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
import joblib

text="Congrats! you have won a telephone! click here"

# # Carica il modello addestrato
model = joblib.load('../spam_classifier_model.joblib')
vectorizer = CountVectorizer(vocabulary=joblib.load('../spam_classifier_vectorizer.joblib'))


X_new = vectorizer.transform([text])
prediction = model.predict(X_new)[0]
probabilities = model.predict_proba(X_new)[0]

# Restituisci la classificazione come risposta JSON
response = {
        'classification': prediction,
        'spam_probability': probabilities[1],  # Probabilità di essere spam
    }
print(response)
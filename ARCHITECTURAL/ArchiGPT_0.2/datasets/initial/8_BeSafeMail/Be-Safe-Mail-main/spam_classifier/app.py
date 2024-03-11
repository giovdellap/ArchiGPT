from flask import Flask, request, jsonify
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.naive_bayes import MultinomialNB
import joblib

app = Flask(__name__)

# # Carica il modello addestrato
model = joblib.load('spam_classifier_model.joblib')
vectorizer = CountVectorizer(vocabulary=joblib.load('spam_classifier_vectorizer.joblib'))


@app.route('/')
def hello():
    return 'Hello'


# Route per la classificazione dello spam
@app.route('/classify', methods=['POST'])
def classify_spam():
    # Ottieni il testo dalla richiesta POST
    # text = request.form.get('text')
    # print(text)
    data = request.json
    text = data.get('text')

    # Verifica se il testo è spam o non spam
    X_new = vectorizer.transform([text])
    prediction = model.predict(X_new)[0]
    probabilities = model.predict_proba(X_new)[0]

    # Restituisci la classificazione come risposta JSON
    response = {
            'classification': prediction,
            'spam_probability': probabilities[1],  # Probabilità di essere spam
        }
    #Restituisci la classificazione come risposta JSON
    return jsonify(response)
    # return "ritorna" + text

if __name__ == '__main__':
    # Avvia il server Flask
    app.run(host='0.0.0.0',port='8082',debug=True)

# Usa l'immagine base di Python
FROM python:3.9

# Imposta la directory di lavoro nel container
WORKDIR /app

# Copia i file del codice dell'applicazione nella directory di lavoro del container
COPY app.py /app/app.py
COPY spam_classifier_model.joblib /app/spam_classifier_model.joblib
COPY spam_classifier_vectorizer.joblib /app/spam_classifier_vectorizer.joblib
COPY requirements.txt /app/requirements.txt

# Installa le dipendenze dell'applicazione
RUN pip install -r requirements.txt

# Espone la porta su cui l'applicazione Flask è in ascolto (assicurati di utilizzare la stessa porta specificata nel tuo codice Flask)
EXPOSE 8082

# Comando di avvio dell'applicazione
CMD ["python", "app.py"]

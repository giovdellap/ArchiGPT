from flask import Blueprint, render_template, redirect, url_for, request, flash
from flask_login import login_required, current_user
from .models import RiskEmail2
from . import db
import re
import pika
import json
import requests


from . import db

main = Blueprint('main', __name__)


@main.route('/')
def index():
    return render_template('index.html')


@main.route('/profile')
@login_required
def profile():
    
    name = current_user.name  # test
    # emails=[]
    # risks = RiskEmail2.query.all()
    # for risk in risks:
    #     emails.append(risk)
    

    
    return render_template('profile.html', name=current_user.name)



@main.route('/chat', methods=['POST'])
@login_required
def sendToChatAdmin():
    recipient = current_user.name
    text = request.form.get('text')

    # Connessione al broker RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda persistente
    channel.queue_declare(queue='chat', durable=True)

    # Invio di un messaggio alla coda persistente
    message = json.dumps([recipient, text]).encode('utf-8')

    channel.basic_publish(exchange='',
                        routing_key='chat',
                        body=message,
                        properties=pika.BasicProperties(delivery_mode=2))

    # Chiusura della connessione
    connection.close()

    #return redirect(url_for('main.profile'))
    return render_template('profile.html', name=current_user.name)




@main.route('/x', methods=['POST'])
@login_required
def sendEmail():
    recipient = current_user.name
    text = request.form.get('text')
    sender = request.form.get('sender')
    score = 0

    new_riskEmail = RiskEmail2(recipient=recipient, text=text, sender=sender, score=score)

    db.session.add(new_riskEmail)
    db.session.commit()
    ###
    # Connessione al broker RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda persistente
    channel.queue_declare(queue='em', durable=True)

    # Define a regular expression pattern to match URLs
    url_pattern = re.compile(r'https?://(?:[-\w.]|(?:%[\da-fA-F]{2}))+')

    # Use the findall method to extract all URLs from the string
    urls = url_pattern.findall(text)
    if(len(urls)>0):
        data_url = {
            'urls': urls
        }
        url_response=requests.post(url='http://url_load_balancer/url/',json=data_url)
        max_value=0
        if(url_response.status_code==200):
            print("Ok")
            scores = url_response.json()["score"]
            max_value=scores
        
    else:
        max_value=0
    data_spam = {
            'text' : text
        }
    spamResponse= requests.post(url='http://spam-classifier:8082/classify',json=data_spam)
    if(spamResponse.status_code==200):
        # Ottieni il contenuto JSON della risposta
        json_data = spamResponse.json()
        # Accedi ai valori all'interno del JSON
        classification = json_data['classification']
        spam_probability = json_data['spam_probability']
        if max_value != 0:
            max_value = (max_value + spam_probability) / 2
        else:
            max_value = spam_probability


    # Invio di un messaggio alla coda persistente
    message = json.dumps([recipient, text, sender, max_value]).encode('utf-8')

    channel.basic_publish(exchange='',
                        routing_key='em',
                        body=message,
                        properties=pika.BasicProperties(delivery_mode=2))

    # Chiusura della connessione
    connection.close()

    #return redirect(url_for('main.profile'))
    return render_template('profile.html', name=current_user.name)




@main.route('/startConsuming')
@login_required
def consume():
    recipient = current_user.name
    ###
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda se non esiste già
    channel.queue_declare(queue='em', durable=True)

    messages = []
    method_frame, header_frame, body = channel.basic_get(queue='em', auto_ack=False)
    while method_frame:
        message = json.loads(body.decode('utf-8'))
        if(message[0]==recipient):
            messages.append(message)
        method_frame, header_frame, body = channel.basic_get(queue='em', auto_ack=False)
    # Chiusura connessione a RabbitMQ
    connection.close()
    ####

    # Rendering del template "index.html" con i messaggi da visualizzare
    return render_template('coda.html', messages=messages)


###Taking questions and answers
@main.route('/seeMessages')
@login_required
def readChat():
###Questions
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda se non esiste già
    channel.queue_declare(queue='chat', durable=True)

    messages = []
    method_frame, header_frame, body = channel.basic_get(queue='chat', auto_ack=False)
    while method_frame:
        message = json.loads(body.decode('utf-8'))
        if(message[0]==current_user.name):
            messages.append(message)
        method_frame, header_frame, body = channel.basic_get(queue='chat', auto_ack=False)
    
    channel2 = connection.channel()

    # Dichiarazione della coda se non esiste già
    channel2.queue_declare(queue='answer', durable=True)

    answers = []
    method_frame, header_frame, body = channel2.basic_get(queue='answer', auto_ack=False)
    while method_frame:
        message = json.loads(body.decode('utf-8'))
        if(message[2]==current_user.name):
            answers.append(message)
        method_frame, header_frame, body = channel2.basic_get(queue='answer', auto_ack=False)
    # Chiusura connessione a RabbitMQ
    connection.close()


    # Rendering del template "index.html" con i messaggi da visualizzare
    return render_template('messages.html', messages=messages,answers=answers)



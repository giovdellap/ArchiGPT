from flask import Blueprint, render_template, redirect, url_for, request, flash
from flask_login import login_required, current_user
from .models import RiskEmail
from . import db
import re
import json
import pika, sys, os


from . import db

main = Blueprint('main', __name__)
x="test"


@main.route('/')
def index():
    return render_template('index.html')


@main.route('/profile')
@login_required
def profile():
    # c = sqlite3.connect('../instance/db.sqlite')
    # cur = c.cursor()
    name = current_user.name  # test
    # cur.execute("SELECT * from risk_email LIMIT 11")
    # test = cur.fetchall()
    test = RiskEmail.query.filter_by(recipient=name).all()
    return render_template('profile.html', name=current_user.name, test=test)


@main.route('/startConsuming')
@login_required
def consume():
    # Connessione a RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda se non esiste già
    channel.queue_declare(queue='em', durable=True)

    messages = []
    method_frame, header_frame, body = channel.basic_get(queue='em', auto_ack=False)
    while method_frame:
        message = json.loads(body.decode('utf-8'))
        messages.append(message)
        method_frame, header_frame, body = channel.basic_get(queue='em', auto_ack=False)
    # Chiusura connessione a RabbitMQ
    connection.close()

    # Rendering del template "index.html" con i messaggi da visualizzare
    return render_template('coda.html', messages=messages)


###Taking questions and answers
@main.route('/chatAdmin')
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
        messages.append(message)
        method_frame, header_frame, body = channel.basic_get(queue='chat', auto_ack=False)
    # Chiusura connessione a RabbitMQ
    connection.close()
    # Connessione a RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters(host='rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda se non esiste già
    channel.queue_declare(queue='answer', durable=True)

    answers = []
    method_frame, header_frame, body = channel.basic_get(queue='answer', auto_ack=False)
    while method_frame:
        message = json.loads(body.decode('utf-8'))
        answers.append(message)
        method_frame, header_frame, body = channel.basic_get(queue='answer', auto_ack=False)
    # Chiusura connessione a RabbitMQ
    connection.close()


    # Rendering del template "index.html" con i messaggi da visualizzare
    return render_template('messages.html', messages=messages,answers=answers)



###Admin answer to customers
@main.route('/answer', methods=['POST'])
@login_required
def answer():

    text = request.form.get('text')
    customer = request.form.get('customer')


    # Connessione al broker RabbitMQ
    connection = pika.BlockingConnection(pika.ConnectionParameters('rabbitmq'))
    channel = connection.channel()

    # Dichiarazione della coda persistente
    channel.queue_declare(queue='answer', durable=True)

    # Invio di un messaggio alla coda persistente
    message = json.dumps([current_user.name, text,customer]).encode('utf-8')

    channel.basic_publish(exchange='',
                        routing_key='answer',
                        body=message,
                        properties=pika.BasicProperties(delivery_mode=2))

    # Chiusura della connessione
    connection.close()

    return redirect(url_for('main.profile'))
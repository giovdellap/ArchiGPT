from flask import Flask,render_template
import socket
import os
import subprocess
import sys
os.environ['SENDGRID_API_KEY'] = "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"

import pika, os, time
import requests
import sendgrid
import os
from sendgrid.helpers.mail import *
from sendgrid import SendGridAPIClient
  
import pymongo

app = Flask(__name__)

def send_simple_message(user,msg):
    message = Mail(
        from_email="carmignanifederico@gmail.com",
        to_emails=user,
        subject='EvenTicket notification',
        html_content='Ciao, EvenTicket ha appena pubblicato un evento che potrebbe interessarti! Guarda sul sito'+' '+str(msg)+"!"
        )
    try:
        print(os.environ.get('SENDGRID_API_KEY'))
        sg = SendGridAPIClient(os.environ.get('SENDGRID_API_KEY'))
        response = sg.send(message)
        print(response.status_code)
        print(response.body)
        print(response.headers)
    except Exception as e:
        print(e.message)

def pdf_process_function(msg,method):
    print(" PDF processing")
    msg=str(msg,'utf-8')
    print(" [x] Received " + msg)
    #connetti al db
    #utenti query che interessa msg
    myclient = pymongo.MongoClient("mongodb://172.16.230.9:27017/")
    mydb = myclient["test"]
    mycol = mydb["users"]
    myquery = { "preferenza": method.routing_key }
    print("connection ok")
    mydoc = mycol.find(myquery)
    print(mydoc)
    for x in mydoc:
        print(x)
        user=x['email']
        print(user)
        send_simple_message(user,msg)
        print("email sent!")
        time.sleep(5) # delays for 5 seconds
    print(" PDF processing finished")
    return
    

if __name__ == "__main__":
    # Access the CLODUAMQP_URL environment variable and parse it (fallback to localhost)
    url = os.environ.get('CLOUDAMQP_URL', 'amqps://tldmywiz:fSr5JmVpRN1mnUSmPjByc6RbVTAAZwDv@rat.rmq2.cloudamqp.com/tldmywiz')
    params = pika.URLParameters(url)
    connection = pika.BlockingConnection(params)
    channel = connection.channel() # start a channel
    channel.queue_declare(queue='cultura')
    channel.queue_declare(queue='sport')
    channel.queue_declare(queue='musica')
    channel.queue_declare(queue='cinema')
    channel.queue_declare(queue='teatro')
    channel.queue_declare(queue='discoteca')
    # Declare a queue
    # create a function which is called on incoming messages
    def callback(ch, method, properties, body):
        pdf_process_function(body,method)
        
        
    # set up subscription on the queue
    channel.basic_consume('cultura',
    callback,
    auto_ack=True)
    channel.basic_consume('sport',
    callback,
    auto_ack=True)
    channel.basic_consume('musica',
    callback,
    auto_ack=True)
    channel.basic_consume('cinema',
    callback,
    auto_ack=True)
    channel.basic_consume('teatro',
    callback,
    auto_ack=True)
    channel.basic_consume('discoteca',
    callback,
    auto_ack=True)
    # start consuming (blocks)
    channel.start_consuming()
    connection.close()

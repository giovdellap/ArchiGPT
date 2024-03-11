from flask import Flask,render_template,request,jsonify,make_response
import socket
import pika, os, logging

app = Flask(__name__)




@app.route("/produce")
def index():
    interest=request.args.get('interest')
    nome=request.args.get('name_event')
    channel.basic_publish(exchange='', routing_key=interest, body='evento '+nome+' al seguente link: http://localhost/Aevents?event_categ='+interest )
    data= {'message':'done','code':'SUCCESS'}
    return make_response(jsonify(data),201)



if __name__ == "__main__":
    #CREAZIONE CODE CONNESSIONE RABBITMQ IN CLOUD
    logging.basicConfig()
    # Parse CLODUAMQP_URL (fallback to localhost)
    url = os.environ.get('CLOUDAMQP_URL', 'amqps://tldmywiz:fSr5JmVpRN1mnUSmPjByc6RbVTAAZwDv@rat.rmq2.cloudamqp.com/tldmywiz')
    params = pika.URLParameters(url)
    params.socket_timeout = 5
    connection = pika.BlockingConnection(params) # Connect to CloudAMQP
    channel = connection.channel() # start a channel
    channel.queue_declare(queue='cultura')
    channel.queue_declare(queue='sport')
    channel.queue_declare(queue='musica')
    channel.queue_declare(queue='cinema')
    channel.queue_declare(queue='teatro')
    channel.queue_declare(queue='discoteca')
    app.run(host='0.0.0.0', port=8081)

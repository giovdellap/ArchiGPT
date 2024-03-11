import pika, os, logging
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
# Declare a queue
# send a message
#save in db 
channel.basic_publish(exchange='', routing_key='cultura', body='Evento Mostra')
#channel.basic_publish(exchange='', routing_key='sport', body='Evento Sport')
#channel.basic_publish(exchange='', routing_key='musica', body='Evento Musica')
#channel.basic_publish(exchange='', routing_key='cinema', body='Evento Cinema')
#channel.basic_publish(exchange='', routing_key='discoteca', body='Evento Discoteca')
#channel.basic_publish(exchange='', routing_key='teatro', body='Evento Teatro')
print ("[x] Messages sent to consumer")
connection.close()
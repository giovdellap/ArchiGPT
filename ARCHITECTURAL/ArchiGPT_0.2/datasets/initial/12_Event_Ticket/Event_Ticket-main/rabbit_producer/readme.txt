We are creating 1 consumer and 1 producer.
The producer every time an event is created inserts in the proper queue the event.
The consumer takes the events from the queues and sends emails to the user interested in that types of events.
The queues are hosted in the cloud using a cloud solution for rabbitMQ called CloudAMQP.
The email are handled asking an external service to manage them: SendGrid.

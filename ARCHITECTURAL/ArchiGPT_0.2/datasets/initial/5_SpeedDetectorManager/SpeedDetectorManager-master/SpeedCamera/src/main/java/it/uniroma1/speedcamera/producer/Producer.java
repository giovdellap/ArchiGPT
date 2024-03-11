package it.uniroma1.speedcamera.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.Date;

public class Producer {

	private String queue;
	private String brokerUrl;

	private ActiveMQConnectionFactory connectionFactory;
	private Session session;
	private Connection connection;

	/**
	 * Producer constructor.
	 * 
	 * @param brokerUrl : JMS broker URL
	 * @param queue : JMS queue
	 */
	public Producer(String brokerUrl, String queue) {
		this.queue = queue;
		this.brokerUrl = brokerUrl;

		// Create a ConnectionFactory
		connectionFactory = new ActiveMQConnectionFactory(brokerUrl);

	}

	/**
	 * Send JMS message.
	 * 
	 * @param text
	 */
	public void sendMessage(String text) {

		try {

			startConnection();

			// Create the destination (Topic or Queue)
			Destination destination = session.createQueue(queue);
			// Create a MessageProducer from the Session to the Topic or Queue
			MessageProducer producer = session.createProducer(destination);
			// Create a messages
			Message message = session.createTextMessage(text);

			producer.send(message);
			System.out.println("send | message : << " + text + " >> | queue << " + queue + " >> | broker url : << " + brokerUrl + " >> | " + new Date());

			// Clean up
			producer.close();
			closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Create and start connection session with JMS server.
	 * 
	 * @throws JMSException
	 */
	private void startConnection() throws JMSException {

		System.out.println("Get Connection from broker << " + brokerUrl + " >>");

		// Create a Connection
		connection = connectionFactory.createConnection();
		connection.start();

		// Create a Session
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		System.out.println("Connection Created with ID << " + connection.getClientID() + " >>");
	}

	/**
	 * Close JMS connection.
	 * 
	 * @throws JMSException
	 */
	private void closeConnection() throws JMSException {
		session.close();
		connection.close();

		System.out.println("JMS Connection closed");
	}

}

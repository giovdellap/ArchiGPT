/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.yourstore.store.jmsClient;

import java.util.Random;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.Context;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ToDatabaseJMSProducer implements MessageListener {

	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(ToDatabaseJMSProducer.class);

	Context jndiContext = null;
	ActiveMQConnectionFactory connectionFactory = null;
	Connection connection = null;
	Session session = null;
	Destination queue = null;
	Destination productInfoDest = null;
	MessageProducer producer = null;
	String destinationName = "StoreToStock";
	MessageConsumer responseConsumer = null;
	
	@Value("${activeMq.baseUrl}")
	public String activeMqBaseUrl;
	
	@PostConstruct
	public void start() {
		connectionFactory = new ActiveMQConnectionFactory(activeMqBaseUrl);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			queue = session.createQueue(destinationName);

			producer = session.createProducer(queue);
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			LOG.info("Producer To Database avviato.....");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendCheckProductAvailabilityRequest(Integer productId, Integer amount) throws Exception {
		try {
			Destination tempDest = session.createTemporaryQueue();
			MessageConsumer responseConsumer = session.createConsumer(tempDest);
			responseConsumer.setMessageListener(this);

			TextMessage message = session.createTextMessage();
			message.setJMSReplyTo(tempDest);
			String correlationId = this.createRandomString();
			message.setJMSCorrelationID(correlationId);

			message.setStringProperty("State", "CheckProductDisponibility");
			message.setIntProperty("ProductId", productId);
			message.setIntProperty("Amount", amount);

			LOG.info("Send product info request to Database... Product id: " + productId);
			try {
				producer.send(message);
			} catch (Exception err) {
				err.printStackTrace();
				throw new Exception();
			}
		} catch (JMSException e) {
			LOG.error("JMSException occurred: " + e);
		}
	}

	public void sendPurchasedProductNotification(Integer productId, Integer amount) {
		try {
			TextMessage message = session.createTextMessage();

			message.setStringProperty("State", "PurchasedProduct");
			message.setIntProperty("ProductId", productId);
			message.setIntProperty("Amount", amount);
			LOG.info("Send purchased product notification to Database... Product id and amount: " + productId + ", "
					+ amount);
			try {
				producer.send(message);
			} catch (Exception err) {
				err.printStackTrace();
			}
		} catch (JMSException e) {
			LOG.error("JMSException occurred: " + e);
		}
	}

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = (TextMessage) message;
		Boolean check;
		try {
			check = textMessage.getBooleanProperty("Check");
			if (!check) {
				throw new Exception();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String createRandomString() {
		Random random = new Random(System.currentTimeMillis());
		long randomLong = random.nextLong();
		return Long.toHexString(randomLong);
	}
}

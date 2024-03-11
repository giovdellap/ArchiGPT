package it.YourOnlineStore.Database;

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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FromStoreJMSListener implements MessageListener {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FromStoreJMSListener.class);
	@Autowired
	public ProductService productService;

	private Connection connection;
	private Session session;
	private Destination queue = null;
	private MessageProducer producer = null;
	ActiveMQConnectionFactory connectionFactory = null;
	
	@Value("${activeMq.baseUrl}")
	public String activeMqBaseUrl;
	
	public void stop() {
		try {
			connection.stop();
		} catch (JMSException err) {
			err.printStackTrace();
		}
	}
	
	@PostConstruct
	public void start() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(activeMqBaseUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			this.session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			queue = this.session.createQueue("StoreToStock");
			
			this.producer = this.session.createProducer(null);
			this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			MessageConsumer consumer = this.session.createConsumer(queue);
			consumer.setMessageListener(this);
			LOG.info("Listener From Store avviato.....");
		} catch (JMSException err) {
			err.printStackTrace();
		}
	}

	public void onMessage(Message mex) {
		try {
			String state = mex.getStringProperty("State");
			switch (state) {
			case "CheckProductDisponibility":
				TextMessage messageProduct = null;
				messageProduct = this.session.createTextMessage();
				Integer productId = mex.getIntProperty("ProductId");
				LOG.info("Getting info product with ID: " + productId);
				if(productService.exists(productId)) {
					Integer amount = mex.getIntProperty("Amount");
					Product product = productService.findById(productId).get();
					Integer disponibility = product.getDisponibility();
					if(disponibility >= amount) {
						messageProduct.setBooleanProperty("Check", true);
					} else {
						messageProduct.setBooleanProperty("Check", false);
					}
					messageProduct.setJMSCorrelationID(mex.getJMSCorrelationID());

		            this.producer.send(mex.getJMSReplyTo(), messageProduct);
				}
				break;
			case "PurchasedProduct":
				Integer prodId = mex.getIntProperty("ProductId");
				Integer amount = mex.getIntProperty("Amount");
				LOG.info("Got Message ProdottoAcquistato with: ( Amount: " + amount + ", prodId: "
						+ prodId + " )");
				productService.buy(prodId, amount);
				break;
			default:
				break;
			}
		} catch (JMSException err) {
			err.printStackTrace();
		}
	}

}

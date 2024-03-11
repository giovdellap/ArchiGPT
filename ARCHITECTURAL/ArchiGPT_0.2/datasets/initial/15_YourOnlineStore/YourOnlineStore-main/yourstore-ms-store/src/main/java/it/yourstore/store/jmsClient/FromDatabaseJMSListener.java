package it.yourstore.store.jmsClient;

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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import it.yourstore.store.domain.Product;
import it.yourstore.store.service.ProductService;

@Service
public class FromDatabaseJMSListener implements MessageListener {
	private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(FromDatabaseJMSListener.class);
	
	@Autowired
	public ProductService productService;
	
	private Connection connection;
	private Session session;
	private Destination queue = null;
	private MessageProducer producer = null;
	ActiveMQConnectionFactory connectionFactory = null;
	
	@Value("${activeMq.baseUrl}")
	public String activeMqBaseUrl;
	
	@PostConstruct
	public void start() {
		try {
			connectionFactory = new ActiveMQConnectionFactory(activeMqBaseUrl);
			connection = connectionFactory.createConnection();
			connection.start();
			this.session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
			queue = this.session.createQueue("StockToStore");

			this.producer = this.session.createProducer(null);
			this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

			MessageConsumer consumer = this.session.createConsumer(queue);
			consumer.setMessageListener(this);
			LOG.info("Listener From Database avviato.....");
		} catch (JMSException err) {
			err.printStackTrace();
		}
	}

	public void onMessage(Message mex) {
		try {
			String state = mex.getStringProperty("State");
			switch (state) {
			case "NewProduct":
				Integer productId = mex.getIntProperty("ProductId");
				Float cost = mex.getFloatProperty("Cost");
				Product product = new Product();
				product.setProductId(productId);
				product.setCost(cost);
				productService.insert(product);
				LOG.info("New product added: " + productId);
				break;
			default:
				break;
			}
		} catch (JMSException err) {
			err.printStackTrace();
		}
	}

}

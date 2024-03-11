package it.uniroma1.commons.queue.config;

import javax.jms.Queue;

import it.uniroma1.commons.utility.ParamConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

@Configuration
@EnableJms
public class SpringActiveMQConfig {

	@Value("${activemq.broker.url}")
	private String brokerUrl;

	@Bean
	@Qualifier("speed_queue")
	public Queue queueSpeed() {
		return new ActiveMQQueue(ParamConnection.SPEED_DETECTION_QUEUE);
	}

	@Bean
	@Qualifier("statistic_queue")
	public Queue queueStatistic() {
		return new ActiveMQQueue(ParamConnection.STATISTIC_QUEUE);
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL(brokerUrl);
		return activeMQConnectionFactory;
	}

	@Bean
	public JmsTemplate jmsTemplate() {
		return new JmsTemplate(activeMQConnectionFactory());
	}

}

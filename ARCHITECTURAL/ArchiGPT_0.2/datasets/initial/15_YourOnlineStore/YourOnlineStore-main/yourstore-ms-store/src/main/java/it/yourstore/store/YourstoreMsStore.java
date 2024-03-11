package it.yourstore.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import it.yourstore.store.jmsClient.FromDatabaseJMSListener;
import it.yourstore.store.jmsClient.ToDatabaseJMSProducer;

@SpringBootApplication
@ComponentScan({ "it.yourstore.store"})
public class YourstoreMsStore {
	public static void main(String[] args) {
		SpringApplication.run(YourstoreMsStore.class, args);
	}
	
//	@Bean
//	public FromDatabaseJMSListener jMSListener() throws Exception {
//		FromDatabaseJMSListener listener = new FromDatabaseJMSListener();
//		listener.start();
//		return listener;
//	}
//	
//	@Bean
//	public ToDatabaseJMSProducer jMSProducer() throws Exception {
//		ToDatabaseJMSProducer producer = new ToDatabaseJMSProducer();
//		producer.start();
//		return producer;
//	}
}
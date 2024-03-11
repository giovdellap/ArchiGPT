package it.YourOnlineStore.Database;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
@ComponentScan({ "it.YourOnlineStore.Database"})
public class DatabaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatabaseApplication.class, args);
	}
	
//	@Bean
//	public FromStoreJMSListener jMSListener() throws Exception {
//		FromStoreJMSListener listener = new FromStoreJMSListener();
//		listener.start();
//		return listener;
//	}
//	
//	@Bean
//	public ToStoreJMSProducer jMSProducer() throws Exception {
//		ToStoreJMSProducer producer = new ToStoreJMSProducer();
//		producer.start();
//		return producer;
//	}

}

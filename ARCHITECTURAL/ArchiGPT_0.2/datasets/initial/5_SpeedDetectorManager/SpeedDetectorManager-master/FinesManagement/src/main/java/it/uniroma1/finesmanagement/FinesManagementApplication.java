package it.uniroma1.finesmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@ComponentScan({"it.uniroma1.commons","it.uniroma1.finesmanagement"})
public class FinesManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(FinesManagementApplication.class, args);

    }
}

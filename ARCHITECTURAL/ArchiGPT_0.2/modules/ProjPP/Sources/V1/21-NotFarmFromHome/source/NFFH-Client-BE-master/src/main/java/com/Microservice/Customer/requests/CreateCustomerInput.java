package com.Microservice.Customer.requests;

import java.util.Date;

import com.Microservice.Customer.entities.Customer;

public record CreateCustomerInput(String email, String name, String password) {
    public Customer toTask() {
        Customer task = new Customer();

        task.setEmail(email)
            .setName(name)
            .setPassword(password);

        return task;
    }
}

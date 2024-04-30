package com.Microservice.Customer.services;

import java.util.List;
import java.util.Optional;

import com.Microservice.Customer.entities.Customer;


public interface ICustomerService {
        
    Customer create(Customer task);

    List<Customer> findAll();

    Optional<Customer> findById(int id);

    Customer findByEmail(String email);

    Customer update(Customer taskToUpdate);

    void delete(int id);

}

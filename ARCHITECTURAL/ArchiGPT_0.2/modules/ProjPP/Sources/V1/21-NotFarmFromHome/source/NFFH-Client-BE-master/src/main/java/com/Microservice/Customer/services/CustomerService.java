package com.Microservice.Customer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Microservice.Customer.entities.Customer;
import com.Microservice.Customer.repositories.CustomerRepository;

@Service
public class CustomerService implements ICustomerService {
    
    @Autowired
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository taskRepository) {
        this.customerRepository = taskRepository;
    }

    @Override
    public Customer create(Customer task) {
        return customerRepository.save(task);
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        customerRepository.findAll().forEach(customers::add);

        return customers;
    }

    @Override
    public Optional<Customer> findById(int id) {
        return customerRepository.findById(id);
    }

    @Override
    public Customer findByEmail(String email) {
        return customerRepository.findByemail(email);
    }

    @Override
    public Customer update(Customer taskToUpdate) {
        return customerRepository.save(taskToUpdate);
    }

    @Override
    public void delete(int id) {
        customerRepository.deleteById(id);
    }
}

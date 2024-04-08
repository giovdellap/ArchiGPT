package com.Microservice.Customer.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Microservice.Customer.entities.Customer;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    Customer findByemail(String email);

}

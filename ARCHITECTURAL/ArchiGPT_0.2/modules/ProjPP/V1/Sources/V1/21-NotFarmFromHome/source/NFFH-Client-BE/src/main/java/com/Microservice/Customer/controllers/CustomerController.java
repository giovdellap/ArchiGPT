package com.Microservice.Customer.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.Microservice.Customer.entities.Customer;
import com.Microservice.Customer.model.GetClientByIDResponse;
import com.Microservice.Customer.model.LoginInput;
import com.Microservice.Customer.model.LoginResponse;
import com.Microservice.Customer.model.SignupResponse;
import com.Microservice.Customer.requests.CreateCustomerInput;
import com.Microservice.Customer.services.CustomerService;
import com.Microservice.Customer.verifyToken.VerifyHandler;

import java.util.List;
import java.util.Optional;

@RestController
public class CustomerController {

    @Autowired
    public final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping(path = "/client", consumes = "application/json", produces = "application/json")
    public ResponseEntity<SignupResponse> createCustomer(@RequestBody CreateCustomerInput createTaskInput) {
        
        Customer customer = customerService.create(createTaskInput.toTask());


        SignupResponse response = new SignupResponse("true",String.valueOf(customer.getId()));
        return new ResponseEntity<SignupResponse>(response, HttpStatus.OK);
    }

    @GetMapping("/clients")
    public ResponseEntity<List<Customer>> allCustomer() {
        List<Customer> tasks = customerService.findAll();
        return new ResponseEntity<>(tasks, HttpStatus.MULTI_STATUS);
    }

    @GetMapping("/client/{id}")
    public ResponseEntity<GetClientByIDResponse> oneCustomer(
        @RequestHeader("token") String token,
        @RequestBody int id
        ) {

        boolean error = false;
        VerifyHandler handler = new VerifyHandler(this.customerService);
        handler.verify(token);


        if(!handler.isSuccess())
            error = true;
        if((!error) || handler.getRole().equals("admin")) {
            Optional<Customer> optionalTask = customerService.findById(id);
            if (optionalTask.isPresent()) {
                Customer customer = optionalTask.get();
                GetClientByIDResponse res = new GetClientByIDResponse(token, customer.getEmail(), customer.getPassword());
                return new ResponseEntity<GetClientByIDResponse>(res, HttpStatus.OK);
            }
        }
        return new ResponseEntity<GetClientByIDResponse>(new GetClientByIDResponse(), HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/client/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable int id) {
        customerService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @PostMapping("/client/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginInput email){
        
        String mail = email.ToStringEmail();
        
        Customer customer = customerService.findByEmail(mail);

        LoginResponse res;

        if(customer != null){
            res = new LoginResponse(true, customer.getPassword(), String.valueOf(customer.getId()), customer.getName());
        }

        else{
            res = new LoginResponse(false,null,null,null);
            return new ResponseEntity<LoginResponse>(res, HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<LoginResponse>(res, HttpStatus.OK);
    }
}

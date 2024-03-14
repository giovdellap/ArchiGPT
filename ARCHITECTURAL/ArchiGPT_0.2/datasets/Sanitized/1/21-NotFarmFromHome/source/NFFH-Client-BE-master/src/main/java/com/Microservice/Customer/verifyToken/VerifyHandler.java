package com.Microservice.Customer.verifyToken;


import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.Microservice.Customer.entities.Customer;
import com.Microservice.Customer.services.CustomerService;


public class VerifyHandler {

    private String authAddress = "http://auth:9701/verifyToken";        
    public final CustomerService customerService;
    private String role = "";
    private Customer customer;
    private boolean success;
    
    public VerifyHandler(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    public void verify(String token) {
        
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<VerifyTokenRequest> req = new HttpEntity<VerifyTokenRequest>(
            new VerifyTokenRequest(token)
        );
        ResponseEntity<VerifyTokenResponse> res = restTemplate.postForEntity(authAddress, req, VerifyTokenResponse.class);
        this.success = res.getBody().isSuccess();
        if(this.success) {
            this.role = res.getBody().getRole();
            if (!this.getRole().equals("admin")) {
                this.customer = this.customerService.findByEmail(res.getBody().getEmail());
            }
            
        }
    }
    
    public Customer getCustomer() {
            return customer;
        }
        
    public String getRole() {
        return role;
    }
    
    public boolean isSuccess() {
        return success;
    }    
}

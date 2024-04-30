package com.Microservice.Customer.model;

import com.Microservice.Customer.entities.Customer;

public class SignupResponse {
    private String success;
    private String id;

    

    public SignupResponse(String success, String id) {
        this.success = success;
        this.id = id;
    }



    public String getSuccess() {
        return success;
    }



    public void setSuccess(String success) {
        this.success = success;
    }



    public String getId() {
        return id;
    }



    public void setId(String id) {
        this.id = id;
    }


    
}

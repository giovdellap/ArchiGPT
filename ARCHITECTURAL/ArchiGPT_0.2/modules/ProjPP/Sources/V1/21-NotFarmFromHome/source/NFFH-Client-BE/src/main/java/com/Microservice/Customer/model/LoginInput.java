package com.Microservice.Customer.model;

public record LoginInput(String email) {
    public String ToStringEmail(){
        return email;
    }
}
package com.Microservice.Customer.model;

public class LoginResponse {
    
    private boolean success;
    private String password;
    private String id;
    private String username;

    
    public LoginResponse(boolean success, String password, String id, String username) {
        this.success = success;
        this.password = password;
        this.id = id;
        this.username = username;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    

    
        
    

    

    

}

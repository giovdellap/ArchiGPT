package com.example.marketinganalyzer.Models.Api;

public class LoginPostResponse {
    private String jwt;

    public LoginPostResponse() {
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}

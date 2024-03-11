package com.example.marketinganalyzer.Models;

import java.util.HashMap;

public class AnalyticsProfile {
    private HashMap<String,String> customerProfile;
    private HashMap<String,String> productProfile;

    public AnalyticsProfile() {
    }

    public AnalyticsProfile(HashMap<String, String> customerProfile, HashMap<String, String> productProfile) {
        this.customerProfile = customerProfile;
        this.productProfile = productProfile;
    }

    public HashMap<String, String> getCustomerProfile() {
        return customerProfile;
    }

    public void setCustomerProfile(HashMap<String, String> customerProfile) {
        this.customerProfile = customerProfile;
    }

    public HashMap<String, String> getProductProfile() {
        return productProfile;
    }

    public void setProductProfile(HashMap<String, String> productProfile) {
        this.productProfile = productProfile;
    }
}

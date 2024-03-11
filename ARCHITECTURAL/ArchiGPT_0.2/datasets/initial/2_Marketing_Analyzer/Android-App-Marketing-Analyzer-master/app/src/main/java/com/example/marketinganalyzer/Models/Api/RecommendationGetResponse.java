package com.example.marketinganalyzer.Models.Api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RecommendationGetResponse {
    private HashMap<String , HashMap<String ,Float>> recProfile;
    private String profileName;

    public RecommendationGetResponse() {
        recProfile = new HashMap<String,HashMap<String ,Float>>();
    }

    public String getProfileName() {
        return profileName;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void addProfileEntry(String name, HashMap<String,Float> values){
        recProfile.put(name,values);
    }

    public HashMap<String, HashMap<String, Float>> getRecProfile() {
        return recProfile;
    }
}

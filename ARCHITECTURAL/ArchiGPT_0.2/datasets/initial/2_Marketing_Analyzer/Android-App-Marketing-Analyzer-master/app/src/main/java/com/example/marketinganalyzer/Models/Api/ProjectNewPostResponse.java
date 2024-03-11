package com.example.marketinganalyzer.Models.Api;

public class ProjectNewPostResponse {
    private String apiUrl;

    public ProjectNewPostResponse() {
    }

    public ProjectNewPostResponse(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}

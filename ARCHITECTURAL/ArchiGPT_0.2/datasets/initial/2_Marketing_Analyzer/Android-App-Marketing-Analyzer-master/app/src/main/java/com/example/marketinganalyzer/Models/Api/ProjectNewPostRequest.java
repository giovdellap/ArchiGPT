package com.example.marketinganalyzer.Models.Api;

public class ProjectNewPostRequest {
    private String project;

    public ProjectNewPostRequest(String project) {
        this.project = project;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}

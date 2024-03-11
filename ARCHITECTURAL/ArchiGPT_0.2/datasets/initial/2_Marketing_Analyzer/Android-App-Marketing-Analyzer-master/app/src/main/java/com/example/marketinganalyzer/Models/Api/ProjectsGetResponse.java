package com.example.marketinganalyzer.Models.Api;

import java.util.ArrayList;
import java.util.List;

public class ProjectsGetResponse {
    private List<String> projects;

    public ProjectsGetResponse() {
        projects = new ArrayList<String>();
    }

    public List<String> getProjects() {
        return projects;
    }

    public void setProjects(List<String> projects) {
        this.projects = projects;
    }

    public void addProject(String project){
        projects.add(project);
    }
}

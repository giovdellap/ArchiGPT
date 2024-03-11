package com.example.marketinganalyzer.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.marketinganalyzer.Models.UserCred;

public abstract class AnalysisResults extends DefaultActivity{
    protected String currentProject;
    protected UserCred userCred;
    protected GetRecApiInput apiInput;
    protected String selectedProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get user Data
        userCred = storage.getUserData();
    }

    protected class GetRecApiInput {
        UserCred userCred;
        String projectName;

        public GetRecApiInput(UserCred userCred, String projectName) {
            this.userCred = userCred;
            this.projectName = projectName;
        }

        public UserCred getUserCred() {
            return userCred;
        }

        public void setUserCred(UserCred userCred) {
            this.userCred = userCred;
        }

        public String getProjectName() {
            return projectName;
        }

        public void setProjectName(String projectName) {
            this.projectName = projectName;
        }
    }
}

package com.example.marketinganalyzer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.marketinganalyzer.Models.Api.LoginPostRequest;
import com.example.marketinganalyzer.Models.Api.LoginPostResponse;
import com.example.marketinganalyzer.Models.Api.ProjectsGetResponse;
import com.example.marketinganalyzer.Models.ProjectProfile;
import com.example.marketinganalyzer.Models.UserCred;
import com.example.marketinganalyzer.Network.ApiUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ProfilePageActivity extends DefaultActivity {
    private UserCred currentCred;
    private TextView pendingMessage;
    private TextView welcomeMessage;
    private AttributeSet pendingMessageAttr;
    private AttributeSet projectsListButtonAttr;
    private LinearLayout projectsListLayout;
    private Button buttonCreate;
    private Button buttonLogout;
    private Button projectsListButton;
    protected List<ProjectProfile> currentProjects;
    protected String selectedProject;
    protected TextView margin_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        // Get the intent and its data.
        Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //TextView textView = findViewById(R.id.order_textview);
        //textView.setText(message);

        //Get userdata
        currentCred = storage.getUserData();

        //setup layout before API Call
        layoutBeforeAPICall();

        //Async Fetch API
        try {
            invokeApi();
        }
        catch (Exception ex){
            displayToast(ex.getMessage());
        }


        buttonCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this,
                        NewProjectActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
            }
        });

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePageActivity.this,
                        WelcomeActivity.class);
                //intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void layoutBeforeAPICall() {
        //Init parser and load custom xml attributes
        resources = this.getResources();
        xmlResourceParser = resources.getXml(R.xml.profile_page_text_pending_projects);
        pendingMessageAttr = Xml.asAttributeSet(xmlResourceParser);
        pendingMessage = new TextView(this,pendingMessageAttr);

        //Init Create project button
        buttonCreate = findViewById(R.id.profile_button_create_project);
        buttonLogout = findViewById(R.id.profile_button_logout);

        // INIT Buttons List and attributes
        xmlResourceParser = resources.getXml(R.xml.profile_page_button_current_project);
        projectsListButtonAttr = Xml.asAttributeSet(xmlResourceParser);

        //Populate Welcome Message
        welcomeMessage = findViewById(R.id.profile_text_welcome_username);
        welcomeMessage.setText(getString(R.string.profile_text_welcome) + " " + currentCred.getUsername());

        //SET Pending message to visible with loading phrase
        pendingMessage.setText(getString(R.string.profile_text_pending_projects));
        pendingMessage.setVisibility(View.VISIBLE);

        projectsListLayout = findViewById(R.id.profile_layout_projects);
        projectsListLayout.addView(pendingMessage);
    }

    @Override
    protected void layoutOnAPIReturn(JSONObject response) {
        try{
            ProjectsGetResponse responseObj = unmarshal(response);

            //check if the list is empty
            if (responseObj.getProjects().isEmpty()){
                //Show no current projects available
                pendingMessage.setText(getString(R.string.profile_text_projects_not_avail));
                pendingMessage.setVisibility(View.VISIBLE);
            }
            else {
                projectsListLayout.removeView(pendingMessage);
                for(int i=0; i < responseObj.getProjects().size(); i ++){
                    String projectName = responseObj.getProjects().get(i);
                    projectsListButton = new Button(this, projectsListButtonAttr,0, R.style.Widget_MainTheme_Button_TypeB);
                    projectsListButton.setText(projectName);
                    projectsListButton.setOnClickListener(new ProjectListButtonListener());
                    projectsListLayout.addView(projectsListButton);

                    margin_adapter = new TextView(this);
                    margin_adapter.setText("          ");
                    projectsListLayout.addView(margin_adapter);
                }
            }
        }
        catch (Exception ex){
            displayToast(ex.getMessage());
            layoutBeforeAPICall();
        }
    }

    @Override
    protected void invokeApi() throws Exception {
        //prepare request params
        int method = Request.Method.GET;
        String url = ApiUrls.userProjectsUrl;
        ApiCall(method,url,null, currentCred.getToken());
    }

    protected ProjectsGetResponse unmarshal(JSONObject response) throws Exception{
        ProjectsGetResponse responseObject = new ProjectsGetResponse();
        if(response.has("error")){
            throw new Exception((String) response.opt("error"));
        }
        else if (!response.has("projects")){
            throw new Exception(API_ERROR);
        }
        else {
            JSONArray projects = (JSONArray) response.opt("projects");
            for(int i=0; i < projects.length(); i ++){
                responseObject.addProject(projects.getString(i));
            }
            return responseObject;
        }
    }

    private class ProjectListButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button currentButton = (Button) v;
            selectedProject = currentButton.getText().toString();
            storage.setSelectedProject(selectedProject);
            // Start the new Intent with the Selected Project Name
            Intent intent = new Intent(ProfilePageActivity.this,
                    DashboardActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        //super.onBackPressed();
    }
}

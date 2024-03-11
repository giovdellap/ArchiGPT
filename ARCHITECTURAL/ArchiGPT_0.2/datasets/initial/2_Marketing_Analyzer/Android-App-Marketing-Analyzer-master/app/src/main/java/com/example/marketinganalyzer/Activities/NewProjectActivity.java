package com.example.marketinganalyzer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.marketinganalyzer.Models.Api.LoginPostRequest;
import com.example.marketinganalyzer.Models.Api.LoginPostResponse;
import com.example.marketinganalyzer.Models.Api.ProjectNewPostRequest;
import com.example.marketinganalyzer.Models.Api.ProjectNewPostResponse;
import com.example.marketinganalyzer.Models.ProjectTemplatesSupported;
import com.example.marketinganalyzer.Models.UserCred;
import com.example.marketinganalyzer.Network.ApiUrls;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewProjectActivity extends DefaultActivity {
    private UserCred currentCred;
    protected RadioGroup radioGroup;
    protected TextView loadingText;
    protected List<RadioButton> radioButtons;
    protected AttributeSet radioButtonAttributeSet;
    private String projectTemplateSelected;
    private String projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        //Get userdata
        currentCred = storage.getUserData();

        //Set Layout before API Call
        layoutBeforeAPICall();

        Button buttonCreateProject = findViewById(R.id.new_project_button_create);
        buttonCreateProject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Get selected radiobutton
                RadioButton selectedRadioButton = getRadiobuttonSelected();
                if (selectedRadioButton == null){
                    displayToast(getString(R.string.new_project_toast_no_radioButton_selected));
                }
                else {
                    projectTemplateSelected = String.valueOf(selectedRadioButton.getText());

                    //Get selected Project Name
                    TextInputLayout tc = findViewById(R.id.new_project_text_project_name);
                    projectName = tc.getEditText().getText().toString();

                    if (projectName.matches("")){
                        displayToast(getString(R.string.new_project_toast_invalid_project_name));
                    }
                    else {
                        try{
                            //Invoke APi and register the project
                            invokeApi();
                            loadingText.setVisibility(View.VISIBLE);
                        }
                        catch (Exception ex){
                            displayToast(ex.getMessage());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(projectTemplateSelected != null && projectName != null){
            outState.putString(PROJECT_TEMPLATE, projectTemplateSelected);
            outState.putString(NEW_PROJECT_NAME, projectName);
        }
    }

    protected void layoutBeforeAPICall(){
        //Switch visibility between loading and RadioGroup
        radioGroup = findViewById(R.id.new_projects_radioGroup);
        radioGroup.clearCheck();
        radioGroup.removeAllViews();
        radioButtons = new ArrayList<RadioButton>();
        radioGroup.setVisibility(View.VISIBLE);

        loadingText = findViewById(R.id.new_project_text_loading);
        loadingText.setVisibility(View.INVISIBLE);


        //Init parser and load custom xml attributes
        resources = this.getResources();
        xmlResourceParser = resources.getXml(R.xml.new_project_radiobutton);
        radioButtonAttributeSet = Xml.asAttributeSet(xmlResourceParser);

        //Show project Templates
        List<String> templates = new ArrayList<>();
        templates.add("Food Industry");
        templates.add("Electronics");
        templates.add("Automotive");
        for (int i =0; i < templates.size(); i ++){
            RadioButton radioButton = new RadioButton(NewProjectActivity.this, radioButtonAttributeSet, 0, R.style.Widget_MainTheme_RadioButton);
            radioButton.setText(templates.get(i));
            radioGroup.addView(radioButton);
            radioButtons.add(radioButton);
        }
    }

    protected void layoutOnAPIReturn(JSONObject response){
        try{
            //Check Response Object
            unmarshal(response);

            //Turn off the loading text
            loadingText.setVisibility(View.INVISIBLE);

            //Starting the Other Activity
            Intent intent = new Intent(NewProjectActivity.this,
                    ProfilePageActivity.class);
            intent.putExtra(PROJECT_TEMPLATE, projectTemplateSelected);
            intent.putExtra(NEW_PROJECT_NAME, projectName);
            startActivity(intent);
        }
        catch (Exception ex){
            displayToast(ex.getMessage());
            layoutBeforeAPICall();
        }
    }

    @Override
    protected void invokeApi() throws Exception {
        //prepare request params
        int method = Request.Method.POST;
        String url = ApiUrls.userProjectsNewUrl;
        JSONObject request = marshal(new ProjectNewPostRequest(projectName));
        ApiCall(method,url,request, currentCred.getToken());
    }

    protected void unmarshal(JSONObject response) throws Exception{
        if(response.has("error")){
            throw new Exception((String) response.opt("error"));
        }
        else if (!response.has("message")){
            throw new Exception(API_ERROR);
        }
        else {
            displayToast(response.opt("message").toString());
        }
    }

    protected JSONObject marshal(ProjectNewPostRequest request) throws Exception{
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.putOpt("project", request.getProject());
        }
        catch (Exception ex){
            throw new Exception(API_ERROR);
        }
        return requestObject;
    }

    protected RadioButton getRadiobuttonSelected(){
        RadioButton currentRadioButton;
        for(int i=0; i< radioButtons.size(); i ++){
            currentRadioButton = radioButtons.get(i);
            if (currentRadioButton.isChecked()){
                return currentRadioButton;
            }
        }
        return null;
    }
}
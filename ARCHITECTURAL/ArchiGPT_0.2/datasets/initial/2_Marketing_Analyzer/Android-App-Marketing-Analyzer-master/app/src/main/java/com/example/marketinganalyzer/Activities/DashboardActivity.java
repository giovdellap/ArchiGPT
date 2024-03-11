package com.example.marketinganalyzer.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.util.AttributeSet;
import android.util.Xml;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.marketinganalyzer.Models.Api.RecommendationGetResponse;
import com.example.marketinganalyzer.Models.ProjectProfile;
import com.example.marketinganalyzer.Models.UserCred;
import com.example.marketinganalyzer.Network.ApiUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class DashboardActivity extends DefaultActivity{
    private String projectSelected;
    private UserCred currentCred;
    private TextView pendingMessage;
    private TextView welcomeMessage;
    private AttributeSet pendingMessageAttr;
    private AttributeSet projectsListButtonAttr;
    private LinearLayout projectsListLayout;
    private Button projectsListButton;
    protected String selectedProduct;
    protected TextView margin_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        // Get Project Name and Set it
        projectSelected = storage.getSelectedProject();

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
    }

    @Override
    protected void layoutBeforeAPICall() {
        //Init parser and load custom xml attributes
        resources = this.getResources();
        xmlResourceParser = resources.getXml(R.xml.profile_page_text_pending_projects);
        pendingMessageAttr = Xml.asAttributeSet(xmlResourceParser);
        pendingMessage = new TextView(this,pendingMessageAttr);

        // INIT Buttons List and attributes
        xmlResourceParser = resources.getXml(R.xml.profile_page_button_current_project);
        projectsListButtonAttr = Xml.asAttributeSet(xmlResourceParser);

        //Populate Welcome Message
        welcomeMessage = findViewById(R.id.dashboard_text_welcome_username);
        welcomeMessage.setText(getString(R.string.dashboard_text_welcome) + " " + currentCred.getUsername());

        //SET Pending message to visible with loading phrase
        pendingMessage.setText(getString(R.string.dashboard_text_pending_products));
        pendingMessage.setVisibility(View.VISIBLE);

        projectsListLayout = findViewById(R.id.dashboard_layout_products);
        projectsListLayout.addView(pendingMessage);
    }

    @Override
    protected void layoutOnAPIReturn(JSONObject response) {
        try{
            List<RecommendationGetResponse> responseObj = unmarshal(response);

            //check if the list is empty
            if (responseObj.isEmpty()){
                //Show no current projects available
                pendingMessage.setText(getString(R.string.dashboard_text_products_not_avail));
                pendingMessage.setVisibility(View.VISIBLE);
            }
            else {
                projectsListLayout.removeView(pendingMessage);
                for(int i=0; i < responseObj.size(); i ++){
                    String projectName = responseObj.get(i).getProfileName();
                    projectsListButton = new Button(this, projectsListButtonAttr,0, R.style.Widget_MainTheme_Button_TypeB);
                    projectsListButton.setText(projectName);
                    projectsListButton.setOnClickListener(new DashboardActivity.ProjectListButtonListener());
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
        String url = ApiUrls.recommendationUrl + projectSelected;
        ApiCall(method,url,null, currentCred.getToken());
    }

    protected List<RecommendationGetResponse> unmarshal(JSONObject response) throws Exception{
        HashMap<String,Float> itemMap;
        Iterator<String> keys;
        List<RecommendationGetResponse> responseList = new ArrayList<RecommendationGetResponse>();
        RecommendationGetResponse recObject;
        String recItemField;
        if(response.has("error")){
            throw new Exception((String) response.opt("error"));
        }
        else if (!response.has("recommendation")){
            throw new Exception(API_ERROR);
        }
        else {
            JSONArray items = (JSONArray) response.get("recommendation");
            for(int i=0; i < items.length(); i ++){
                recObject = new RecommendationGetResponse();
                JSONObject item = items.getJSONObject(i);
                keys = item.keys();

                while (keys.hasNext()){
                    recItemField = keys.next();
                    if(recItemField.equals("_id")){
                        //Do Nothing
                    }
                    else if(recItemField.equals("name")){
                        recObject.setProfileName(item.getString(recItemField));
                    }
                    else {
                        // iterating Field
                        JSONObject field = item.getJSONObject(recItemField);
                        Iterator<String> fieldKeys = field.keys();
                        itemMap = new HashMap<String ,Float>();
                        while(fieldKeys.hasNext()) {
                            String key = fieldKeys.next();
                            Double val = (double) field.get(key)* 100;
                            itemMap.put(key, val.floatValue());
                        }

                        recObject.addProfileEntry(recItemField, itemMap);
                    }
                }
                responseList.add(recObject);
            }
        }
        return responseList;
    }

    private class ProjectListButtonListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Button currentButton = (Button) v;
            selectedProduct = currentButton.getText().toString();
            storage.setSelectedProduct(selectedProduct);
            // Start the new Intent with the Selected Project Name
            Intent intent = new Intent(DashboardActivity.this,
                    RecommendationsActivity.class);
            startActivity(intent);
        }
    }
}

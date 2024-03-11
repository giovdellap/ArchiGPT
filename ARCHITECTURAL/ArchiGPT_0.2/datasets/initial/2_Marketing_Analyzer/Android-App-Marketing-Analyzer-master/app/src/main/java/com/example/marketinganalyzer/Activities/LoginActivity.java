package com.example.marketinganalyzer.Activities;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketinganalyzer.Models.Api.LoginPostRequest;
import com.example.marketinganalyzer.Models.Api.LoginPostResponse;
import com.example.marketinganalyzer.Models.UserCred;
import com.example.marketinganalyzer.Network.ApiUrls;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class LoginActivity extends DefaultActivity {
    protected String username;
    protected String password;
    protected TextView pendingText;
    protected UserCred currentUserCred;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Setup layout before logging in
        layoutBeforeAPICall();

        Button buttonLogin = findViewById(R.id.login_button_login);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check user inputs
                TextInputLayout userInput = findViewById(R.id.login_text_username);
                TextInputLayout passwordInput = findViewById(R.id.login_text_password);
                username = userInput.getEditText().getText().toString();
                password = passwordInput.getEditText().getText().toString();
                if (username.matches("") || password.matches("")){
                    displayToast(getString(R.string.login_toast_invalid_cred));
                }
                else {
                    //Set pending msg, update user cred and invoke API
                    pendingText = findViewById(R.id.login_text_logging_in);
                    pendingText.setVisibility(View.VISIBLE);
                    currentUserCred = new UserCred(username,password);

                    //Invoke login API
                    try {
                        invokeApi();
                    }
                    catch (Exception ex){
                        displayToast(ex.getMessage());
                    }

                }
            }
        });
    }

    protected void layoutBeforeAPICall(){
        //Show pending Text
        pendingText = findViewById(R.id.login_text_logging_in);
        pendingText.setVisibility(View.INVISIBLE);
    }

    protected void layoutOnAPIReturn(JSONObject response){
        try{
            LoginPostResponse responseObj = unmarshal(response);
            currentUserCred.setToken(responseObj.getJwt());
            storage.setUserData(currentUserCred);

            //Turn off the pending message again
            pendingText = findViewById(R.id.login_text_logging_in);
            pendingText.setVisibility(View.INVISIBLE);
            //Create the new Intent and Switch Activities
            Intent intent = new Intent(LoginActivity.this,
                    ProfilePageActivity.class);
            //intent.putExtra(EXTRA_MESSAGE, mOrderMessage);
            startActivity(intent);
            finish();


        }
        catch (Exception ex){
            displayToast(ex.getMessage());
            layoutBeforeAPICall();
        }
    }

    @Override
    protected void invokeApi() throws Exception{
        //prepare request params
        int method = Request.Method.POST;
        String url = ApiUrls.loginUrl;
        JSONObject request = marshal(new LoginPostRequest(currentUserCred.getUsername(),currentUserCred.getPassword()));
        ApiCall(method,url,request);
    }

    protected LoginPostResponse unmarshal(JSONObject response) throws Exception{
        LoginPostResponse responseObject = new LoginPostResponse();
        if(response.has("error")){
            throw new Exception((String) response.opt("error"));
        }
        else if (!response.has("jwt")){
            throw new Exception(API_ERROR);
        }
        else {
            responseObject.setJwt((String) response.opt("jwt"));
            return responseObject;
        }
    }

    protected JSONObject marshal(LoginPostRequest request) throws Exception{
        JSONObject requestObject = new JSONObject();
        try{
            requestObject.putOpt("username", request.getUsername());
            requestObject.putOpt("password", request.getPassword());
        }
        catch (Exception ex){
            throw new Exception(API_ERROR);
        }
        return requestObject;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (currentUserCred != null){
            storage.setUserData(currentUserCred);
        }
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        super.onBackPressed();
        // Start the new Intent with the Selected Project Name
        Intent intent = new Intent(LoginActivity.this,
                WelcomeActivity.class);
        startActivity(intent);
    }
}

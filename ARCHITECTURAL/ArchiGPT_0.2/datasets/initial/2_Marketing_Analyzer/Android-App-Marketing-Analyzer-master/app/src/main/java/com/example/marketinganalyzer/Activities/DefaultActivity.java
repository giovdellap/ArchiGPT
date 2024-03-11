package com.example.marketinganalyzer.Activities;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.marketinganalyzer.StoreSharedPref.StoreSharedPref;

import org.json.*;

import java.util.HashMap;
import java.util.Map;

public abstract class DefaultActivity extends AppCompatActivity {
    // Name of shared preferences file
    private final String PREF_FILE = "com.example.android.Activities";
    protected static final String PROJECT_TEMPLATE = "PROJECT_TEMPLATE";
    protected static final String NEW_PROJECT_NAME = "NEW_PROJECT_NAME";
    protected static final String SELECTED_PROJECT = "SELECTED_PROJECT";
    protected static final String API_ERROR = "Server Error is Encountered";
    protected StoreSharedPref storage;
    protected Resources resources;
    protected XmlResourceParser xmlResourceParser;
    protected RequestQueue queue;
    protected JsonObjectRequest jsonObjectRequest;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Get Storage Reference
        storage = new StoreSharedPref(getSharedPreferences(PREF_FILE,MODE_PRIVATE));
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    //new layout after getting data from the network
    protected abstract void layoutOnAPIReturn(JSONObject response);

    //layout before getting data from the network
    protected abstract void layoutBeforeAPICall();

    //prepares request, and calls the network for results
    protected abstract void invokeApi() throws Exception;

    //custom method for getting async api call results without redrawing the layout
    protected void onApiCallReturn(JSONObject response){
        //Do Nothing
    }

    protected void onApiCallError(VolleyError error){
        //Do Nothing
    }

    protected void ApiCall(int method, String url, JSONObject request){
        jsonObjectRequest = new JsonObjectRequest(method, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                layoutOnAPIReturn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Handle error
                layoutBeforeAPICall();
                displayToast(API_ERROR);
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    protected void ApiCall(int method, String url, JSONObject request, String token){
        jsonObjectRequest = new JsonObjectRequest(method, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                layoutOnAPIReturn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Handle error
                layoutBeforeAPICall();
                System.out.println("SERVER DIDNT RESPOND");
                displayToast(API_ERROR);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    protected void ApiCallNoRender(int method, String url, JSONObject request, String token){
        jsonObjectRequest = new JsonObjectRequest(method, url, request, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response){
                onApiCallReturn(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Handle error
                onApiCallError(error);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsonObjectRequest);
    }

    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}

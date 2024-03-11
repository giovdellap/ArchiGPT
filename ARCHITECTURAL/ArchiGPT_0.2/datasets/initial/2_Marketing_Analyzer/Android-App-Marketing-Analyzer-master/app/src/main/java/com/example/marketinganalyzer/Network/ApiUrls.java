package com.example.marketinganalyzer.Network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class ApiUrls {
    public static final String loginUrl = "http://10.0.2.2:5000/auth/login";
    public static final String signupUrl = "http://10.0.2.2:5000/auth/signup";
    public static final String userProjectsUrl = "http://10.0.2.2:3000/projects";
    public static final String userProjectsNewUrl = "http://10.0.2.2:3000/projects/new";
    public static final String recommendationUrl = "http://10.0.2.2:3000/recommendation/";
    public static final String likeUrl = "http://10.0.2.2:3000/likes/";

    private ApiUrls() {
    }
}

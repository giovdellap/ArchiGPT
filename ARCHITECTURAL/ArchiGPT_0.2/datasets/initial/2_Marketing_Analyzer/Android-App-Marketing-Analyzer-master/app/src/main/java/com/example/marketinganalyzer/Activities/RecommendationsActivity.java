package com.example.marketinganalyzer.Activities;

import static java.lang.Math.abs;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.Display;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextClock;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.example.marketinganalyzer.CustomViews.PieView;
import com.example.marketinganalyzer.Models.Api.ProjectNewPostRequest;
import com.example.marketinganalyzer.Models.Api.RecommendationGetResponse;
import com.example.marketinganalyzer.Models.Api.RecommendationLikeResponse;
import com.example.marketinganalyzer.Network.ApiUrls;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class RecommendationsActivity extends AnalysisResults implements SensorEventListener {
    //Main View related attributes
    protected TextView pieChartTitle;
    protected TextView productName;
    protected AttributeSet pieChartTitleAttributeSet;
    protected LinearLayout pieChartLayout;
    protected Button likeButton;
    protected Button unLikeButton;
    protected TextView margin_adapter;

    //Animation related attributes
    protected ObjectAnimator pieAnimator;
    protected float startAngle;

    //pie view related attributes
    protected PieView pieView;
    protected List<PieView> pieViewList;
    protected final Integer PIE_DIAMETER = 800;
    protected final Float PIE_ELEMENT_TEXT_SIZE = 40 * ((float)PIE_DIAMETER/700);

    //Sensor related attributes
    protected SensorManager sensorManager;
    protected float[] accelerometerData = new float[3];
    protected final float ACCELEROMETER_THRESHOLD = 5;
    protected Sensor sensorAccelerometer;

    //Concurrent Likes updates Variables
    Timer timer;
    TimerTask timerTask;
    private final int POLLING_INTERVAL_MS = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendations);

        //Init Accelerometer Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        // Init PieView list
        pieViewList = new ArrayList<>();

        // Get Project Name to Call the API.
        currentProject = storage.getSelectedProject();

        // Get Product Name to view
        selectedProduct = storage.getSelectedProduct();

        // Invoke layout before Api
        layoutBeforeAPICall();

        //FetchAPI
        try{
            //Invoke APi and register the project
            invokeApi();
        }
        catch (Exception ex){
            displayToast(ex.getMessage());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorAccelerometer != null) {
            sensorManager.registerListener(this, sensorAccelerometer, sensorManager.SENSOR_DELAY_NORMAL);
        }
        timer = new Timer();
        timerTask = new updateLikesTask();
        timer.schedule(timerTask,0,POLLING_INTERVAL_MS);
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
        timer.cancel();
    }

    @Override
    protected void layoutBeforeAPICall() {
        // Update Title
        productName = findViewById(R.id.recommendation_text_product_name);
        productName.setText(selectedProduct);

        // Update Likes and Unlikes
        likeButton = findViewById(R.id.rec_button_like);
        likeButton.setText("like");
        unLikeButton = findViewById(R.id.rec_button_unlike);
        unLikeButton.setText("unlike");


    }

    @Override
    protected void layoutOnAPIReturn(JSONObject response) {
        float startangle = 0;
        int targetIndex = 0;
        try{
            List<RecommendationGetResponse> productsRecProfiles = unmarshal(response);

            for (int i=0 ; i < productsRecProfiles.size(); i ++){
                if (selectedProduct.equals(productsRecProfiles.get(i).getProfileName())){
                    targetIndex = i;
                    break;
                }
            }

            RecommendationGetResponse productsRecProfile = productsRecProfiles.get(targetIndex);

            for(Map.Entry<String, HashMap<String,Float>> entry : productsRecProfile.getRecProfile().entrySet()){
                //Create PieChart View
                pieView = new PieView(this,entry.getValue(),
                        PIE_DIAMETER,PIE_ELEMENT_TEXT_SIZE, startangle);
                pieViewList.add(pieView);

                //Create PieChart Title
                resources = this.getResources();
                xmlResourceParser = resources.getXml(R.xml.rec_page_text_pie_chart_title);
                pieChartTitleAttributeSet = Xml.asAttributeSet(xmlResourceParser);
                pieChartTitle = new TextView(this, pieChartTitleAttributeSet);
                pieChartTitle.setTextAppearance(R.style.MainTheme_TextAppearanceTitle4);
                pieChartTitle.setText(entry.getKey());


                //Add Title and PieChart to the layout
                pieChartLayout = findViewById(R.id.rec_layout_canvas);

                margin_adapter = new TextView(this);
                margin_adapter.setText("          ");
                pieChartLayout.addView(margin_adapter);

                pieChartLayout.addView(pieChartTitle);

                margin_adapter = new TextView(this);
                margin_adapter.setText("          ");
                pieChartLayout.addView(margin_adapter);

                //pieChartLayout.addView(pieChartImgView);
                pieChartLayout.addView(pieView);
            }

            likeButton = findViewById(R.id.rec_button_like);
            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likePostApi(1);
                }
            });

            unLikeButton = findViewById(R.id.rec_button_unlike);
            unLikeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likePostApi(0);
                }
            });
        }
        catch (Exception ex){
            displayToast(ex.getMessage());
        }
    }

    protected void updateLikes(RecommendationLikeResponse likeResponse){
        // Update Likes and Unlikes
        likeButton = findViewById(R.id.rec_button_like);
        likeButton.setText("like " + String.valueOf(likeResponse.getLikes()));
        unLikeButton = findViewById(R.id.rec_button_unlike);
        unLikeButton.setText("unlike " + String.valueOf(likeResponse.getUnLikes()));
    }
    protected void likePostApi(int likeState){
        int method = Request.Method.POST;
        String url = ApiUrls.likeUrl + currentProject + "/" + selectedProduct + "/";
        if (likeState == 1){
            url += "like";
        }
        else{
            url += "unlike";
        }
        ApiCallNoRender(method, url, null, userCred.getToken());
    }

    protected void likeGetApi(){
        int method = Request.Method.GET;
        String url = ApiUrls.likeUrl + currentProject + "/" + selectedProduct;
        ApiCallNoRender(method, url, null, userCred.getToken());
    }

    @Override
    protected void onApiCallReturn(JSONObject response){
        super.onApiCallReturn(response);
        if(response.has("message")){
            //Do Nothing
        }
        else if(response.has("likes") && response.has("unLikes")){
            RecommendationLikeResponse likeResponse = new RecommendationLikeResponse((int) response.opt("likes"),
                    (int) response.opt("unLikes"));
            updateLikes(likeResponse);
        }
        else{
            displayToast(API_ERROR);
        }
    }

    @Override
    protected void onApiCallError(VolleyError error){
        super.onApiCallError(error);
        displayToast(error.toString());
    }

    @Override
    protected void invokeApi() throws Exception {
        //prepare request params
        int method = Request.Method.GET;
        String url = ApiUrls.recommendationUrl + currentProject;
        ApiCall(method,url,null, userCred.getToken());
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

    @Override
    public void onSensorChanged(SensorEvent event) {
        // Get the display from the window manager (for rotation).
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display mDisplay = wm.getDefaultDisplay();
        int sensorType = event.sensor.getType();
        float currentAcceleration = (float) 0.0;

        switch (sensorType) {
            case Sensor.TYPE_ACCELEROMETER:
                accelerometerData = event.values.clone();
                break;
            default:
                return;
        }

        switch (mDisplay.getRotation()) {
            case Surface.ROTATION_0:
            case Surface.ROTATION_180:
                currentAcceleration = accelerometerData[0];
                break;
            case Surface.ROTATION_90:
            case Surface.ROTATION_270:
                currentAcceleration = accelerometerData[1];
                break;
        }

        if ((currentAcceleration < ACCELEROMETER_THRESHOLD &&
                currentAcceleration >- ACCELEROMETER_THRESHOLD) || (pieAnimator != null &&
                pieAnimator.isRunning())){
            return;
        }
        else {
            //Get Data from sensor
            List<Long> kinematicsValues = ApplyKinematics(startAngle,(float) PIE_DIAMETER/2 ,currentAcceleration);
            long duration = kinematicsValues.get(0) * 1000;
            float endAngle = (float) kinematicsValues.get(1);

            //Initialize Animator
            for(PieView currentPieView : pieViewList){
                pieAnimator = ObjectAnimator.ofFloat(currentPieView, "startAngle",
                        startAngle,endAngle);
                pieAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                pieAnimator.setDuration(duration);
                pieAnimator.start();
            }

            //Set start angle to end angle
            startAngle = endAngle;
        }
    }

    private List<Long> ApplyKinematics(Float startAngle, Float sphereRadius, Float acceleration){
        final Float VELOCITY = (float) 25;
        List<Long> retVal = new ArrayList<>();

        //the result will be multiplied by 2 for acc/dec
        Long duration = (long) (VELOCITY / acceleration);

        //intermediate angle calc
        Long angularVelocity = (long)(VELOCITY * 1000/sphereRadius);
        Long angularAcceleration = (long)(acceleration* 1000/sphereRadius);
        Long intermediateAngle = (long) (startAngle + 0.5 * angularAcceleration * Math.pow(duration,2));

        //end distance calc from intermediate point
        Long endAngle = (long)(intermediateAngle + angularVelocity*duration + (0.5 * -angularAcceleration * Math.pow(duration,2)));

        retVal.add(abs(duration * 2));
        retVal.add(endAngle);
        return retVal;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class updateLikesTask extends TimerTask{

        @Override
        public void run() {
            likeGetApi();
        }
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ir.shenakht.paint.domain.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hossien
 */
public class SendMessageWithParse {

    protected static ObjectMapper mapper;

//    public static void sendNotificationToAllDeviceAndAllUsers(String content, App app) {
//        String channelName = "apora" + app.getAppCode();
//        String[] channels = new String[]{channelName};
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("alert", content);
//        data.put("type", "notification");
//
//        try {
//            new SendMessageWithParse().sendPost(channels, null, data, app);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendNotificationToAllDeviceAndUserSelection(Notification notification, App app, List<User> users) throws JSONException {
//        Map<String, String> data = new HashMap<String, String>();
//        JSONObject jo = new JSONObject();
//        JSONObject where = new JSONObject();
//        JSONObject installationId = new JSONObject();
//        ArrayList<String> arrayRgId = new ArrayList<>();
//
//        String channelName = "apora" + app.getAppCode();
//        String[] channels = new String[]{channelName};
//
//        data.put("alert", notification.getContent());
//        data.put("type", "notification");
//
//        for (User user : users) {
//            if (user.getInstallationId() != null) {
//                arrayRgId.add(user.getInstallationId());
//            }
//        }
//
//        installationId.put("$in", arrayRgId);
//        where.put("installationId", installationId);
//        jo.put("where", where);
//        jo.put("data", data);
//        jo.put("channels", channels);
//        try {
//            new SendMessageWithParse().pushData(jo.toString(), app);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendMessageToAllDeviceAndUserSelection(String message, App app) throws JsonProcessingException {
//        try {
//            new SendMessageWithParse().pushData(message, app);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendToDeviceAndroid(String content, App app) {
//        String channelName = "apora" + app.getAppCode();
//        String[] channels = new String[]{channelName};
//        String type = "android";
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("alert", content);
//
//        try {
//            new SendMessageWithParse().sendPost(channels, type, data, app);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static void sendToDeviceIOS(String content, App app) {
//        String channelName = "apora" + app.getAppCode();
//        String[] channels = new String[]{channelName};
//        String type = "ios";
//        Map<String, String> data = new HashMap<String, String>();
//        data.put("alert", content);
//
//        try {
//            new SendMessageWithParse().sendPost(channels, type, data, app);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void sendPost(String[] channels, String type, Map<String, String> data, App app) throws Exception {
//        JSONObject jo = new JSONObject();
//        jo.put("channels", channels);
//        if (type != null) {
//            //??type?????android?ios???
//            jo.put("type", type);
//        }
//        jo.put("data", data);
//        this.pushData(jo.toString(), app);
//    }
//
//    private void pushData(String postData, App app) throws Exception {
//        DefaultHttpClient httpclient = new DefaultHttpClient();
//        HttpResponse response = null;
//        HttpEntity entity = null;
//        String responseString = null;
//        HttpPost httpost = new HttpPost(ConstantValues.AddressServers.Parse.PUSH_URL + app.getId() + "/push");
//        httpost.addHeader("X-Parse-Application-Id", app.getAppCode());
//        httpost.addHeader("X-Parse-REST-API-Key", ConstantValues.AddressServers.Parse.REST_API_KEY);
//        httpost.addHeader("Content-Type", "application/json");
//        httpost.addHeader("X-Parse-Master-Key", ConstantValues.AddressServers.Parse.MASTER_KEY);
//        StringEntity reqEntity = new StringEntity(postData, HTTP.UTF_8);
//        httpost.setEntity(reqEntity);
//        response = httpclient.execute(httpost);
//        entity = response.getEntity();
//        if (entity != null) {
//            responseString = EntityUtils.toString(response.getEntity());
//        }
//        System.out.println(responseString);
//    }
}

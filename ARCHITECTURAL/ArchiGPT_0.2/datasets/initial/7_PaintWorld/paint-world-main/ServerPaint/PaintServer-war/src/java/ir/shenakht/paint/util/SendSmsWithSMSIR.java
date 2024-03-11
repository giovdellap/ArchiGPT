/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.io.IOException;
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
public class SendSmsWithSMSIR {

    public static void SendMessage(String phone, String code) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseString = null;
        HttpPost httpost = new HttpPost(ConstantValues.SmsInfo.ADDRESS_VERFICATION_DEFAULT);
        httpost.addHeader("Content-Type", "application/json");
        httpost.addHeader("x-sms-ir-secure-token", getTokenSmsIr());
        JSONObject jo = new JSONObject();
        jo.put("code", code);
        jo.put("MobileNumber", phone);
        StringEntity reqEntity = new StringEntity(jo.toString(), HTTP.UTF_8);
        httpost.setEntity(reqEntity);
        response = httpclient.execute(httpost);
        entity = response.getEntity();
        if (entity != null) {
            responseString = EntityUtils.toString(response.getEntity());
        }
        System.out.println(responseString);
    }

    private static String getTokenSmsIr() throws JSONException, IOException {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseString = null;
        HttpPost httpost = new HttpPost(ConstantValues.SmsInfo.ADDRESS_GET_TOKEN);
        httpost.addHeader("Content-Type", "application/json");
        JSONObject jo = new JSONObject();
        jo.put("UserApiKey", ConstantValues.SmsInfo.USER_API_KEY);
        jo.put("SecretKey", ConstantValues.SmsInfo.SECRET_KEY);
        StringEntity reqEntity = new StringEntity(jo.toString(), HTTP.UTF_8);
        httpost.setEntity(reqEntity);
        response = httpClient.execute(httpost);
        entity = response.getEntity();
        if (entity != null) {
            responseString = EntityUtils.toString(response.getEntity());
        }
        jo = new JSONObject(responseString);
        System.out.println(jo.get("TokenKey"));
        return jo.get("TokenKey").toString();
    }
}

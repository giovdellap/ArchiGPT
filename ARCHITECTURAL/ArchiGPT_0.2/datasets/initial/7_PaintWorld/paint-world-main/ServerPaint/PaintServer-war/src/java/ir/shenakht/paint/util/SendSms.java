/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author hossien
 */
public class SendSms {

    public static void SendMessage(String phone, String text) throws Exception {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpResponse response = null;
        HttpEntity entity = null;
        String responseString = null;
        HttpPost httpost = new HttpPost(ConstantValues.SmsInfo.ADDRESS_SMS_PANEL);

        List<NameValuePair> urlParameters = new ArrayList<NameValuePair>();
        urlParameters.add(new BasicNameValuePair("user", ConstantValues.SmsInfo.USER));
        urlParameters.add(new BasicNameValuePair("pass", ConstantValues.SmsInfo.PASSWORD));
        urlParameters.add(new BasicNameValuePair("lineNo", ConstantValues.SmsInfo.LINE_NO));
        urlParameters.add(new BasicNameValuePair("to", phone));
        urlParameters.add(new BasicNameValuePair("text", text));
        httpost.setEntity(new UrlEncodedFormEntity(urlParameters, "UTF-8"));
        response = httpclient.execute(httpost);
        entity = response.getEntity();
        if (entity != null) {
            responseString = EntityUtils.toString(response.getEntity());
        }
        System.out.println(responseString);
    }

}

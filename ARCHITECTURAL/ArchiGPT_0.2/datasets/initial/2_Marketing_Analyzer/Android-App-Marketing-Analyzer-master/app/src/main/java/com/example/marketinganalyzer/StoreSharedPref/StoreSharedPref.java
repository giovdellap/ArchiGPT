package com.example.marketinganalyzer.StoreSharedPref;

import android.content.SharedPreferences;

import com.example.marketinganalyzer.Models.UserCred;

public class StoreSharedPref {
    private SharedPreferences mPreferences;

    public StoreSharedPref(SharedPreferences mPreferences) {
        this.mPreferences = mPreferences;
    }

    public UserCred getUserData(){
        String username = mPreferences.getString("USER", null);
        String password = mPreferences.getString("PASS", null);
        String token = mPreferences.getString("TOKEN", null);
        return new UserCred(username,password,token);
    }

    public String getUserName(){
        return mPreferences.getString("USER", null);
    }

    public void setUserData(UserCred userData){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("USER", userData.getUsername());
        editor.putString("PASS", userData.getPassword());
        editor.putString("TOKEN", userData.getToken());
        editor.apply();
    }

    public void setSelectedProject(String selectedProject){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("SELECTED_PROJECT", selectedProject);
        editor.apply();
    }

    public void setSelectedProduct(String selectedProduct){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString("SELECTED_PRODUCT", selectedProduct);
        editor.apply();
    }

    public String getSelectedProduct(){
        return mPreferences.getString("SELECTED_PRODUCT", null);
    }
    public String getSelectedProject(){
        return mPreferences.getString("SELECTED_PROJECT", null);
    }

    public void clearData(){
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.clear();
        editor.apply();
    }
}

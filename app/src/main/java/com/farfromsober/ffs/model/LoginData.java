package com.farfromsober.ffs.model;

import android.util.Base64;

import java.util.HashMap;

/**
 * Created by joanbiscarri on 30/11/15.
 */
public class LoginData {

    private static final String LOGIN_KEY = "user";
    private static final String PASSWORD_KEY = "password";

    private String mLogin;
    private String mPassword;


    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(LOGIN_KEY, getLogin());
        hashMap.put(PASSWORD_KEY, getPassword());

        return hashMap;
    }

    public HashMap<String, Object> getHeaders() {
        String userPassword = getLogin()+":"+getPassword();
        final String basicAuth = "Basic " + Base64.encodeToString(userPassword.getBytes(), Base64.NO_WRAP);
        //Basic am9hbjpLZWVwQ29kaW5n
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("Authorization", basicAuth);
        hashMap.put("Content-Type", "application/json");

       return hashMap;
    }

    public LoginData(String login, String password) {
        this.mLogin = login;
        this.mPassword = password;
    }


    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }
}

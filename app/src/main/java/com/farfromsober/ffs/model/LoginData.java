package com.farfromsober.ffs.model;

import java.util.HashMap;

/**
 * Created by joanbiscarri on 30/11/15.
 */
public class LoginData {

    private static final String LOGIN_KEY = "login";
    private static final String PASSWORD_KEY = "password";

    private String mLogin;
    private String mPassword;


    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(LOGIN_KEY, getLogin());
        hashMap.put(PASSWORD_KEY, getPassword());

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

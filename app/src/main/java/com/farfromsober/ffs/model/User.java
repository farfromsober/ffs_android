package com.farfromsober.ffs.model;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

public class User {

    private static final String ID_KEY = "_id";
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String EMAIL_KEY = "email";
    private static final String USERNAME_KEY = "username";
    private static final String LATITUDE_KEY = "latitude";
    private static final String LONGITUDE_KEY = "longitude";
    private static final String AVATAR_KEY = "avatar";
    private static final String CITY_KEY = "city";
    private static final String STATE_KEY = "state";
    private static final String SALES_KEY = "sales";

    private String mUserId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mUsername;
    private String mLatitude;
    private String mLongitude;
    private String mAvatarURL;
    private String mCity;
    private String mState;
    private double mSales;

    public User(String userId, String firstName, String lastName, String email, String username, String latitude,
                String longitude, String avatarURL, String city, String state, double sales) {
        mUserId = userId;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mUsername = username;
        mLatitude = latitude;
        mLongitude = longitude;
        mAvatarURL = avatarURL;
        mCity = city;
        mState = state;
        mSales = sales;
    }

    public User(JSONObject json) {
        mUserId = json.optString(ID_KEY);
        mFirstName = json.optString(FIRST_NAME_KEY);
        mLastName = json.optString(LAST_NAME_KEY);
        mEmail = json.optString(EMAIL_KEY);
        mUsername = json.optString(USERNAME_KEY);
        mLatitude = json.optString(LATITUDE_KEY);
        mLongitude = json.optString(LONGITUDE_KEY);
        mAvatarURL = json.optString(AVATAR_KEY);
        mCity = json.optString(CITY_KEY);
        mState = json.optString(STATE_KEY);
        mSales = json.optDouble(SALES_KEY);
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public void setFirstName(String firstName) {
        mFirstName = firstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lastName) {
        mLastName = lastName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getLatitude() {
        return mLatitude;
    }

    public void setLatitude(String latitude) {
        mLatitude = latitude;
    }

    public String getLongitude() {
        return mLongitude;
    }

    public void setLongitude(String longitude) {
        mLongitude = longitude;
    }

    public String getAvatarURL() {
        return mAvatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        mAvatarURL = avatarURL;
    }

    public String getCity() {
        return mCity;
    }

    public void setCity(String city) {
        mCity = city;
    }

    public String getState() {
        return mState;
    }

    public void setState(String state) {
        mState = state;
    }

    public double getSales() {
        return mSales;
    }

    public void setSales(double sales) {
        mSales = sales;
    }

    @Override
    public String toString() {
        return "User{" +
                "mUserId='" + mUserId + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mAvatarURL='" + mAvatarURL + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mSales=" + mSales +
                '}';
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ID_KEY, getUserId());
        hashMap.put(FIRST_NAME_KEY, getFirstName());
        hashMap.put(LAST_NAME_KEY, getLastName());
        hashMap.put(EMAIL_KEY, getEmail());
        hashMap.put(USERNAME_KEY, getUsername());
        hashMap.put(LATITUDE_KEY, getLatitude());
        hashMap.put(LONGITUDE_KEY, getLongitude());
        hashMap.put(AVATAR_KEY, getAvatarURL());
        hashMap.put(CITY_KEY, getCity());
        hashMap.put(STATE_KEY, getState());
        hashMap.put(SALES_KEY, getSales());

        return hashMap;
    }
}

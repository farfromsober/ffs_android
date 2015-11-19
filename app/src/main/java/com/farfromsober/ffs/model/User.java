package com.farfromsober.ffs.model;

import org.json.JSONObject;

import java.net.URL;

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
    private static final String IS_ACTIVE_KEY = "is_active";

    private String mId;
    private String mFirstName;
    private String mLastName;
    private String mEmail;
    private String mUsername;
    private String mLatitude;
    private String mLongitude;
    private String mAvatar;
    private String mCity;
    private String mState;
    private double mSales;
    private boolean mIsActive;

    public User(String id, String firstName, String lastName, String email, String username, String latitude,
                String longitude, String avatar, String city, String state, double sales, boolean isActive) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
        mEmail = email;
        mUsername = username;
        mLatitude = latitude;
        mLongitude = longitude;
        mAvatar = avatar;
        mCity = city;
        mState = state;
        mSales = sales;
        mIsActive = isActive;
    }

    public User(JSONObject json) {
        mId = json.optString(ID_KEY);
        mFirstName = json.optString(FIRST_NAME_KEY);
        mLastName = json.optString(LAST_NAME_KEY);
        mEmail = json.optString(EMAIL_KEY);
        mUsername = json.optString(USERNAME_KEY);
        mLatitude = json.optString(LATITUDE_KEY);
        mLongitude = json.optString(LONGITUDE_KEY);
        mAvatar = json.optString(AVATAR_KEY);
        mCity = json.optString(CITY_KEY);
        mState = json.optString(STATE_KEY);
        mSales = json.optDouble(SALES_KEY);
        mIsActive = json.optBoolean(IS_ACTIVE_KEY);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
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

    public boolean isActive() {
        return mIsActive;
    }

    public void setIsActive(boolean isActive) {
        mIsActive = isActive;
    }

    @Override
    public String toString() {
        return "User{" +
                "mId='" + mId + '\'' +
                ", mFirstName='" + mFirstName + '\'' +
                ", mLastName='" + mLastName + '\'' +
                ", mEmail='" + mEmail + '\'' +
                ", mUsername='" + mUsername + '\'' +
                ", mLatitude='" + mLatitude + '\'' +
                ", mLongitude='" + mLongitude + '\'' +
                ", mAvatar='" + mAvatar + '\'' +
                ", mCity='" + mCity + '\'' +
                ", mState='" + mState + '\'' +
                ", mSales=" + mSales +
                ", mIsActive=" + mIsActive +
                '}';
    }
}

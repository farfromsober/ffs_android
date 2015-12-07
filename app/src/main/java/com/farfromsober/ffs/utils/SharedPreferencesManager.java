package com.farfromsober.ffs.utils;

import android.content.Context;

import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.ffs.model.User;
import com.farfromsober.generalutils.SharedPreferencesGeneralManager;

public class SharedPreferencesManager {


    //region Keys
    private static final String PREF_LOGIN_USER = "com.farfromsober.generalutils.SharedPreferencesGeneralManager.PREF_LOGIN_USER";
    private static final String PREF_USER_DATA = "com.farfromsober.generalutils.SharedPreferencesGeneralManager.PREF_USER_DATA";

    //endregion


    //LOGIN USER
    public static void savePrefLoginUser(Context context, LoginData value) {
        String loginDataString = SharedPreferencesGeneralManager.objectToJSONString(value);
        SharedPreferencesGeneralManager.savePreferece(context, PREF_LOGIN_USER, loginDataString);
    }

    //public static String getPrefLoginUserString(Context context) {
    //    return SharedPreferencesGeneralManager.getPreferenceString(context, PREF_LOGIN_USER);
    //}

    public static LoginData getPrefLoginUser(Context context) {
        String loginDataString = SharedPreferencesGeneralManager.getPreferenceString(context, PREF_LOGIN_USER);
        LoginData loginData = (LoginData) SharedPreferencesGeneralManager.JSONStringToObject(loginDataString, LoginData.class);
        return loginData;
    }

    public static void removePrefLoginUser(Context context) {
        SharedPreferencesGeneralManager.removePreference(context, PREF_LOGIN_USER);
    }

    //USER DATA
    public static void savePrefUserData(Context context, User user) {
        String userJson = SharedPreferencesGeneralManager.objectToJSONString(user);
        SharedPreferencesGeneralManager.savePreferece(context, PREF_USER_DATA, userJson);
    }

    public static User getPrefUserData(Context context) {
        String UserJson =  SharedPreferencesGeneralManager.getPreferenceString(context, PREF_USER_DATA);
        return (User) SharedPreferencesGeneralManager.JSONStringToObject(UserJson, User.class);
    }

    public static void removePrefUserData(Context context) {
        SharedPreferencesGeneralManager.removePreference(context, PREF_USER_DATA);
    }


}

package com.farfromsober.generalutils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

public class SharedPreferencesGeneralManager {

    public static final String DEFAULT_STRING_VALUE = "";
    public static final int DEFAULT_INT_VALUE = Integer.MIN_VALUE;
    public static final long DEFAULT_LONG_VALUE = Long.MIN_VALUE;
    public static final boolean DEFAULT_BOOLEAN_VALUE = false;

    //region Save Preferences
    public static void savePreferece(Context context, String key, String value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putString(key, value).apply();
    }

    public static void savePreferece(Context context, String key, int value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putInt(key, value).apply();
    }

    public static void savePreferece(Context context, String key, long value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putLong(key, value).apply();
    }

    public static void savePreferece(Context context, String key, boolean value) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().putBoolean(key, value).apply();
    }
    //endregion


    //region Get Preferences
    public static String getPreferenceString(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(key, DEFAULT_STRING_VALUE);
    }

    public static int getPreferenceInt(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, DEFAULT_INT_VALUE);
    }

    public static long getPreferenceLong(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getLong(key, DEFAULT_LONG_VALUE);
    }

    public static boolean getPreferenceBoolean(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getBoolean(key, DEFAULT_BOOLEAN_VALUE);
    }
    //endregion



    //region Remove Preferences
    public static void removePreference(Context context, String key) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().remove(key).apply();
    }
    //endregion


    //region Save/Get custom objects in/from SharedPreferences (as a JSON String)
    public static String objectToJSONString (Object myObject) {
        Gson gson = new Gson();
        return gson.toJson(myObject);
    }

    public static Object JSONStringToObject (String JSONString, Class<?> modelClass) {
        Gson gson = new Gson();
        return gson.fromJson(JSONString, modelClass);
    }
    //endregion
}
package com.farfromsober.ffs.utils;

import android.content.Context;

import com.farfromsober.generalutils.SharedPreferencesGeneralManager;

public class SharedPreferencesManager {


    //region Keys
    private static final String PREF_LOGIN_USER = "com.farfromsober.generalutils.SharedPreferencesGeneralManager.PREF_LOGIN_USER";
    //endregion


    //region Save Preferences
    public static void savePrefLoginUser(Context context, String value) {
        SharedPreferencesGeneralManager.savePreferece(context, PREF_LOGIN_USER, value);
    }
    //endregion


    //region Save Preferences
    public static String getPrefLoginUser(Context context) {
        return SharedPreferencesGeneralManager.getPreferenceString(context, PREF_LOGIN_USER);
    }
    //endregion


    // region Remove Preferences
    public static void removePrefLoginUser(Context context) {
        SharedPreferencesGeneralManager.removePreference(context, PREF_LOGIN_USER);
    }
    //endregion
}

package com.farfromsober.ffs.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FakeModelObjectsHelper {

    public static double CATEGORY_INDEX = 0;
    public static double CATEGORY_DEFAULT_INDEX = 0;
    public static String CATEGORY_NAME = "Category 1";

    private static String jsonCategory =   "{\"index\":" + CATEGORY_INDEX + "," +
                                            "\"name\":\"" + CATEGORY_NAME + "\"}";

    private static String jsonCategoryWithoutName =   "{\"index\": 0\"}";

    private static String jsonCategoryWithoutIndex =   "{\"name\": \"Category 1\"}";



    public static JSONObject fakeJSONCategory() throws JSONException {
        return new JSONObject(jsonCategory);
    }
    public static JSONObject fakeJSONCategoryWithoutIndex() throws JSONException {
        return new JSONObject(jsonCategoryWithoutIndex);
    }
    public static JSONObject fakeJSONCategoryWithoutName() throws JSONException {
        return new JSONObject(jsonCategoryWithoutName);
    }
}

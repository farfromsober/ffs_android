package com.farfromsober.ffs.model;

import org.json.JSONException;
import org.json.JSONObject;

public class FakeModelObjectsHelper {

    private static String JSON_STARTING_STRING = "{";
    private static String JSON_KEY_VALUE_SEPARATOR_STRING = ":";
    private static String JSON_FIELD_SEPARATOR_STRING = ",";
    private static String JSON_ENDING_STRING = "}";


    public static double DOUBLE_DEFAULT_VALUE = 0;


    public static String CATEGORY_INDEX_KEY = "\"index\"";
    public static double CATEGORY_INDEX_VALUE = 0;
    public static String CATEGORY_NAME_KEY = "\"name\"";
    public static String CATEGORY_NAME_VALUE = "Category 1";

    private static String stringValue (String value) {
        return "\"" + value + "\"";
    }

    private static String CATEGORY_INDEX_JSON_STRING = CATEGORY_INDEX_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + CATEGORY_INDEX_VALUE;
    private static String CATEGORY_NAME_JSON_STRING = CATEGORY_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(CATEGORY_NAME_VALUE);

    private static String jsonCategory =    JSON_STARTING_STRING +
                                            CATEGORY_INDEX_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                            CATEGORY_NAME_JSON_STRING +
                                            JSON_ENDING_STRING;

    private static String jsonCategoryWithoutName =     JSON_STARTING_STRING +
                                                        CATEGORY_INDEX_JSON_STRING +
                                                        JSON_ENDING_STRING;

    private static String jsonCategoryWithoutIndex =    JSON_STARTING_STRING +
                                                        CATEGORY_NAME_JSON_STRING +
                                                        JSON_ENDING_STRING;



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

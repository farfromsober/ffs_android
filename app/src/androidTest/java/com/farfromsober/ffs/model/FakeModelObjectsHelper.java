package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class FakeModelObjectsHelper {

    private static final String JSON_STARTING_STRING = "{";
    private static final String JSON_KEY_VALUE_SEPARATOR_STRING = ":";
    private static final String JSON_FIELD_SEPARATOR_STRING = ",";
    private static final String JSON_ENDING_STRING = "}";


    public static final int INT_DEFAULT_VALUE = 0;
    public static final double DOUBLE_DEFAULT_VALUE = 0;

    private static String stringValue (String value) {
        return "\"" + value + "\"";
    }



    private static final String CATEGORY_INDEX_KEY = "index";
    public static final int CATEGORY_INDEX_VALUE = 0;
    private static final String CATEGORY_NAME_KEY = "name";
    public static final String CATEGORY_NAME_VALUE = "Category 1";

    private static final String CATEGORY_INDEX_JSON_STRING = CATEGORY_INDEX_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + CATEGORY_INDEX_VALUE;
    private static final String CATEGORY_NAME_JSON_STRING = CATEGORY_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(CATEGORY_NAME_VALUE);

    private static final String jsonCategory =    JSON_STARTING_STRING +
                                            CATEGORY_INDEX_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                            CATEGORY_NAME_JSON_STRING +
                                            JSON_ENDING_STRING;

    private static final String jsonCategoryWithoutName =     JSON_STARTING_STRING +
                                                        CATEGORY_INDEX_JSON_STRING +
                                                        JSON_ENDING_STRING;

    private static final String jsonCategoryWithoutIndex =    JSON_STARTING_STRING +
                                                        CATEGORY_NAME_JSON_STRING +
                                                        JSON_ENDING_STRING;

    public static JSONObject fakeJSONCategory() {
        try {
            return new JSONObject(jsonCategory);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject fakeJSONCategoryWithoutIndex() {
        try {
            return new JSONObject(jsonCategoryWithoutIndex);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject fakeJSONCategoryWithoutName() {
        try {
            return new JSONObject(jsonCategoryWithoutName);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }




    private static final String USER_USER_KEY = "user";
    private static final String USER_USER_ID_KEY = "id";
    public static final int USER_USER_ID_VALUE = 5;
    private static final String USER_FIRST_NAME_KEY = "first_name";
    public static final String USER_FIRST_NAME_VALUE = "Javier";
    private static final String USER_LAST_NAME_KEY = "last_name";
    public static final String USER_LAST_NAME_VALUE = "Alzueta";
    private static final String USER_EMAIL_KEY = "email";
    public static final String USER_EMAIL_VALUE = "jalzuetap@gmail.com";
    private static final String USER_USERNAME_KEY = "username";
    public static final String USER_USERNAME_VALUE = "javi";

    private static final String USER_PROFILE_ID_KEY = "id";
    public static final int USER_PROFILE_ID_VALUE = 3;
    private static final String USER_LATITUDE_KEY = "latitude";
    public static final String USER_LATITUDE_VALUE = "42.813604";
    private static final String USER_LONGITUDE_KEY = "longitude";
    public static final String USER_LONGITUDE_VALUE = "-1.646247";
    private static final String USER_AVATAR_KEY = "avatar";
    public static final String USER_AVATAR_VALUE = "";
    private static final String USER_CITY_KEY = "city";
    public static final String USER_CITY_VALUE = "Pamplona";
    private static final String USER_STATE_KEY = "state";
    public static final String USER_STATE_VALUE = "Navarra";
    private static final String USER_SALES_KEY = "sales";
    public static final int USER_SALES_VALUE = 0;


    private static final String USER_USER_VALUE =   JSON_STARTING_STRING +
                                                    USER_USER_ID_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_ID_VALUE + JSON_FIELD_SEPARATOR_STRING +
                                                    USER_USERNAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_USERNAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                    USER_FIRST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_FIRST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                    USER_LAST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_LAST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                    USER_EMAIL_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_EMAIL_VALUE) +
                                                    JSON_ENDING_STRING;

    private static final String USER_USER_VALUE_WITH_NO_USER_NAME =     JSON_STARTING_STRING +
                                                                        USER_USER_ID_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_ID_VALUE + JSON_FIELD_SEPARATOR_STRING +
                                                                        //USER_USERNAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + "" + JSON_FIELD_SEPARATOR_STRING +
                                                                        USER_FIRST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_FIRST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                                        USER_LAST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_LAST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                                        USER_EMAIL_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_EMAIL_VALUE) +
                                                                        JSON_ENDING_STRING;

    private static final String USER_USER_VALUE_WITH_NO_EMAIL =     JSON_STARTING_STRING +
                                                                    USER_USER_ID_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_ID_VALUE + JSON_FIELD_SEPARATOR_STRING +
                                                                    USER_USERNAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_USERNAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                                    USER_FIRST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_FIRST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                                    USER_LAST_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_LAST_NAME_VALUE) + JSON_FIELD_SEPARATOR_STRING +
                                                                    //USER_EMAIL_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + "" +
                                                                    JSON_ENDING_STRING;

    private static final String USER_USER_JSON_STRING = USER_USER_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_VALUE;
    private static final String USER_USER_JSON_STRING_WITH_NO_USER_NAME = USER_USER_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_VALUE_WITH_NO_USER_NAME;
    private static final String USER_USER_JSON_STRING_WITH_NO_EMAIL = USER_USER_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_USER_VALUE_WITH_NO_EMAIL;

    private static final String USER_ID_JSON_STRING = USER_PROFILE_ID_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_PROFILE_ID_VALUE;
    private static final String USER_AVATAR_JSON_STRING = USER_AVATAR_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_AVATAR_VALUE);
    private static final String USER_LATITUDE_JSON_STRING = USER_LATITUDE_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_LATITUDE_VALUE);
    private static final String USER_LONGITUDE_JSON_STRING = USER_LONGITUDE_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_LONGITUDE_VALUE);
    private static final String USER_CITY_JSON_STRING = USER_CITY_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_CITY_VALUE);
    private static final String USER_STATE_JSON_STRING = USER_STATE_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(USER_STATE_VALUE);
    private static final String USER_SALES_JSON_STRING = USER_SALES_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + USER_SALES_VALUE;

    private static final String jsonUser =   JSON_STARTING_STRING +
            USER_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
            USER_USER_JSON_STRING +
//            USER_AVATAR_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
//            USER_LATITUDE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
//            USER_LONGITUDE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
//            USER_CITY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
//            USER_STATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
//            USER_SALES_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
            JSON_ENDING_STRING;

    private static final String jsonUserWithNoUserId =   JSON_STARTING_STRING +
            USER_USER_JSON_STRING +
            JSON_ENDING_STRING;

    private static final String jsonUserWithNoUserName =   JSON_STARTING_STRING +
            USER_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
            USER_USER_JSON_STRING_WITH_NO_USER_NAME +
            JSON_ENDING_STRING;

    private static final String jsonUserWithNoEmail =   JSON_STARTING_STRING +
            USER_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
            USER_USER_JSON_STRING_WITH_NO_EMAIL +
            JSON_ENDING_STRING;


    public static JSONObject fakeJSONUser() {
        try {
            return new JSONObject(jsonUser);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject fakeJSONUserWithNoUserId() {
        try {
            return new JSONObject(jsonUserWithNoUserId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject fakeJSONUserWithNoUserName() {
        try {
            return new JSONObject(jsonUserWithNoUserName);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static JSONObject fakeJSONUserWithNoEmail() {
        try {
            return new JSONObject(jsonUserWithNoEmail);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }


    private static final String PRODUCT_ID_KEY = "id";
    public static final String PRODUCT_ID_VALUE = "1";
    private static final String PRODUCT_NAME_KEY = "name";
    public static final String PRODUCT_NAME_VALUE = "Producto 1";
    private static final String PRODUCT_DESCRIPTION_KEY = "description";
    public static final String PRODUCT_DESCRIPTION_VALUE = "Descripcion producto 1";
    private static final String PRODUCT_PUBLISHED_DATE_KEY = "published_date";
    public static final Date PRODUCT_PUBLISHED_DATE_VALUE = new Date();
    public static final String PRODUCT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
    private static final String PRODUCT_SELLING_KEY = "selling";
    public static final boolean PRODUCT_SELLING_VALUE = true;
    private static final String PRODUCT_PRICE_KEY = "price";
    public static final String PRODUCT_PRICE_VALUE = "54";
    private static final String PRODUCT_SELLER_KEY = "seller";
    public static final String PRODUCT_SELLER_VALUE = jsonUser;
    private static final String PRODUCT_CATEGORY_KEY = "category";
    public static final String PRODUCT_CATEGORY_VALUE = jsonCategory;
    private static final String PRODUCT_IMAGES_KEY = "images";
    public static final String PRODUCT_IMAGES_VALUE = "[]";


    private static final String PRODUCT_ID_JSON_STRING = PRODUCT_ID_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(PRODUCT_ID_VALUE);
    private static final String PRODUCT_NAME_JSON_STRING = PRODUCT_NAME_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(PRODUCT_NAME_VALUE);
    private static final String PRODUCT_DETAIL_JSON_STRING = PRODUCT_DESCRIPTION_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(PRODUCT_DESCRIPTION_VALUE);
    private static final String PRODUCT_PUBLISHED_DATE_JSON_STRING = PRODUCT_PUBLISHED_DATE_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(DateManager.stringFromDate(PRODUCT_PUBLISHED_DATE_VALUE, PRODUCT_DATE_FORMAT));
    private static final String PRODUCT_SELLING_JSON_STRING = PRODUCT_SELLING_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + PRODUCT_SELLING_VALUE;
    private static final String PRODUCT_PRICE_JSON_STRING = PRODUCT_PRICE_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(PRODUCT_PRICE_VALUE);
    private static final String PRODUCT_SELLER_JSON_STRING = PRODUCT_SELLER_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + PRODUCT_SELLER_VALUE;
    private static final String PRODUCT_CATEGORY_JSON_STRING = PRODUCT_CATEGORY_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + PRODUCT_CATEGORY_VALUE;
    private static final String PRODUCT_IMAGES_JSON_STRING = PRODUCT_IMAGES_KEY + JSON_KEY_VALUE_SEPARATOR_STRING + stringValue(PRODUCT_IMAGES_VALUE);

    private static final String jsonProduct =   JSON_STARTING_STRING +
                                                PRODUCT_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_NAME_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_DETAIL_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_PUBLISHED_DATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_SELLING_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_PRICE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_SELLER_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_CATEGORY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                PRODUCT_IMAGES_JSON_STRING +
                                                JSON_ENDING_STRING;

    private static final String jsonProductWithNoProductId =    JSON_STARTING_STRING +
                                                                //PRODUCT_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_NAME_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_DETAIL_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_PUBLISHED_DATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_SELLING_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_PRICE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_SELLER_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_CATEGORY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                                PRODUCT_IMAGES_JSON_STRING +
                                                                JSON_ENDING_STRING;

    private static final String jsonProductWithNoName = JSON_STARTING_STRING +
                                                        PRODUCT_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        //PRODUCT_NAME_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_DETAIL_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_PUBLISHED_DATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_SELLING_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_PRICE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_SELLER_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_CATEGORY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                        PRODUCT_IMAGES_JSON_STRING +
                                                        JSON_ENDING_STRING;

    private static final String jsonProductWithNoPrice =    JSON_STARTING_STRING +
                                                            PRODUCT_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_NAME_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_DETAIL_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_PUBLISHED_DATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_SELLING_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            //PRODUCT_PRICE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_SELLER_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_CATEGORY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_IMAGES_JSON_STRING +
                                                            JSON_ENDING_STRING;

    private static final String jsonProductWithNoSeller =   JSON_STARTING_STRING +
                                                            PRODUCT_ID_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_NAME_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_DETAIL_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_PUBLISHED_DATE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_SELLING_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_PRICE_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            //PRODUCT_SELLER_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_CATEGORY_JSON_STRING + JSON_FIELD_SEPARATOR_STRING +
                                                            PRODUCT_IMAGES_JSON_STRING +
                                                            JSON_ENDING_STRING;



    public static JSONObject fakeJSONProduct() {
        try {
            return new JSONObject(jsonProduct);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject fakeJSONProductWithNoProductId() {
        try {
            return new JSONObject(jsonProductWithNoProductId);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject fakeJSONProductWithNoName() {
        try {
            return new JSONObject(jsonProductWithNoName);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject fakeJSONProductWithNoPrice() {
        try {
            return new JSONObject(jsonProductWithNoPrice);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static JSONObject fakeJSONProductWithNoSeller() {
        try {
            return new JSONObject(jsonProductWithNoSeller);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}

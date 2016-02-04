package com.farfromsober.network;

import android.support.annotation.NonNull;

import com.farfromsober.network.callbacks.OnDataParsedCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtils {

    private static final String FIRST_URL_PARAM_APPEND_CHARACTER = "?";
    private static final String OTHER_URL_PARAM_APPEND_CHARACTER = "&";
    private static final String EQUAL_URL_PARAM_ASSIGN_CHARACTER = "=";
    private static final String UTF_8_ENCODING = "UTF-8";
    private static final String JSON_START_CHARACTER = "{";
    private static final String JSON_END_CHARACTER = "}";
    private static final String JSON_ARRAY_START_CHARACTER = "[";
    private static final String JSON_ARRAY_END_CHARACTER = "]";
    private static final String JSON_KEY_VALUE_SEPARATOR_CHARACTER = ":";
    private static final String JSON_FIELD_SEPARATOR_CHARACTER = ",";
    private static final String QUOTATION_CHARACTER = "\"";
    private static final String NEW_LINE_CHARACTER = "\n";

    @NonNull
    public static URL urlFromString(String urlStr) throws MalformedURLException, URISyntaxException {
        URL url;
        URL auxUrl = new URL(urlStr);
        URI uri = new URI(auxUrl.getProtocol(), auxUrl.getUserInfo(), auxUrl.getHost(), auxUrl.getPort(), auxUrl.getPath(), auxUrl.getQuery(), auxUrl.getRef());
        url = uri.toURL();
        return url;
    }

    public static String getUrlDataString(HashMap<String, Object> params) throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first) {
                first = false;
                result.append(FIRST_URL_PARAM_APPEND_CHARACTER);
            } else {
                result.append(OTHER_URL_PARAM_APPEND_CHARACTER);
            }

            result.append(URLEncoder.encode(entry.getKey(), UTF_8_ENCODING));
            result.append(EQUAL_URL_PARAM_ASSIGN_CHARACTER);

            if (entry.getValue().getClass().equals(String.class)) {
                result.append(URLEncoder.encode((String) entry.getValue(), UTF_8_ENCODING));
            } else {
                result.append(entry.getValue());
            }
        }

        return result.toString();
    }

    public static String getBodyDataString(HashMap<String, Object> params) throws UnsupportedEncodingException {
        if (params == null) {
            return "";
        }
        StringBuilder result = new StringBuilder();
        result.append(JSON_START_CHARACTER);
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append(JSON_FIELD_SEPARATOR_CHARACTER + NEW_LINE_CHARACTER);

            result.append(QUOTATION_CHARACTER);
            result.append(entry.getKey());
            result.append(QUOTATION_CHARACTER);
            result.append(JSON_KEY_VALUE_SEPARATOR_CHARACTER);
            if (entry.getValue() instanceof HashMap) {
                // Si se trata de un Hashmap dentro de otro
                result.append(getBodyDataString((HashMap<String, Object>) entry.getValue()));
            } else if (entry.getValue() instanceof Object[]) {
                result.append(getBodyDataStringFromArray((Object[]) entry.getValue()));
            } else {
                result.append(QUOTATION_CHARACTER);
                result.append(entry.getValue());
                result.append(QUOTATION_CHARACTER);
            }
        }
        result.append(JSON_END_CHARACTER);
        return result.toString();
    }

    public static String getBodyDataStringFromArray(Object[] array) {
        if (array == null) {
            return "";
        }

        StringBuilder result = new StringBuilder();
        result.append(JSON_ARRAY_START_CHARACTER);
        boolean first = true;
        for (Object objectInArray : array) {
            if (first)
                first = false;
            else
                result.append(JSON_FIELD_SEPARATOR_CHARACTER + NEW_LINE_CHARACTER);
            result.append(QUOTATION_CHARACTER);
            result.append(objectInArray.toString());
            result.append(QUOTATION_CHARACTER);
        }

        result.append(JSON_ARRAY_END_CHARACTER);
        return result.toString();
    }

    public static void parseObjects(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        if (response.equals("")) {
            onDataParsedCallbackWeakReference.get().onDataObjectParsed(responseCode, null);
            return;
        }
        Object json;
        try {
            json = new JSONTokener(response).nextValue();
            if (json instanceof JSONObject) {

                Constructor<?> ctor = modelClass.getConstructor(JSONObject.class);
                Object object = ctor.newInstance((JSONObject) json);

                if (onDataParsedCallbackWeakReference != null && onDataParsedCallbackWeakReference.get() != null) {
                    onDataParsedCallbackWeakReference.get().onDataObjectParsed(responseCode, object);
                }
            } else if (json instanceof JSONArray) {
                ArrayList<Object> objects = new ArrayList<>();
                JSONArray objectsArray = new JSONArray(response);
                for (int i = 0; i < objectsArray.length(); i++) {
                    JSONObject jsonObject = objectsArray.getJSONObject(i);
                    Constructor<?> constructor = modelClass.getConstructor(JSONObject.class);
                    Object object = constructor.newInstance(jsonObject);
                    objects.add(object);
                }
                onDataParsedCallbackWeakReference.get().onDataArrayParsed(responseCode, objects);
            }
        } catch (JSONException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            onDataParsedCallbackWeakReference.get().onDataArrayParsed(responseCode, null);
        }
    }
}

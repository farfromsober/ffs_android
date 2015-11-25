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

import javax.net.ssl.HttpsURLConnection;

public class NetworkUtils {

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
            if (first){
                first = false;
                result.append("?");
            }
            else{
                result.append("&");
            }

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");

            if (entry.getValue().getClass().equals(String.class)){
                result.append(URLEncoder.encode((String)entry.getValue(), "UTF-8"));
            } else{
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
        result.append("{");
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append(",\n");

            result.append("\"");
            result.append(entry.getKey());
            result.append("\"");
            result.append(":");
            result.append("\"");
            result.append(entry.getValue());
            result.append("\"");
        }
        result.append("}");
        return result.toString();
    }
    public static void parseObjects(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            Object json;
            try {
                json = new JSONTokener(response).nextValue();
                if (json instanceof JSONObject) {

                    Constructor<?> ctor = modelClass.getConstructor(JSONObject.class);
                    Object object = ctor.newInstance((JSONObject) json);

                    if (onDataParsedCallbackWeakReference != null && onDataParsedCallbackWeakReference.get() != null) {
                        onDataParsedCallbackWeakReference.get().onDataParsed(object);
                    }
                }
                else if (json instanceof JSONArray) {
                    ArrayList<Object> objects = new ArrayList<>();
                    JSONArray objectsArray = new JSONArray(response);
                    for (int i = 0; i < objectsArray.length(); i++) {
                        JSONObject jsonObject = objectsArray.getJSONObject(i);
                        Constructor<?> constructor = modelClass.getConstructor(JSONObject.class);
                        Object object = constructor.newInstance(jsonObject);
                        objects.add(object);
                    }
                    onDataParsedCallbackWeakReference.get().onDataParsed(objects);
                }
            } catch (JSONException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
                onDataParsedCallbackWeakReference.get().onDataParsed(null);
            }
        } else {
            onDataParsedCallbackWeakReference.get().onDataParsed(null);
        }
    }
}

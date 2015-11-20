package com.farfromsober.network.interfaces;

import android.support.annotation.NonNull;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

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
}

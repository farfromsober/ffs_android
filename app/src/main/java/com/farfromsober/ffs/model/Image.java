package com.farfromsober.ffs.model;

import org.json.JSONObject;

public class Image {

    private static final String URL_KEY = "url";

    private String mUrl;

    public Image(String url) {
        mUrl = url;
    }

    public Image(JSONObject json) {
        mUrl = json.optString(URL_KEY);
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public String toString() {
        return "Image{" +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}

package com.farfromsober.ffs.model;

import org.json.JSONObject;

public class Image {

    private static final String ID_KEY = "_id";
    private static final String AZURE_ID_KEY = "azure_id";
    private static final String URL_KEY = "url";

    private String mId;
    private String mAzureId;
    private String mUrl;

    public Image(String id, String azureId, String url) {
        mId = id;
        mAzureId = azureId;
        mUrl = url;
    }

    public Image(JSONObject json) {
        mId = json.optString(ID_KEY);
        mAzureId = json.optString(AZURE_ID_KEY);
        mUrl = json.optString(URL_KEY);
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAzureId() {
        return mAzureId;
    }

    public void setAzureId(String azureId) {
        mAzureId = azureId;
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
                "mId='" + mId + '\'' +
                ", mAzureId='" + mAzureId + '\'' +
                ", mUrl='" + mUrl + '\'' +
                '}';
    }
}

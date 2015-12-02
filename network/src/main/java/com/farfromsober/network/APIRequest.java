package com.farfromsober.network;

import java.util.HashMap;

public class APIRequest {

    private String mUrlString;
    private APIAsyncTask.ApiRequestType mApiRequestType;
    private HashMap<String, Object> mHeaders;
    private HashMap<String, Object> mUrlDataParams;
    private HashMap<String, Object> mBodyDataParams;
    private int mReadTimeout;
    private int mConnectTimeout;

    public APIRequest(String urlString, APIAsyncTask.ApiRequestType apiRequestType,
                      HashMap<String, Object> headers, HashMap<String, Object> urlDataParams,
                      HashMap<String, Object> bodyDataParams, int readTimeout, int connectTimeout) {
        mUrlString = urlString;
        mApiRequestType = apiRequestType;
        mHeaders = headers;
        mUrlDataParams = urlDataParams;
        mBodyDataParams = bodyDataParams;
        mReadTimeout = readTimeout;
        mConnectTimeout = connectTimeout;
    }

    public String getUrlString() {
        return mUrlString;
    }

    public void setUrlString(String urlString) {
        mUrlString = urlString;
    }

    public APIAsyncTask.ApiRequestType getApiRequestType() {
        return mApiRequestType;
    }

    public void setApiRequestType(APIAsyncTask.ApiRequestType apiRequestType) {
        mApiRequestType = apiRequestType;
    }

    public HashMap<String, Object> getHeaders() {
        return mHeaders;
    }

    public void setHeaders(HashMap<String, Object> headers) {
        mHeaders = headers;
    }

    public HashMap<String, Object> getUrlDataParams() {
        return mUrlDataParams;
    }

    public void setUrlDataParams(HashMap<String, Object> urlDataParams) {
        mUrlDataParams = urlDataParams;
    }

    public HashMap<String, Object> getBodyDataParams() {
        return mBodyDataParams;
    }

    public void setBodyDataParams(HashMap<String, Object> bodyDataParams) {
        mBodyDataParams = bodyDataParams;
    }

    public int getReadTimeout() {
        return mReadTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        mReadTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return mConnectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        mConnectTimeout = connectTimeout;
    }
}

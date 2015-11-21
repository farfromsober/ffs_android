package com.farfromsober.network;

import android.os.AsyncTask;

import com.farfromsober.network.interfaces.OnDataParsedCallback;
import com.farfromsober.network.interfaces.OnResponseReceivedCallback;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class APIAsyncTask extends AsyncTask<String, Integer, HashMap<String, Object>> {

    private static final int NO_RESPONSE_CODE = 0;
    private static final String RESPONSE_CODE_KEY = "APIAsyncTask.RESPONSE_CODE_KEY";
    private static final String RESPONSE_KEY = "APIAsyncTask.RESPONSE_KEY";

    private String mUrlString;
    private boolean mIsPostRequest;
    private HashMap<String, String> mHeaders;
    private HashMap<String, Object> mPostDataParams;
    private WeakReference<OnResponseReceivedCallback> mOnResponseReceivedCallbackWeakReference;
    private WeakReference<OnDataParsedCallback> mOnDataParsedCallbackWeakReference;
    private Class mModelClass;

    public APIAsyncTask(String urlString, boolean isPostRequest, HashMap<String, String> headers, HashMap<String, Object> postDataParams,
                        OnResponseReceivedCallback onResponseReceivedCallback, OnDataParsedCallback onDataParsedCallback, Class<?> modelClass) {
        mUrlString = urlString;
        mIsPostRequest = isPostRequest;
        mHeaders = headers;
        mPostDataParams = postDataParams;
        mOnResponseReceivedCallbackWeakReference = new WeakReference<>(onResponseReceivedCallback);
        mOnDataParsedCallbackWeakReference = new WeakReference<>(onDataParsedCallback);
        mModelClass = modelClass;
    }

    @Override
    protected HashMap<String, Object> doInBackground(String... args) {

        int responseCode = NO_RESPONSE_CODE;
        String response = "";

        try {
            URL url = NetworkUtils.urlFromString(mUrlString);

            HttpURLConnection conn = (HttpURLConnection) (url.openConnection());
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(10000);

            //add request header
            if (mHeaders != null) {
                Iterator it = mHeaders.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry)it.next();
                    conn.setRequestProperty(pair.getKey().toString(), pair.getValue().toString());
                }
            }

            if (mIsPostRequest) {
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(NetworkUtils.getBodyDataString(mPostDataParams));

                writer.flush();
                writer.close();
                os.close();
            } else {
                conn.setRequestMethod("GET");
            }

            responseCode = conn.getResponseCode();

            String line;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            while ((line = br.readLine()) != null) {
                response += line;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        HashMap<String, Object> values = new HashMap<>();
        values.put(RESPONSE_CODE_KEY, responseCode);
        values.put(RESPONSE_KEY, response);
        return values;
    }

    @Override
    protected void onPostExecute(HashMap<String, Object> values) {

        int responseCode = (int)values.get(RESPONSE_CODE_KEY);
        String response = (String) values.get(RESPONSE_KEY);

        if (mOnResponseReceivedCallbackWeakReference != null && mOnResponseReceivedCallbackWeakReference.get() != null) {
            mOnResponseReceivedCallbackWeakReference.get().onResponseReceived(responseCode, response, mModelClass, mOnDataParsedCallbackWeakReference);
        }
        super.onPostExecute(values);
    }


}

package com.farfromsober.ffs.network;

import android.content.Context;

import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.network.APIAsyncTask;
import com.farfromsober.network.APIRequest;
import com.farfromsober.network.NetworkUtils;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.network.callbacks.OnResponseReceivedCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;

import static com.farfromsober.network.APIAsyncTask.ApiRequestType;

public class APIManager implements OnResponseReceivedCallback{

    private final Context mContext;

    private static final String ALL_PRODUCTS_URL = "http://forsale.cloudapp.net/api/1.0/products";
    private static final String ALL_USERS_URL = "http://beta.json-generator.com/api/json/get/NJsNmZgQe";
    private static final String LOGIN_URL = "http://forsale.cloudapp.net/api/1.0/login/";

    public APIManager(Context context) {
        mContext = context;
    }

    public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(), null, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void allUsers(OnDataParsedCallback<User> onDataParsedCallback){
        APIRequest apiRequest = new APIRequest(ALL_USERS_URL, ApiRequestType.GET, null, null, null, 10000, 10000);
        APIAsyncTask allUsersAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, User.class);
        allUsersAsyncTask.execute();
    }

    public void login(OnDataParsedCallback<User> onDataParsedCallback) {
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        APIRequest apiRequest = new APIRequest(LOGIN_URL, ApiRequestType.POST, loginData.getHeaders(), null, loginData.toHashMap(), 10000, 10000);
        APIAsyncTask loginAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, User.class);
        loginAsyncTask.execute();
    }

    public void login(String login, String password, OnDataParsedCallback<User> onDataParsedCallback) {
        LoginData loginData = new LoginData(login, password);
        APIRequest apiRequest = new APIRequest(LOGIN_URL, ApiRequestType.POST, loginData.getHeaders(), null, loginData.toHashMap(), 10000, 10000);
        APIAsyncTask loginAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, User.class);
        loginAsyncTask.execute();
    }

    public void allProductsFilterByCategories(ArrayList<String> categoriesIds,OnDataParsedCallback<Product> onDataParsedCallback){
        HashMap<String,Object> getParameters = null;
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        // Crear hashmap con parametros del get
        if (categoriesIds.size()>=1) {
            getParameters = new HashMap<String, Object>();
            getParameters.put("category", categoriesIds.get(0));
        }
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(),getParameters, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }



    @Override
    public void onResponseReceived(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        NetworkUtils.parseObjects(responseCode, response, modelClass, onDataParsedCallbackWeakReference);
    }

    @Override
    public void onExceptionReceived(Exception e, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        if (onDataParsedCallbackWeakReference != null && onDataParsedCallbackWeakReference.get() != null) {
            onDataParsedCallbackWeakReference.get().onExceptionReceived(e);
        }
    }
}

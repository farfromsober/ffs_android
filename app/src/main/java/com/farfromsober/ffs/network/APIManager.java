package com.farfromsober.ffs.network;

import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.network.APIAsyncTask;
import com.farfromsober.network.APIRequest;
import com.farfromsober.network.NetworkUtils;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.network.callbacks.OnResponseReceivedCallback;

import java.lang.ref.WeakReference;

import static com.farfromsober.network.APIAsyncTask.ApiRequestType;

public class APIManager implements OnResponseReceivedCallback{
    private static final String ALL_PRODUCTS_URL = "http://beta.json-generator.com/api/json/get/NyJpZWxQl";
    private static final String ALL_USERS_URL = "http://beta.json-generator.com/api/json/get/NJsNmZgQe";
    private static final String LOGIN_URL = "http://www.json-generator.com/api/json/get/cqiwTpuSqa";

    public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, null, null, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void allUsers(OnDataParsedCallback<User> onDataParsedCallback){
        APIRequest apiRequest = new APIRequest(ALL_USERS_URL, ApiRequestType.GET, null, null, null, 10000, 10000);
        APIAsyncTask allUsersAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, User.class);
        allUsersAsyncTask.execute();
    }

    public void login(LoginData loginData, OnDataParsedCallback<User> onDataParsedCallback) {
        APIAsyncTask loginAsyncTask = new APIAsyncTask(LOGIN_URL, ApiRequestType.GET, null, loginData.toHashMap(), null, this, onDataParsedCallback, User.class);
        loginAsyncTask.execute();
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

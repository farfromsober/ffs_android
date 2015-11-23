package com.farfromsober.ffs.network;

import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.User;
import com.farfromsober.network.APIAsyncTask;
import com.farfromsober.network.NetworkUtils;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.network.callbacks.OnResponseReceivedCallback;

import java.lang.ref.WeakReference;

public class APIManager implements OnResponseReceivedCallback{
    private static final String ALL_PRODUCTS_URL = "http://beta.json-generator.com/api/json/get/NyJpZWxQl";
    private static final String ALL_USERS_URL = "http://beta.json-generator.com/api/json/get/NJsNmZgQe";

    public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(ALL_PRODUCTS_URL, false, null, null, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void allUsers(OnDataParsedCallback<User> onDataParsedCallback){
        APIAsyncTask allUsersAsyncTask = new APIAsyncTask(ALL_USERS_URL, false, null, null, this, onDataParsedCallback, User.class);
        allUsersAsyncTask.execute();
    }

    @Override
    public void onResponseReceived(int responseCode, String response, Class<?> modelClass, WeakReference<OnDataParsedCallback> onDataParsedCallbackWeakReference) {
        NetworkUtils.parseObjects(responseCode, response, modelClass, onDataParsedCallbackWeakReference);
    }
}

package com.farfromsober.ffs.network;

import android.content.Context;
import android.location.Location;

import com.farfromsober.ffs.model.LoginData;
import com.farfromsober.ffs.model.Product;
import com.farfromsober.ffs.model.Transaction;
import com.farfromsober.ffs.model.User;
import com.farfromsober.ffs.utils.SharedPreferencesManager;
import com.farfromsober.network.APIAsyncTask;
import com.farfromsober.network.APIRequest;
import com.farfromsober.network.NetworkUtils;
import com.farfromsober.network.callbacks.OnDataParsedCallback;
import com.farfromsober.network.callbacks.OnResponseReceivedCallback;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import static com.farfromsober.network.APIAsyncTask.ApiRequestType;

import static com.farfromsober.ffs.fragments.CategoryFilterFragment.*;

public class APIManager implements OnResponseReceivedCallback {

    private final Context mContext;

    private static final String ALL_PRODUCTS_URL = "http://forsale.cloudapp.net/api/1.0/products";
    private static final String NEW_PRODUCT_URL = "http://forsale.cloudapp.net/api/1.0/products/";
    private static final String IMAGES_URL = "http://forsale.cloudapp.net/api/1.0/images/";
    private static final String ALL_USERS_URL = "http://beta.json-generator.com/api/json/get/NJsNmZgQe";
    private static final String LOGIN_URL = "http://forsale.cloudapp.net/api/1.0/login/";
    private static final String TRANSACTIONS_URL = "http://forsale.cloudapp.net/api/1.0/transactions/";


    public APIManager(Context context) {
        mContext = context;
    }

    public void allProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(), null, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void mySellingProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        HashMap<String, Object> urlParams = new HashMap<>();
        urlParams.put("seller",SharedPreferencesManager.getPrefUserData(mContext).getUsername());
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(), urlParams, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void mySoldProducts(OnDataParsedCallback<Product> onDataParsedCallback){
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        HashMap<String, Object> urlParams = new HashMap<>();
        urlParams.put("seller",SharedPreferencesManager.getPrefUserData(mContext).getUsername());
        urlParams.put("selling", "3");
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(), urlParams, null, 10000, 10000);
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

    public void allProductsFilterByCategoriesAndDistance(HashMap<String,Integer> categoriesIds,OnDataParsedCallback<Product> onDataParsedCallback, Location location){
        HashMap<String,Object> getParameters = null;
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        // Crear hashmap con parametros del get
        if (categoriesIds.size()>=1) {
            getParameters = new HashMap<String, Object>();
            if(categoriesIds.containsKey(CATEGORY_KEY)) {
                getParameters.put(CATEGORY_KEY, categoriesIds.get(CATEGORY_KEY));
            }
            if(categoriesIds.containsKey(DISTANCE_KEY)) {
                getParameters.put(DISTANCE_KEY, categoriesIds.get(DISTANCE_KEY));
                if (location != null) {
                    getParameters.put("latitude", String.valueOf(location.getLatitude()).replace(",","."));
                    getParameters.put("longitude", String.valueOf(location.getLongitude()).replace(",","."));
                }
            }
        }
        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(),getParameters, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void createTransaction(String productId, String buyerId, OnDataParsedCallback<Transaction> onDataParsedCallback){
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        // Crear hashmap con parametros del post
        HashMap<String,Object> postParameters = null;
        if (productId.length()>=1 && buyerId.length()>=1) {
            postParameters = new HashMap<String, Object>();
            postParameters.put("productId", productId);
            postParameters.put("buyerId", buyerId);
        }
        APIRequest apiRequest = new APIRequest(TRANSACTIONS_URL, ApiRequestType.POST, loginData.getHeaders(),null, postParameters, 10000, 10000);
        APIAsyncTask transactionAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Transaction.class);
        transactionAsyncTask.execute();
    }

    public void allProductsFilterByWord(String word,OnDataParsedCallback<Product> onDataParsedCallback){
        HashMap<String,Object> getParameters = null;
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        // Crear hashmap con parametros del get
        if (word != null) {
            getParameters = new HashMap<String, Object>();
            getParameters.put("name", word);
        }

        APIRequest apiRequest = new APIRequest(ALL_PRODUCTS_URL, ApiRequestType.GET, loginData.getHeaders(),getParameters, null, 10000, 10000);
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        allProductsAsyncTask.execute();
    }

    public void createProduct(Product newProduct, OnDataParsedCallback<Product> onDataParsedCallback) {
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        APIRequest apiRequest = new APIRequest(NEW_PRODUCT_URL, ApiRequestType.POST, loginData.getHeaders(),null, newProduct.toNewProductHashMap(), 10000, 10000);
        APIAsyncTask createProductAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, Product.class);
        createProductAsyncTask.execute();
    }

    public void uploadNewProductImages(Product newProduct, OnDataParsedCallback<Product> onDataParsedCallback) {
        LoginData loginData = SharedPreferencesManager.getPrefLoginUser(mContext);
        APIRequest apiRequest = new APIRequest(IMAGES_URL, ApiRequestType.POST, loginData.getHeaders(),null, newProduct.toImagesHashMap(), 10000, 10000);
        APIAsyncTask uploadNewProductImagesAsyncTask = new APIAsyncTask(apiRequest, this, onDataParsedCallback, null);
        uploadNewProductImagesAsyncTask.execute();
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

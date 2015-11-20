package com.farfromsober.ffs.network;

import android.content.Context;
import android.util.Log;

import com.farfromsober.ffs.model.Product;
import com.farfromsober.network.APIAsyncTask;
import com.farfromsober.network.interfaces.OnResponseReceivedCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class APIManager implements OnResponseReceivedCallback{
    private static final String ALL_PRODUCTS_URL = "http://beta.json-generator.com/api/json/get/NyJpZWxQl";

    public void allProducts(Context context){
        APIAsyncTask allProductsAsyncTask = new APIAsyncTask(ALL_PRODUCTS_URL, false, null, null, this);
        allProductsAsyncTask.execute();
    }

    @Override
    public void onResponseReceived(int responseCode, String response) {
        Log.i("ffs", responseCode + ": " + response);
        ArrayList<Product> products = new ArrayList<>();
        if (responseCode == HttpsURLConnection.HTTP_OK) {
            try {
                JSONArray objectsArray = new JSONArray(response);
                for (int i = 0; i < objectsArray.length(); i++) {
                    JSONObject object = objectsArray.getJSONObject(i);
                    Product product = new Product(object);
                    products.add(product);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }
}

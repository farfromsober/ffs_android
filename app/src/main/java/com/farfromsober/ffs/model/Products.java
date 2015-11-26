package com.farfromsober.ffs.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by joanbiscarri on 27/11/15.
 */
public class Products {

    public static final String PREF_CITIES = "com.farfromsober.ffs.model.Products.";
    private static Products ourInstance;

    private List<Product> mProducts;
    private WeakReference<Context> mContext;

    public static Products getInstance(Context context) {
        if (ourInstance == null || ourInstance.mContext.get() == null) {
            if (ourInstance == null) {
                ourInstance = new Products(context);
            } else if (ourInstance.mContext.get() == null) {
                ourInstance.mContext = new WeakReference<Context>(context);
            }
        }
        return ourInstance;
    }

    private Products(Context context) {
        mProducts = new LinkedList<>();
        mContext = new WeakReference<Context>(context);
    }

    public void addProduct(Product product) {
        mProducts.add(product);
    }

    public void setProducts(List<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public List<Product> getProducts() {
        return mProducts;
    }
}
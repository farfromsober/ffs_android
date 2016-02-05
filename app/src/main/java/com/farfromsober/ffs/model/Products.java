package com.farfromsober.ffs.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by joanbiscarri on 27/11/15.
 */
public class Products {

    public static final String PREF_CITIES = "com.farfromsober.ffs.model.Products.";
    //private static Products ourInstance;

    private ArrayList<Product> mProducts;
    //private WeakReference<Context> mContext;

    /*public static Products getInstance(Context context) {
        if (ourInstance == null || ourInstance.mContext.get() == null) {
            if (ourInstance == null) {
                ourInstance = new Products(context);
                //ourInstance.fillDummyData();
            } else if (ourInstance.mContext.get() == null) {
                ourInstance.mContext = new WeakReference<Context>(context);
            }
        }
        return new Products(context);
    }*/

    //private Products(Context context) {
    public Products() {
        mProducts = new ArrayList<>();
        //mContext = new WeakReference<Context>(context);
    }

    public void addProduct(Product product) {
        mProducts.add(product);
    }

    public void setProducts(ArrayList<Product> mProducts) {
        this.mProducts = mProducts;
    }

    public ArrayList<Product> getProducts() {
        return mProducts;
    }

    public void deleteProducts() {
        mProducts = new ArrayList<>();
    }

    public void fillDummyData() {
        User user = new User("userId","Name", "LastName", "email@email.com", "username", "1.0000", "1.0000", "avatar", "city", "Active", 1);
        Category category = new Category("category", 1);
        Product p1 = new Product("id", "name1", "detail1", new Date(), true, "20.00", user, category, new ArrayList<String>());
        this.addProduct(p1);
        Product p2 = new Product("id", "name2", "detail2", new Date(), true, "13.50", user, category, new ArrayList<String>());
        this.addProduct(p2);
        Product p3 = new Product("id", "name3", "detail3", new Date(), true, "40.00", user, category, new ArrayList<String>());
        this.addProduct(p3);
        Product p4 = new Product("id", "name4", "detail4", new Date(), true, "18.35", user, category, new ArrayList<String>());
        this.addProduct(p4);
        Product p5 = new Product("id", "name5", "detail5", new Date(), true, "11.50", user, category, new ArrayList<String>());
        this.addProduct(p5);
    }

}
package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;

import static com.farfromsober.ffs.model.Product.DATE_FORMAT;

public class Transaction {

    private static final String PRODUCT_KEY = "product";
    private static final String BUYER_KEY = "buyer";
    private static final String DATE_KEY = "date";

    private Product mProduct;
    private User mBuyer;
    private Date mDate;

    public Transaction(Product product, User buyer, Date date) {
        mProduct = product;
        mBuyer = buyer;
        mDate = date;
    }

    public Transaction(JSONObject json) throws JSONException, ParseException {
        Product product = new Product((JSONObject) json.opt(PRODUCT_KEY));
        mProduct = product;
        mBuyer = new User((JSONObject) json.opt(BUYER_KEY));
        mDate = DateManager.dateFromString(json.optString(DATE_KEY), DATE_FORMAT);
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public User getBuyer() {
        return mBuyer;
    }

    public void setBuyer(User buyer) {
        mBuyer = buyer;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mProduct=" + mProduct +
                ", mBuyer=" + mBuyer +
                ", mDate=" + mDate +
                '}';
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(PRODUCT_KEY, getProduct().toHashMap());
        hashMap.put(BUYER_KEY, getBuyer().toHashMap());
        hashMap.put(DATE_KEY, DateManager.stringFromDate(getDate(), DATE_FORMAT));

        return hashMap;
    }
}

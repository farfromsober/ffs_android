package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.Date;

import static com.farfromsober.ffs.model.Product.DATE_FORMAT;

public class Transaction {

    private static final String PRODUCT_KEY = "product";
    private static final String BUYER_KEY = "buyer";
    private static final String DATE_KEY = "date";
    private static final String IMAGES_KEY = "images";

    private Product mProduct;
    private User mSeller;
    private User mBuyer;
    private Date mDate;

    public Transaction(Product product, User buyer, Date date) {
        mProduct = product;
        mSeller = product.getSeller();
        mBuyer = buyer;
        mDate = date;
    }

    public Transaction(JSONObject json) throws JSONException, ParseException {
        Product product = new Product((JSONObject) json.opt(PRODUCT_KEY));
        mProduct = product;
        mSeller = product.getSeller();
        mBuyer = new User((JSONObject) json.opt(BUYER_KEY));
        mDate = DateManager.dateFromString(json.optString(DATE_KEY), DATE_FORMAT);
    }

    public Product getProduct() {
        return mProduct;
    }

    public void setProduct(Product product) {
        mProduct = product;
    }

    public User getSeller() {
        return mSeller;
    }

    public void setSeller(User seller) {
        mSeller = seller;
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
                ", mSeller=" + mSeller +
                ", mBuyer=" + mBuyer +
                ", mDate=" + mDate +
                '}';
    }
}

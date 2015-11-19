package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import static com.farfromsober.ffs.model.Product.*;

public class Transaction {

    private static final String ID_KEY = "_id";
    private static final String PRODUCT_KEY = "product";
    private static final String SELLER_KEY = "seller";
    private static final String BUYER_KEY = "buyer";
    private static final String DATE_KEY = "date";
    private static final String IMAGES_KEY = "images";

    private String mId;
    private Product mProduct;
    private User mSeller;
    private User mBuyer;
    private Date mDate;
    private ArrayList<Image> mImages;

    public Transaction(String id, Product product, User seller, User buyer, Date date, ArrayList<Image> images) {
        mId = id;
        mProduct = product;
        mSeller = seller;
        mBuyer = buyer;
        mDate = date;
        mImages = images;
    }

    public Transaction(JSONObject json) throws JSONException, ParseException {
        mId = json.optString(ID_KEY);
        mProduct = new Product((JSONObject) json.opt(PRODUCT_KEY));
        mSeller = new User((JSONObject) json.opt(SELLER_KEY));
        mBuyer = new User((JSONObject) json.opt(BUYER_KEY));
        mDate = DateManager.dateFromString(json.optString(DATE_KEY), DATE_FORMAT);

        mImages = new ArrayList<>();
        JSONArray objectsArray = json.optJSONArray(IMAGES_KEY);
        for (int i = 0; i < objectsArray.length(); i++) {
            JSONObject object = objectsArray.getJSONObject(i);
            mImages.add(new Image(object));
        }
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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

    public ArrayList<Image> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<Image> images) {
        mImages = images;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "mId='" + mId + '\'' +
                ", mProduct=" + mProduct +
                ", mSeller=" + mSeller +
                ", mBuyer=" + mBuyer +
                ", mDate=" + mDate +
                ", mImages=" + mImages +
                '}';
    }
}

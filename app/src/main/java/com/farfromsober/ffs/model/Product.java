package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Product {

    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PUBLISH_DATE_KEY = "publish_date";
    public static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ssZ"; // "2015-11-02T14:16:29+00:00";
    private static final String SELLING_KEY = "selling";
    private static final String PRICE_KEY = "price";
    private static final String SELLER_KEY = "seller";
    private static final String CATEGORY_KEY = "category";
    private static final String IMAGES_KEY = "images";

    private String mName;
    private String mDetail;
    private Date mPublished;
    private boolean mIsSelling;
    private String mPrice;
    private User mSeller;
    private Category mCategory;
    private ArrayList<Image> mImages;

    public Product(String name, String detail, Date published, boolean isSelling,
                   String price, User seller, Category category, ArrayList<Image> images) {
        mName = name;
        mDetail = detail;
        mPublished = published;
        mIsSelling = isSelling;
        mPrice = price;
        mSeller = seller;
        mCategory = category;
        mImages = images;
    }

    public Product(JSONObject json) throws JSONException, ParseException {
        mName = json.optString(NAME_KEY);
        mDetail = json.optString(DESCRIPTION_KEY);
        mPublished = DateManager.dateFromString(json.optString(PUBLISH_DATE_KEY), DATE_FORMAT);
        mIsSelling = json.optBoolean(SELLING_KEY);
        mPrice = json.optString(PRICE_KEY);
        mSeller = new User((JSONObject) json.opt(SELLER_KEY));
        mCategory = new Category((JSONObject) json.opt(CATEGORY_KEY));

        mImages = new ArrayList<>();
        JSONArray objectsArray = json.optJSONArray(IMAGES_KEY);
        for (int i = 0; i < objectsArray.length(); i++) {
            JSONObject object = objectsArray.getJSONObject(i);
            mImages.add(new Image(object));
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public Date getPublished() {
        return mPublished;
    }

    public void setPublished(Date published) {
        mPublished = published;
    }

    public boolean isSelling() {
        return mIsSelling;
    }

    public void setSelling(boolean selling) {
        mIsSelling = selling;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public User getSeller() {
        return mSeller;
    }

    public void setSeller(User seller) {
        mSeller = seller;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    public ArrayList<Image> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<Image> images) {
        mImages = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "mName='" + mName + '\'' +
                ", mDetail='" + mDetail + '\'' +
                ", mPublished=" + mPublished +
                ", mIsSelling=" + mIsSelling +
                ", mPrice='" + mPrice + '\'' +
                ", mSeller=" + mSeller +
                ", mCategory=" + mCategory +
                ", mImages=" + mImages +
                '}';
    }
}

package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

public class Product {

    private static final String ID_KEY = "_id";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PUBLISH_DATE_KEY = "publish_date";
    public static final String DATE_FORMAT = "yyyy'-'MM'-'dd'T'HH':'mm':'ssZ"; // "2015-11-02T14:16:29+00:00";
    private static final String SELLING_KEY = "selling";
    private static final String PRICE_KEY = "price";
    private static final String SELLER_KEY = "seller";
    private static final String CATEGORY_KEY = "category";
    private static final String IMAGES_KEY = "images";

    private String mId;
    private String mName;
    private String mDescription;
    private Date mPublishedDate;
    private boolean mSelling;
    private String mPrice;
    private User mSeller;
    private Category mCategory;
    private ArrayList<Image> mImages;

    public Product(String id, String name, String description, Date publishedDate, boolean selling,
                   String price, User seller, Category category, ArrayList<Image> images) {
        mId = id;
        mName = name;
        mDescription = description;
        mPublishedDate = publishedDate;
        mSelling = selling;
        mPrice = price;
        mSeller = seller;
        mCategory = category;
        mImages = images;
    }

    public Product(JSONObject json) throws JSONException, ParseException {
        mId = json.optString(ID_KEY);
        mName = json.optString(NAME_KEY);
        mDescription = json.optString(DESCRIPTION_KEY);
        mPublishedDate = DateManager.dateFromString(json.optString(PUBLISH_DATE_KEY), DATE_FORMAT);
        mSelling = json.optBoolean(SELLING_KEY);
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

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Date getPublishedDate() {
        return mPublishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        mPublishedDate = publishedDate;
    }

    public boolean isSelling() {
        return mSelling;
    }

    public void setSelling(boolean selling) {
        mSelling = selling;
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
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mPublishedDate=" + mPublishedDate +
                ", mSelling=" + mSelling +
                ", mPrice='" + mPrice + '\'' +
                ", mSeller=" + mSeller +
                ", mCategory=" + mCategory +
                ", mImages=" + mImages +
                '}';
    }
}

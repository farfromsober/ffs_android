package com.farfromsober.ffs.model;

import com.farfromsober.generalutils.DateManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

public class Product implements Serializable {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String PUBLISHED_DATE_KEY = "published_date";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"; // "2015-12-04T14:31:35.074948Z";
    private static final String SELLING_KEY = "selling";
    private static final String PRICE_KEY = "price";
    private static final String SELLER_KEY = "seller";
    private static final String CATEGORY_KEY = "category";
    private static final String IMAGES_KEY = "images";

    private static final String IMAGE_UPLOAD_ID_KEY = "productId";
    private static final String IMAGE_UPLOAD_IMAGES_URLS = "urls";

    private String mId;
    private String mName;
    private String mDetail;
    private Date mPublished;
    private boolean mIsSelling;
    private String mPrice;
    private User mSeller;
    private Category mCategory;
    private ArrayList<String> mImages;

    public Product() { }

    public Product(String id, String name, String detail, Date published, boolean isSelling,
                   String price, User seller, Category category, ArrayList<String> images) {
        mId = id;
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
        mId = json.optString(ID_KEY);
        mName = json.optString(NAME_KEY);
        mDetail = json.optString(DESCRIPTION_KEY);
        mPublished = DateManager.dateFromString(json.optString(PUBLISHED_DATE_KEY), DATE_FORMAT);
        mIsSelling = json.optBoolean(SELLING_KEY);
        mPrice = json.optString(PRICE_KEY);
        mSeller = new User((JSONObject) json.opt(SELLER_KEY));
        mCategory = new Category((JSONObject) json.opt(CATEGORY_KEY));

        mImages = new ArrayList<>();
        JSONArray objectsArray = json.optJSONArray(IMAGES_KEY);
        for (int i = 0; i < objectsArray.length(); i++) {
            mImages.add(objectsArray.getString(i));
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

    public boolean getIsSelling() {
        return mIsSelling;
    }

    public void setIsSelling(boolean isSelling) {
        mIsSelling = isSelling;
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

    public ArrayList<String> getImages() {
        return mImages;
    }

    public void setImages(ArrayList<String> images) {
        mImages = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "miD='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mDetail='" + mDetail + '\'' +
                ", mPublished=" + mPublished +
                ", mIsSelling=" + mIsSelling +
                ", mPrice='" + mPrice + '\'' +
                ", mSeller=" + mSeller +
                ", mCategory=" + mCategory +
                ", mImages=" + mImages +
                '}';
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(ID_KEY, getId());
        hashMap.put(NAME_KEY, getName());
        hashMap.put(DESCRIPTION_KEY, getDetail());
        hashMap.put(PUBLISHED_DATE_KEY, DateManager.stringFromDate(getPublished(), DATE_FORMAT));
        hashMap.put(SELLING_KEY, getIsSelling());
        hashMap.put(PRICE_KEY, getPrice());
        hashMap.put(SELLER_KEY, getSeller().toHashMap());
        hashMap.put(CATEGORY_KEY, getCategory().toHashMap());

        return hashMap;
    }

    public HashMap<String, Object> toNewProductHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(NAME_KEY, getName());
        hashMap.put(DESCRIPTION_KEY, getDetail());
        hashMap.put(PRICE_KEY, getPrice());
        hashMap.put(CATEGORY_KEY, getCategory().toHashMap());

        return hashMap;
    }

    public HashMap<String, Object> toImagesHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(IMAGE_UPLOAD_ID_KEY, getId());
        hashMap.put(IMAGE_UPLOAD_IMAGES_URLS, getImages().toArray());

        return hashMap;
    }
}

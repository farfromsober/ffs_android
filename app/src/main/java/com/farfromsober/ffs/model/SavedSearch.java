package com.farfromsober.ffs.model;

import org.json.JSONObject;

import java.util.HashMap;

public class SavedSearch {

    private static final String QUERY_KEY = "query";
    private static final String USER_KEY = "user";
    private static final String CATEGORY_KEY = "category";

    private String mQuery;
    private User mUser;
    private Category mCategory;

    public SavedSearch(String query, User user, Category category) {
        mQuery = query;
        mUser = user;
        mCategory = category;
    }

    public SavedSearch(JSONObject json) {
        mQuery = json.optString(QUERY_KEY);
        mUser = new User((JSONObject) json.opt(USER_KEY));
        mCategory = new Category((JSONObject) json.opt(CATEGORY_KEY));
    }

    public String getQuery() {
        return mQuery;
    }

    public void setQuery(String query) {
        mQuery = query;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Category getCategory() {
        return mCategory;
    }

    public void setCategory(Category category) {
        mCategory = category;
    }

    @Override
    public String toString() {
        return "SavedSearch{" +
                "mQuery='" + mQuery + '\'' +
                ", mUser=" + mUser +
                ", mCategory=" + mCategory +
                '}';
    }
    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(QUERY_KEY, getQuery());
        hashMap.put(USER_KEY, getUser().toHashMap());
        hashMap.put(CATEGORY_KEY, getCategory().toHashMap());

        return hashMap;
    }
}

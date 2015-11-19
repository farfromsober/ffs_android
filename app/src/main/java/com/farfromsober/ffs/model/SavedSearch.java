package com.farfromsober.ffs.model;

import org.json.JSONObject;

public class SavedSearch {

    private static final String ID_KEY = "_id";
    private static final String QUERY_KEY = "query";
    private static final String USER_KEY = "user";
    private static final String CATEGORY_KEY = "category";

    private String mId;
    private String mQuery;
    private User mUser;
    private Category mCategory;

    public SavedSearch(String id, String query, User user, Category category) {
        mId = id;
        mQuery = query;
        mUser = user;
        mCategory = category;
    }

    public SavedSearch(JSONObject json) {
        mId = json.optString(ID_KEY);
        mQuery = json.optString(QUERY_KEY);
        mUser = new User((JSONObject) json.opt(USER_KEY));
        mCategory = new Category((JSONObject) json.opt(CATEGORY_KEY));
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
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
                "mId='" + mId + '\'' +
                ", mQuery='" + mQuery + '\'' +
                ", mUser=" + mUser +
                ", mCategory=" + mCategory +
                '}';
    }
}

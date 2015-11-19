package com.farfromsober.ffs.model;

import org.json.JSONObject;

public class Category {

    private static final String INDEX_KEY = "_id";
    private static final String NAME_KEY = "azure_id";

    private String mName;
    private double mIndex;

    public Category(String name, double index) {
        mName = name;
        mIndex = index;
    }

    public Category(JSONObject json) {
        mName = json.optString(NAME_KEY);
        mIndex = json.optDouble(INDEX_KEY);
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getIndex() {
        return mIndex;
    }

    public void setIndex(double index) {
        mIndex = index;
    }

    @Override
    public String toString() {
        return "Category{" +
                "mName='" + mName + '\'' +
                ", mIndex=" + mIndex +
                '}';
    }
}

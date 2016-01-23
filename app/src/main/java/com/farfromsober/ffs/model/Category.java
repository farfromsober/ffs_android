package com.farfromsober.ffs.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class Category implements Serializable {

    private static final String INDEX_KEY = "index";
    private static final String NAME_KEY = "name";

    private String mName;
    private int mIndex;

    public Category(String name, int index) {
        super();
        mName = name;
        mIndex = index;
    }

    public Category(JSONObject json) {
        if (hasNeededFields(json)) {
            mName = json.optString(NAME_KEY);
            mIndex = json.optDouble(INDEX_KEY);
        }
    }

    private boolean hasNeededFields(JSONObject json) {
        if (json == null) {
            return false;
        }
        ArrayList<String> neededFields = new ArrayList<>();
        neededFields.add(NAME_KEY);
        neededFields.add(INDEX_KEY);

        Iterator iterator = json.keys();
        ArrayList<String> keysList = new ArrayList<>();
        while(iterator.hasNext()) {
            String key = (String) iterator.next();
            keysList.add(key);
        }

        for (String neededField:neededFields) {
            if (!keysList.contains(neededField)) {
                return false;
            }
        }
        return true;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex = index;
    }

    @Override
    public String toString() {
        /*return "Category{" +
                "mName='" + mName + '\'' +
                ", mIndex=" + mIndex +
                '}';*/
        return mName;
    }

    public HashMap<String, Object> toHashMap() {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(NAME_KEY, getName());
        hashMap.put(INDEX_KEY, getIndex());

        return hashMap;
    }
}

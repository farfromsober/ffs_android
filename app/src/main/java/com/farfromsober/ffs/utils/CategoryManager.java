package com.farfromsober.ffs.utils;

import com.farfromsober.ffs.model.Category;

import java.util.ArrayList;

public class CategoryManager {

    private static CategoryManager mSharedInstance = null;

    private Category[] mCategories;
    private String[] mCategoryNames;

    private CategoryManager(){
        mCategories = new Category[6];
        mCategoryNames = new String[6];
        loadCategories();
    }

    public static CategoryManager getmSharedInstance() {
        if(mSharedInstance == null)
        {
            mSharedInstance = new CategoryManager();
        }
        return mSharedInstance;
    }

    public void loadCategories () {

        Category catMusic = new Category("Music", 5);
        Category catTV = new Category("TV", 4);
        Category catWatch = new Category("Watch", 3);
        Category catiPhone = new Category("iPhone", 2);
        Category catiPad = new Category("iPad", 1);
        Category catMac = new Category("Mac", 0);

        ArrayList<Category> categories = new ArrayList<>();
        categories.add(catMac);
        categories.add(catiPad);
        categories.add(catiPhone);
        categories.add(catWatch);
        categories.add(catTV);
        categories.add(catMusic);

        categories.toArray(mCategories);

        mCategoryNames[0] = catMac.getName().toString();
        mCategoryNames[1] = catiPad.getName().toString();
        mCategoryNames[2] = catiPhone.getName().toString();
        mCategoryNames[3] = catWatch.getName().toString();
        mCategoryNames[4] = catTV.getName().toString();
        mCategoryNames[5] = catMusic.getName().toString();
    }

    public Category[] getCategories() {
        return mCategories;
    }

    public String[] getCategoryNames() {
        return mCategoryNames;
    }
}

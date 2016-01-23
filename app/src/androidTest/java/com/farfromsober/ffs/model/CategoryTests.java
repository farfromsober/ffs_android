package com.farfromsober.ffs.model;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.text.ParseException;

public class CategoryTests extends AndroidTestCase {

    public void testCategoryInitializedWithNilJSONObjectShouldBeCategoryWithNullAttributes() throws JSONException, ParseException {

        Category category = new Category(null);

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.CATEGORY_DEFAULT_INDEX, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectWithoutIndexShouldBeCategoryWithNullAttributes() throws JSONException, ParseException {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategoryWithoutIndex());

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.CATEGORY_DEFAULT_INDEX, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectWithoutNameShouldBeCategoryWithNullAttributes() throws JSONException, ParseException {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategoryWithoutName());

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.CATEGORY_DEFAULT_INDEX, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectShouldBeFullCategory() throws JSONException, ParseException {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategory());

        assertEquals(FakeModelObjectsHelper.CATEGORY_NAME, category.getName());
        assertEquals(FakeModelObjectsHelper.CATEGORY_INDEX, category.getIndex());
    }
}

package com.farfromsober.ffs.model;

import android.test.AndroidTestCase;

public class CategoryTests extends AndroidTestCase {

    public void testCategoryInitializedWithNilJSONObjectShouldBeCategoryWithNullAttributes() {

        Category category = new Category(null);

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.INT_DEFAULT_VALUE, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectWithoutIndexShouldBeCategoryWithNullAttributes() {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategoryWithoutIndex());

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.INT_DEFAULT_VALUE, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectWithoutNameShouldBeCategoryWithNullAttributes() {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategoryWithoutName());

        assertEquals(null, category.getName());
        assertEquals(FakeModelObjectsHelper.INT_DEFAULT_VALUE, category.getIndex());
    }

    public void testCategoryInitializedWithJSONObjectShouldBeFullCategory() {

        Category category = new Category(FakeModelObjectsHelper.fakeJSONCategory());

        assertEquals(FakeModelObjectsHelper.CATEGORY_NAME_VALUE, category.getName());
        assertEquals(FakeModelObjectsHelper.CATEGORY_INDEX_VALUE, category.getIndex());
    }
}

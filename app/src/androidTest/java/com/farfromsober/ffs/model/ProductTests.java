package com.farfromsober.ffs.model;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.text.ParseException;

public class ProductTests extends AndroidTestCase {

    public void testProductInitializedWithNilJSONObjectShouldBeProductWithNullAttributes() throws JSONException, ParseException {

        Product product = new Product(null);
        assertEquals(null, product.getId());
        assertEquals(null, product.getCategory());
        assertEquals(null, product.getDetail());
        assertEquals(null, product.getImages());
        assertEquals(FakeModelObjectsHelper.BOOLEAN_DEFAULT_VALUE, product.getIsSelling());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getPublished());
        assertEquals(null, product.getSeller());
    }

}

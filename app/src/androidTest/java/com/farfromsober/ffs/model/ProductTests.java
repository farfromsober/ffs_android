package com.farfromsober.ffs.model;

import android.test.AndroidTestCase;

public class ProductTests extends AndroidTestCase {

    public void testProductInitializedWithNilJSONObjectShouldBeProductWithNullAttributes() {

        Product product = new Product(null);

        assertEquals(null, product.getId());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getSeller());
    }

    public void testProductInitializedWithJSONObjectWithoutProductIdShouldBeProductWithNullAttributes() {

        Product product = new Product(FakeModelObjectsHelper.fakeJSONProductWithNoProductId());

        assertEquals(null, product.getId());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getSeller());
    }

    public void testProductInitializedWithJSONObjectWithoutNameShouldBeProductWithNullAttributes() {

        Product product = new Product(FakeModelObjectsHelper.fakeJSONProductWithNoName());

        assertEquals(null, product.getId());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getSeller());
    }

    public void testProductInitializedWithJSONObjectWithoutPriceShouldBeProductWithNullAttributes() {

        Product product = new Product(FakeModelObjectsHelper.fakeJSONProductWithNoPrice());

        assertEquals(null, product.getId());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getSeller());
    }

    public void testProductInitializedWithJSONObjectWithoutSellerShouldBeProductWithNullAttributes() {

        Product product = new Product(FakeModelObjectsHelper.fakeJSONProductWithNoSeller());

        assertEquals(null, product.getId());
        assertEquals(null, product.getName());
        assertEquals(null, product.getPrice());
        assertEquals(null, product.getSeller());
    }

    public void testProductInitializedWithJSONObjectShouldBeFullProduct() {

        Product product = new Product(FakeModelObjectsHelper.fakeJSONProduct());

        assertEquals(String.valueOf(FakeModelObjectsHelper.PRODUCT_ID_VALUE), product.getId());
        assertEquals(FakeModelObjectsHelper.PRODUCT_NAME_VALUE, product.getName());
        assertEquals(FakeModelObjectsHelper.PRODUCT_PRICE_VALUE, product.getPrice());

        // TODO: test seller
    }
}

package com.farfromsober.ffs.model;

import android.test.AndroidTestCase;

import org.json.JSONException;

import java.text.ParseException;

public class UserTests extends AndroidTestCase {

    public void testUserInitializedWithNilJSONObjectShouldBeUserWithNullAttributes() {

        User user = new User(null);

        assertEquals(null, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getLatitude());
        assertEquals(null, user.getLongitude());
        assertEquals(null, user.getAvatarURL());
        assertEquals(null, user.getCity());
        assertEquals(null, user.getState());
        assertEquals(FakeModelObjectsHelper.DOUBLE_DEFAULT_VALUE, user.getSales());
    }

    public void testUserInitializedWithJSONObjectWithoutUserIdShouldBeUserWithNullAttributes() {

        User user = new User(FakeModelObjectsHelper.fakeJSONUserWithNoUserId());

        assertEquals(null, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getLatitude());
        assertEquals(null, user.getLongitude());
        assertEquals(null, user.getAvatarURL());
        assertEquals(null, user.getCity());
        assertEquals(null, user.getState());
        assertEquals(FakeModelObjectsHelper.DOUBLE_DEFAULT_VALUE, user.getSales());
    }

    public void testUserInitializedWithJSONObjectWithoutUserNameShouldBeUserWithNullAttributes() {

        User user = new User(FakeModelObjectsHelper.fakeJSONUserWithNoUserName());

        assertEquals(null, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getLatitude());
        assertEquals(null, user.getLongitude());
        assertEquals(null, user.getAvatarURL());
        assertEquals(null, user.getCity());
        assertEquals(null, user.getState());
        assertEquals(FakeModelObjectsHelper.DOUBLE_DEFAULT_VALUE, user.getSales());
    }

    public void testUserInitializedWithJSONObjectWithoutEmailShouldBeUserWithNullAttributes() {

        User user = new User(FakeModelObjectsHelper.fakeJSONUserWithNoEmail());

        assertEquals(null, user.getUserId());
        assertEquals(null, user.getFirstName());
        assertEquals(null, user.getLastName());
        assertEquals(null, user.getEmail());
        assertEquals(null, user.getUsername());
        assertEquals(null, user.getLatitude());
        assertEquals(null, user.getLongitude());
        assertEquals(null, user.getAvatarURL());
        assertEquals(null, user.getCity());
        assertEquals(null, user.getState());
        assertEquals(FakeModelObjectsHelper.DOUBLE_DEFAULT_VALUE, user.getSales());
    }

    public void testUserInitializedWithJSONObjectShouldBeFullUser() throws JSONException, ParseException {

        User user = new User(FakeModelObjectsHelper.fakeJSONUser());

        assertEquals(String.valueOf(FakeModelObjectsHelper.USER_ID_VALUE), user.getUserId());
        assertEquals(FakeModelObjectsHelper.USER_USERNAME_VALUE, user.getUsername());
        assertEquals(FakeModelObjectsHelper.USER_EMAIL_VALUE, user.getEmail());
    }
}

package com.farfromsober.generalutils;

import android.test.AndroidTestCase;

import com.google.gson.Gson;

public class SharedPreferencesTests extends AndroidTestCase {

    private final String stringKey = "stringKey";
    private final String intKey = "intKey";
    private final String longKey = "longKey";
    private final String booleanKey = "booleanKey";

    private final String stringValue = "string value to save";
    private final int intValue = 324;
    private final long longValue = 345;
    private final boolean booleanValue = true;

    class MyObject extends Object{

        String mStringAttribute = stringValue;
        int mIntAttribute = intValue;
        long mLongAttribute = longValue;
        boolean mBooleanAttribute = booleanValue;

        public MyObject() {
            super();
        }

        public String getStringAttribute() {
            return mStringAttribute;
        }

        public int getIntAttribute() {
            return mIntAttribute;
        }

        public long getLongAttribute() {
            return mLongAttribute;
        }

        public boolean isBooleanAttribute() {
            return mBooleanAttribute;
        }
    }

    public void testSaveRecoverString() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), stringKey, stringValue);
        assertEquals(stringValue, SharedPreferencesGeneralManager.getPreferenceString(getContext(), stringKey));
    }
    public void testSaveRecoverInt() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), intKey, intValue);
        assertEquals(intValue, SharedPreferencesGeneralManager.getPreferenceInt(getContext(), intKey));
    }
    public void testSaveRecoverLong() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), longKey, longValue);
        assertEquals(longValue, SharedPreferencesGeneralManager.getPreferenceLong(getContext(), longKey));
    }
    public void testSaveRecoverBoolean() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), booleanKey, booleanValue);
        assertEquals(booleanValue, SharedPreferencesGeneralManager.getPreferenceBoolean(getContext(), booleanKey));
    }

    public void testDeleteString() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), stringKey, stringValue);
        SharedPreferencesGeneralManager.removePreference(getContext(), stringKey);

        assertEquals(SharedPreferencesGeneralManager.DEFAULT_STRING_VALUE,
                SharedPreferencesGeneralManager.getPreferenceString(getContext(), stringKey));
    }
    public void testDeleteInt() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), intKey, intValue);
        SharedPreferencesGeneralManager.removePreference(getContext(), intKey);

        assertEquals(SharedPreferencesGeneralManager.DEFAULT_INT_VALUE,
                SharedPreferencesGeneralManager.getPreferenceInt(getContext(), intKey));
    }
    public void testDeleteLong() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), longKey, longValue);
        SharedPreferencesGeneralManager.removePreference(getContext(), longKey);

        assertEquals(SharedPreferencesGeneralManager.DEFAULT_LONG_VALUE,
                SharedPreferencesGeneralManager.getPreferenceLong(getContext(), longKey));
    }
    public void testDeleteBoolean() {
        SharedPreferencesGeneralManager.savePreferece(getContext(), booleanKey, booleanValue);
        SharedPreferencesGeneralManager.removePreference(getContext(), booleanKey);

        assertEquals(SharedPreferencesGeneralManager.DEFAULT_BOOLEAN_VALUE,
                SharedPreferencesGeneralManager.getPreferenceBoolean(getContext(), booleanKey));
    }

    public void testSaveRecoverObject() {

        MyObject myObject = new MyObject();

        String string = SharedPreferencesGeneralManager.objectToJSONString(myObject);
        MyObject myObject2 = (MyObject) SharedPreferencesGeneralManager.JSONStringToObject(string, MyObject.class);

        assertEquals(myObject.getStringAttribute(), myObject2.getStringAttribute());
        assertEquals(myObject.getIntAttribute(), myObject2.getIntAttribute());
        assertEquals(myObject.getLongAttribute(), myObject2.getLongAttribute());
        assertEquals(myObject.isBooleanAttribute(), myObject2.isBooleanAttribute());
    }
}

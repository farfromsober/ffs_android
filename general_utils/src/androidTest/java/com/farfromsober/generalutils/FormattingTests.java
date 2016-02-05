package com.farfromsober.generalutils;

import android.test.AndroidTestCase;

import java.util.Date;

public class FormattingTests extends AndroidTestCase {

    private final String originString = "test capitalize first or all words";
    private final String capitalizeFirstWordString = "Test capitalize first or all words";
    private final String capitalizeAllWordString = "Test Capitalize First Or All Words";
    private final String dateValue = "2016-01-19T22:51:01.556468Z";
    private final String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";

    public void testcapitalizeFirstWord() {
        assertEquals(capitalizeFirstWordString, FormattingManager.capitalizeFirstWord(originString));
    }

    public void testcapitalizeAllWords() {
        assertEquals(capitalizeAllWordString, FormattingManager.capitalizeAllWords(originString));
    }

    public void testdateFromStringWithFormat() {
        Date date = DateManager.dateFromString(dateValue, dateFormat);
        assertNotSame(date, new Date());
    }

}

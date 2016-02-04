package com.farfromsober.generalutils;

import android.test.AndroidTestCase;

public class FormattingTests extends AndroidTestCase {

    private final String originString = "test capitalize first or all words";
    private final String capitalizeFirstWordString = "Test capitalize first or all words";
    private final String capitalizeAllWordString = "Test Capitalize First Or All Words";

    public void testcapitalizeFirstWord() {
        assertEquals(capitalizeFirstWordString, FormattingManager.capitalizeFirstWord(originString));
    }

    public void testcapitalizeAllWords() {
        assertEquals(capitalizeAllWordString, FormattingManager.capitalizeAllWords(originString));
    }

}

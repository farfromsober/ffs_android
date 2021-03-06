package com.farfromsober.generalutils;

public class FormattingManager {

    public static String capitalizeFirstWord(String inputString) {
        if (inputString.length() == 0) {
            return inputString;
        }
        return inputString.substring(0,1).toUpperCase() + inputString.substring(1).toLowerCase();
    }

    public static String capitalizeAllWords(String inputString) {
        if (inputString.length() == 0) {
            return inputString;
        }
        String[] words = inputString.split(" ");
        StringBuilder resultantString = new StringBuilder();
        for (String word : words) {
            resultantString.append(FormattingManager.capitalizeFirstWord(word)).append(" ");
        }
        return resultantString.toString().trim();
    }
}

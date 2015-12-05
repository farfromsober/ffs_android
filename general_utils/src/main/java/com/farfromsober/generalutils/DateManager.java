package com.farfromsober.generalutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateManager {

    public static String stringFromDate(Date date, String stringFormat) {
        if (date.equals(null)) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(stringFormat, Locale.getDefault());
        String formattedDate = String.format("%s", dateFormat.format(date));

        return formattedDate;
    }

    public static Date dateFromString(String string, String stringFormat) throws ParseException {
        return new SimpleDateFormat(stringFormat).parse(string);
    }

    public static String timestampFromDate(Date date) {
        String format = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'";
        return  stringFromDate(date, format);
    }

    public static Calendar dateToCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static Date calendarToDate(Calendar calendar) {
        return calendar.getTime();
    }
}

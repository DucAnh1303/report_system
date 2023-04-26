package com.example.itspower.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String TIME_ZONE = "Asia/Ho_Chi_Minh";
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd hh:mm:ss";
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    private static SimpleDateFormat sdf = new SimpleDateFormat();
    public static String formatDate(Date date, String dateType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        return dateFormat.format(date);
    }
    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        sdf.applyPattern(FORMAT_DATE);
        return sdf.format(date);
    }

    public static String formatDateTime(Date date) {
        if (date == null) {
            return null;
        }
        sdf.applyPattern(FORMAT_DATE_TIME);
        return sdf.format(date);
    }

    public static Date fromString(String date) {
        if (date == null) {
            return null;
        }
        try {
            DateFormat dateFormat = new SimpleDateFormat(FORMAT_DATE);
            Date convertStringToDate = dateFormat.parse(date);
            return convertStringToDate;
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public static String formatDateString(String date , String dateType) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateType);
        Date date1 = null;
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        DateFormat outputFormatter = new SimpleDateFormat(dateType);
        String formatDateString = outputFormatter.format(date1);
        return formatDateString;
    }
}

package com.petboarding.controllers.utils;

import net.bytebuddy.TypeCache;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static SimpleDateFormat parseFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static SimpleDateFormat showFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);

    public static String format(Date date, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format, Locale.ENGLISH);
        return formatter.format(date);
    }

    public static Date parse(String strDate, String format) {
        SimpleDateFormat parser = new SimpleDateFormat(format, Locale.ENGLISH);
        Date date = null;
        try{
            date = parser.parse(strDate);
        } catch(ParseException exception) {
            //TODO handle the exception
        }
        return date;
    }
}

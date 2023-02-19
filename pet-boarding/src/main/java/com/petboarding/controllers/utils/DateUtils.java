package com.petboarding.controllers.utils;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class DateUtils {

    public static SimpleDateFormat parseFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
    public static SimpleDateFormat showFormatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
}

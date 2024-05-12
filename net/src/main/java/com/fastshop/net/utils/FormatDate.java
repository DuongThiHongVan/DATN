package com.fastshop.net.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatDate {
    public static String parse() {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate= DateFor.format(new Date());
        return stringDate;
    }

    public static String parse(String pattern) {
        SimpleDateFormat DateFor = new SimpleDateFormat(pattern);
        String stringDate= DateFor.format(new Date());
        return stringDate;
    }

    public static String parse(Date date) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        String stringDate= DateFor.format(date);
        return stringDate;
    }

    public static String parse(Date date, String pattern) {
        SimpleDateFormat DateFor = new SimpleDateFormat(pattern);
        String stringDate= DateFor.format(date);
        return stringDate;
    }
}

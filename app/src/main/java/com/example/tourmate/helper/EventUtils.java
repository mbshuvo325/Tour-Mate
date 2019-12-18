package com.example.tourmate.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventUtils {

    public static final String DEGREE = "\u00B0";
    public static final String UNIT_CELCIUS_SYMBOL = "C";


    public static final String WEATHER_CONDITION_ICON_PREFIX = "https://openweathermap.org/img/wn/";

    public static String getCurrentDate(){

        return new SimpleDateFormat("EEE, dd-MM-yyyy HH:mm:ss").format(new Date());
    }

    public static String getFormattedDate(long dt){
        Date date = new Date(dt * 1000);
        return new SimpleDateFormat("EEE, dd-MM-yyyy")
                .format(date);
    }

    public static String getTime(long dt){
        Date date = new Date(dt * 1000);
        return new SimpleDateFormat(" hh:mm aa")
                .format(date);
    }
}

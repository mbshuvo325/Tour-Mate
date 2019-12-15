package com.example.tourmate.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventUtils {


    public static final String WEATHER_CONDITION_ICON_PREFIX = "https://openweathermap.org/img/wn/";

    public static String getCurrentDate(){

        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }
}

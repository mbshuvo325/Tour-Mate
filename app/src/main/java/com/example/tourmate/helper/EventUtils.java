package com.example.tourmate.helper;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EventUtils {

    public static String getCurrentDate(){

        return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
    }
}

package com.testprojects.firstapp.service;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;


@Component
public class DateFormatter {

    String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }
}

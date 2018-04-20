package com.testprojects.firstapp.services;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ChangesLog {

    private List<String> changesList = new ArrayList<>();


    String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void loadFile(String fileName){

        changesList.add("CHANGES LOG for: "+getCurrentDate());
        changesList.add("LOADED FILE: "+fileName);
        changesList.add("EDITIONS:");
    }

    public void editProperty(String key, String oldValue, String newValue){
        changesList.add("EDITED: "+key+"###"+oldValue+" = "+newValue);
    }

    public void addProperty(String key, String value){
        changesList.add("ADDED: "+key+"###"+value);
    }

    public void removeProperty(String key, String value){
        changesList.add("REMOVED: "+key+"###"+value);
    }

    public List<String> getChangesList() {
        return changesList;
    }
}

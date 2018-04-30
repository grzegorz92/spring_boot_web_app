package com.testprojects.firstapp.services;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ChangesLog {

    private List<String> changesLog = new ArrayList<>();


    String getCurrentDate() {
        SimpleDateFormat sdfDate = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        Date now = new Date();
        String strDate = sdfDate.format(now);
        return strDate;
    }

    public void loadFileLog(String fileName){

        changesLog.add("CHANGES LOG for: "+getCurrentDate());
        changesLog.add("LOADED FILE: "+fileName);
        changesLog.add("EDITIONS:");
    }

    public void editedPropertyLog(String key, String oldValue, String newValue){
        changesLog.add("EDITED: "+key+"###"+oldValue+" = "+newValue);
    }

    public void addPropertyLog(String key, String value){
        changesLog.add("ADDED: "+key+"###"+value);
    }

    public void removePropertyLog(String key, String value){
        changesLog.add("REMOVED: "+key+"###"+value);
    }

    public List<String> getChangesLog() {
        return changesLog;
    }

    public void clearChangesLog(){

        changesLog.clear();
    }
}

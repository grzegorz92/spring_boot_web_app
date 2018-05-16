package com.testprojects.firstapp.service;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChangesLog {

    private List<String> changesLog = new ArrayList<>();
    private DateFormatter date = new DateFormatter();

    public ChangesLog() {
    }


    public void logFileLoading(String fileName){

        changesLog.add("CHANGES LOG for: "+date.getCurrentDate());
        changesLog.add("LOADED FILE: "+fileName);
        changesLog.add("EDITIONS:");
    }

    public void logPropertyEdition(String key, String oldValue, String newValue){
        changesLog.add("EDITED: "+key+"###"+oldValue+" = "+newValue);
    }

    public void logPropertyAddition(String key, String value){
        changesLog.add("ADDED: "+key+"###"+value);
    }

    public void logPropertyRemoving(String key, String value){
        changesLog.add("REMOVED: "+key+"###"+value);
    }

    public List<String> getChangesLog() {
        return changesLog;
    }

    public void clearChangesLog(){

        changesLog.clear();
    }
}

package com.testprojects.firstapp.service;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class ChangesLogTest {


    ChangesLog changesLog = new ChangesLog();
    DateFormatter date = new DateFormatter();

    @Test
    public void logFileLoading_test(){

        String fileName = "file";

        List<String> logs = new ArrayList<>();
        logs.add("CHANGES LOG for: "+date.getCurrentDate());
        logs.add("LOADED FILE: "+fileName);
        logs.add("EDITIONS:");

        changesLog.logFileLoading(fileName);

        assertEquals(logs,changesLog.getChangesLog());
    }

    @Test
    public void logPropertyEdition_test(){

        String key = "Name";
        String oldValue = "John";
        String newValue = "Richard";

        List<String> logs = new ArrayList<>();
        logs.add("EDITED: "+key+"###"+oldValue+" = "+newValue);

        changesLog.logPropertyEdition(key,oldValue,newValue);

        assertEquals(logs, changesLog.getChangesLog());
    }

    @Test
    public void logPropertyAddition_test(){

        String key = "Name";
        String value = "John";

        List<String> logs = new ArrayList<>();
        logs.add("ADDED: "+key+"###"+value);

        changesLog.logPropertyAddition(key,value);

        assertEquals(logs, changesLog.getChangesLog());
    }

    @Test
    public void logPropertyRemoving_test(){

        String key = "Name";
        String value = "John";

        List<String> logs = new ArrayList<>();
        logs.add("REMOVED: "+key+"###"+value);

        changesLog.logPropertyRemoving(key,value);

        assertEquals(logs, changesLog.getChangesLog());
    }

}
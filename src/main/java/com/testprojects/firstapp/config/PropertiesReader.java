package com.testprojects.firstapp.config;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component
public class PropertiesReader {

    private Properties props = new Properties();
    private InputStream in;

    public void setIn(InputStream in) {
        this.in = in;
    }

    public InputStream getIn() {
        return in;
    }

    public void getFile(){

        if (this.in != null) {
            props.clear();//clear previous data, before load new .properties file

            try {
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String,String> loadProperties() {

        Map<String,String> map = new HashMap<>();
        Enumeration<?> e = props.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            map.put(key, props.getProperty(key));
        }
       return map;
    }

    public void editProperties(String key, String value){
        props.setProperty(key, value);
    }

    public void removeProperties(String key){
        props.remove(key);
    }


}


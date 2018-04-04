package com.testprojects.firstapp.config;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component// without this intellij says "could not autowire no beans of type found"
public class PropertiesReader {

    private Properties props = new Properties();
    private InputStream in;

    public void setIn(InputStream in) {
        this.in = in;
    }

    public InputStream getIn() {
        return in;
    }

    public Map<String,String> loadProperties() {

// Reading file from predefined path
//        File file = new File("C:\\Users\\grjk\\Desktop\\properties\\employees.properties");
//
//        try (InputStream in = new FileInputStream(file)) {
//            props.load(in);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        try {
            props.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //List<String> list = new ArrayList<>(); //returning key+value as a one string
        Map<String,String> map = new HashMap<>(); //returning key and value separately

        Enumeration<?> e = props.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            //System.out.println(key + ": " + props.getProperty(key));
            //list.add(key + ": " + props.getProperty(key)); //returning key+value as a one string
            map.put(key,props.getProperty(key));             //returning key and value separately
        }

        //return list;
        return map;
    }


}


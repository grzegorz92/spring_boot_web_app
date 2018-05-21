package com.testprojects.firstapp.service;

import java.util.*;


public class JsonFormatter {

    private List<Map<String,String>> propertiesList = new ArrayList<>();
    private Map<String, String> formatProperties = new HashMap<>();


    public List<Map<String,String>> formatPropertiesToJson(PropertiesService propertiesService){

        System.out.println("HERE: "+propertiesService.getProperties());
        for(Map.Entry<String,String> item: propertiesService.getProperties().entrySet()){

            String key=item.getKey();
            String value=item.getValue();

            formatProperties.put("key",key);
            System.out.println("KEY: "+key);
            formatProperties.put("value",value);
            System.out.println("VALUE: "+value);
            propertiesList.add(formatProperties);
            System.out.println("LISTA"+formatProperties);
            System.out.println();
        }
        System.out.println(propertiesList);


        return propertiesList;
    }






}

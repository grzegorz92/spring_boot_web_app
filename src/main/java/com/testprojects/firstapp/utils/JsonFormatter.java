package com.testprojects.firstapp.utils;

import java.util.*;


public class JsonFormatter {

    private List<Map<String, String>> propertiesList = new ArrayList<>();

    public List<Map<String, String>> formatPropertiesToJson(Map<String,String> propertiesMap) {

        propertiesList.clear();

        for (Map.Entry<String, String> item : propertiesMap.entrySet()) {

            Map<String, String> formatProperties = new LinkedHashMap<>();
            String key = item.getKey();
            String value = item.getValue();

            formatProperties.put("key", key);
            formatProperties.put("value", value);
            propertiesList.add(formatProperties);



        }
        return propertiesList;
    }
}

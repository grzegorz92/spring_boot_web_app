package com.testprojects.firstapp.service;

import java.util.*;


public class JsonFormatter {

    private List<Map<String, String>> propertiesList = new ArrayList<>();

    public List<Map<String, String>> formatPropertiesToJson(PropertiesService propertiesService) {

        propertiesList.clear();

        for (Map.Entry<String, String> item : propertiesService.getProperties().entrySet()) {

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

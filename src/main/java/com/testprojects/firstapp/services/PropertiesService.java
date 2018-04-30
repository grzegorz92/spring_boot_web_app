package com.testprojects.firstapp.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;
import java.util.Map;

public interface PropertiesService {

    void getFile(MultipartFile file);

    void saveFileAsProperties(OutputStream outputStream);

    void saveFileAsJson(OutputStream outputStream);

    void saveFileAsYaml(OutputStream outputStream);

    void downloadLog(OutputStream outputStream);

    Map<String,String> loadProperties();

    void editProperties(String key, String oldValue, String newValue);

    void addProperties(String key, String value);

    void removeProperties(String key, String value);



}

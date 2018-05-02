package com.testprojects.firstapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.testprojects.firstapp.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@Service
public class PropertiesServiceImpl {

    private Properties properties = new Properties();
    private ChangesLog log = new ChangesLog();
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private ObjectMapper jsonMapper = new ObjectMapper();
    private ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());


    //LOAD AND SAVE PROPERTIES FILE
    public void getFile(MultipartFile file) throws BusinessException {

        properties.clear();
        log.clearChangesLog();
        try {
            properties.load(file.getInputStream());
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
        log.logFileLoading(file.getOriginalFilename());
        logger.info("FILE LOADED: " + file.getOriginalFilename());
    }

    public void saveFileAsProperties(OutputStream outputStream) throws BusinessException {

        try {
            properties.store(outputStream, null);
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
    }

    public void saveFileAsJson(OutputStream outputStream) throws BusinessException {

        try {
            jsonMapper.writeValue(outputStream, properties);
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
    }


    public void saveFileAsYaml(OutputStream outputStream) throws BusinessException {

        try {
            yamlMapper.writeValue(outputStream, properties);
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
    }

    //Download audit_log file
    public void downloadLog(OutputStream outputStream) throws BusinessException {

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream("logs/audit_log.log");
        } catch (FileNotFoundException e) {
            throw new BusinessException("File Not Found!");
        }
        try {
            FileCopyUtils.copy(fileInputStream, outputStream);
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
    }

    //LOADING PROPERTIES FROM FILE
    public Map<String, String> loadProperties() {

        Map<String, String> loadedProperties = new HashMap<>();
        Enumeration<?> e = properties.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            loadedProperties.put(key, properties.getProperty(key));
        }
        return loadedProperties;
    }

    //PROPERTIES EDITIONS
    public void editProperties(String key, String oldValue, String newValue) {

        //If the same value for given key is added nothing happens
        if (!oldValue.equals(newValue)) {
            properties.setProperty(key, newValue);
            log.logPropertyEdition(key, oldValue, newValue);
            logger.info("EDITED: " + key + "###" + oldValue + "=" + newValue);
        }
    }

    public void addProperties(String key, String value) {

        //Editing in ADD field and generating log from this operation disabled
        if (properties.get(key) == null) {
            properties.setProperty(key, value);
            log.logPropertyAddition(key, value);
            logger.info("ADDED: " + key + "###" + value);
        }
    }

    public void removeProperties(String key, String value) {

        properties.remove(key);
        log.logPropertyRemoving(key, value);
        logger.info("REMOVED: " + key + "###" + value);
    }

    //OTHERS
    public List<String> getLog() {
        return log.getChangesLog();
    }

    //setters for PropertiesReaderTest class to wire mocked Properties class with this one used here
    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public void setLog(ChangesLog log) {
        this.log = log;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public void setJsonMapper(ObjectMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    public void setYamlMapper(ObjectMapper yamlMapper) {
        this.yamlMapper = yamlMapper;
    }


}


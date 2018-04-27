package com.testprojects.firstapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.testprojects.firstapp.exception.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;


@Component
public class PropertiesReader {

    private Properties props = new Properties();
    private ChangesLog log = new ChangesLog();
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private ObjectMapper jsonMapper = new ObjectMapper();
    private ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());


    //LOAD AND SAVE PROPERTIES FILE
    public void getFile(MultipartFile file) throws BusinessException {

        props.clear();
        log.clearChangesList();
        try {
            props.load(file.getInputStream());
        } catch (IOException e) {
            throw new BusinessException("Cannot load file");
        }
        log.loadFile(file.getOriginalFilename());
        logger.info("FILE LOADED: " + file.getOriginalFilename());
    }

    public void saveFileAsProperties(OutputStream os) throws IOException {

        props.store(os, null);
    }

    public void saveFileAsJson(OutputStream os) throws IOException {

        jsonMapper.writeValue(os, props);
    }


    public void saveFileAsYaml(OutputStream os) throws IOException {

        yamlMapper.writeValue(os, props);
    }

    //Download audit_log file
    public void downloadLog(OutputStream os) throws IOException {

        FileInputStream in = new FileInputStream("logs/audit_log.log");
        FileCopyUtils.copy(in, os);
    }

    //LOADING PROPERTIES FROM FILE
    public Map<String, String> loadProperties() {

        Map<String, String> map = new HashMap<>();
        Enumeration<?> e = props.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            map.put(key, props.getProperty(key));
        }
        System.out.println(map);
        return map;
    }

    //PROPERTIES EDITIONS
    public void editProperties(String key, String oldValue, String newValue) {

        //If the same value for given key is added nothing happens
        if (!oldValue.equals(newValue)) {
            props.setProperty(key, newValue);
            log.editProperty(key, oldValue, newValue);
            logger.info("EDITED: " + key + "###" + oldValue + "=" + newValue);
        }
    }

    public void addProperties(String key, String value) {

        //Editing in ADD field and generating log from this operation disabled
        if (props.get(key) == null) {
            props.setProperty(key, value);
            log.addProperty(key, value);
            logger.info("ADDED: " + key + "###" + value);
        }
    }

    public void removeProperties(String key, String value) {

        props.remove(key);
        log.removeProperty(key, value);
        logger.info("REMOVED: " + key + "###" + value);
    }

    //OTHERS
    public List<String> getLog() {
        return log.getChangesList();
    }


    //setters for PropertiesReaderTest class to wire mocked Properties class with this one used here
    public void setProps(Properties props) {
        this.props = props;
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


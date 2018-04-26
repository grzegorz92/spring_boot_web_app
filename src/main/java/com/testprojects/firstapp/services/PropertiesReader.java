package com.testprojects.firstapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import java.io.*;
import java.util.*;



@Component
public class PropertiesReader {

    private Properties props = new Properties();
    private ChangesLog log = new ChangesLog();
    private InputStream in;
    private Logger logger = LoggerFactory.getLogger(getClass().getName());
    private ObjectMapper jsonMapper = new ObjectMapper();
    private ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());


    //LOAD AND SAVE PROPERTIES FILE
    public void getFile(String fileName) throws IOException {

        if (this.in != null) {
            props.clear();//clear previous data, before load new .properties file
            log.clearChangesList();

            try {
                props.load(in);
            } catch (IOException e) {
               // e.printStackTrace();
                // new BS
                logger.error("IOException is caught");
                throw e;
            }
        }
        log.loadFile(fileName);
        logger.info("FILE LOADED: "+fileName);
    }

    public void saveFileAsProperties(OutputStream os) throws IOException {

        try {
            props.store(os,null);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void saveFileAsJson(OutputStream os) throws Exception {

        try {
            jsonMapper.writeValueAsString(props);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public void saveFileAsYaml(OutputStream os) throws IOException {

        try {
            yamlMapper.writeValue(os, props);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    //Download audit_log file
    public void downloadLog(OutputStream os) throws Exception {

//        List<String> logList = new ArrayList<>();
//        String ln; //Has to be used, otherwise bufferedReader doesn'Props read all of the lines
//
//        try (BufferedReader br = new BufferedReader(new FileReader("logs/audit_log.log"))) {
//
//            while((ln = br.readLine())!=null){
//                logList.add(ln);
//            }
//        }catch(FileNotFoundException e){
//            e.printStackTrace();
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//
//        PrintWriter pw = new PrintWriter(os);
//
//        for(String itr: logList){
//            pw.println(itr);
//        }
//
//        pw.close();
        /////

        //Spring method that copy inputStream(here internal audit_log) to outputstream(audit_log downloaded by user)
        FileInputStream in = null;
        try {
            in = new FileInputStream("logs/audit_log.log");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }

        try {
            FileCopyUtils.copy(in,os);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }


    //LOADING PROPERTIES FROM FILE
    public Map<String,String> loadProperties() {

        Map<String,String> map = new HashMap<>();
        Enumeration<?> e = props.propertyNames();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            map.put(key, props.getProperty(key));
        }
       return map;
    }


    //PROPERTIES EDITIONS
    public void editProperties(String key, String oldValue, String newValue){

        //System.out.println("EDITED: "+key+"###"+oldValue+"="+newValue);

        //Blokada przed dodaniem wpisu do loga, gdy user dodaje te sama wartosc do klucza
        if (!oldValue.equals(newValue)){
            props.setProperty(key, newValue);
            log.editProperty(key, oldValue, newValue);
            logger.info("EDITED: "+key+"###"+oldValue+"="+newValue);
        }

    }

    public void addProperties(String key, String value){

        //blokada przed wykonaniem operacji EDYCJI w polu DODAJ i zapisaniem tego do loga

        if (props.get(key) == null) {
            props.setProperty(key, value);
            log.addProperty(key, value);
            logger.info("ADDED: "+key+"###"+value);
        }
    }

    public void removeProperties(String key, String value){
        props.remove(key);
        //System.out.println("REMOVED: "+key);
        log.removeProperty(key, value);
        logger.info("REMOVED: "+key+"###"+value);
    }

    //OTHERS
    public List<String> getLog() {
        return log.getChangesList();
    }

    public void setIn(InputStream in) {
        this.in = in;
    }

    //setters for PropertiesReaderTest class to "connect" mocked Properties class with this one used here
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


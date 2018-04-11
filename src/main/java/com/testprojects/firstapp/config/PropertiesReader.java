package com.testprojects.firstapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
//import java.util.logging.Logger;


@Component
public class PropertiesReader {

    private Properties props = new Properties();
    private ChangesLog log = new ChangesLog();
    private InputStream in;
    //private Logger logger = Logger.getLogger(getClass().getName());
    private Logger logger = LoggerFactory.getLogger(getClass().getName());




    //LOAD AND SAVE PROPERTIES FILE

    public void getFile(String fileName){

        if (this.in != null) {
            props.clear();//clear previous data, before load new .properties file
            log.getChangesList().clear();

            try {
                props.load(in);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //System.out.println("FILE LOADED: "+fileName);
        log.loadFile(fileName);
        logger.info("FILE LOADED: "+fileName);
    }

    public void saveFileAsProperties(OutputStream os){

        try {
            props.store(os,null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFileAsJson(OutputStream os){
        ObjectMapper mapper = new ObjectMapper();

        try {
            mapper.writeValueAsString(props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void saveFileAsYaml(OutputStream os){
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());

        try {
            mapper.writeValue(os, props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Download audit_log file

    public void downloadLog(OutputStream os){

//        List<String> logList = new ArrayList<>();
//        String ln; //Has to be used, otherwise bufferedReader doesn't read all of the lines
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
        }

        try {
            FileCopyUtils.copy(in,os);
        } catch (IOException e) {
            e.printStackTrace();
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
        props.setProperty(key, newValue);
        //System.out.println("EDITED: "+key+"###"+oldValue+"="+newValue);

        //Blokada przed dodaniem wpisu do loga, gdy user dodaje te sama wartosc do klucza
        if (!oldValue.equals(newValue)){
            log.editProperty(key, oldValue, newValue);
            logger.info("EDITED: "+key+"###"+oldValue+"="+newValue);
        }

    }

    public void addProperties(String key, String value){

        //Blokada przed dodaniem wpisu do loga, gdy ktos stara sie dodac ta sama lub inna wartosc do instniejacego juz keya
        if (props.getProperty(key) == null) {
            log.addProperty(key, value);
            logger.info("ADDED: "+key+"###"+value);
        }

        //blokada przed wykonaniem operacji EDYCJI w polu DODAJ.
        //W przyszlosci zamiast tego moze byc wykorzystana walidacja (error message: given key already exist)
        if (props.get(key)==value || props.get(key)==null) {
            props.setProperty(key, value);
        }

        //jesli nie chcemy blokady Edycji w polu Dodawania, konieczne jest zapisywanie
        //Loga w SECIE nie LISCIE, zeby nie dodawal do LOGA takich samych zmian

        //System.out.println("ADDED: "+key+"###"+value);

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

}


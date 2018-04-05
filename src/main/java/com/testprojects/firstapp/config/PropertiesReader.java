package com.testprojects.firstapp.config;

import org.springframework.stereotype.Component;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;


@Component
public class PropertiesReader {

    private Properties props = new Properties();
    private ChangesLog log = new ChangesLog();
    private InputStream in;

    public void setIn(InputStream in) {
        this.in = in;
    }

    public InputStream getIn() {
        return in;
    }

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
    }

    public void saveFile(){

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

    public void editProperties(String key, String oldValue, String newValue){
        props.setProperty(key, newValue);
        //System.out.println("EDITED: "+key+"###"+oldValue+"="+newValue);

        //Blokada przed dodaniem wpisu do loga, gdy user dodaje te sama wartosc do klucza
        if (!oldValue.equals(newValue)){
            log.editProperty(key, oldValue, newValue);
        }

    }

    public void addProperties(String key, String value){

        //Blokada przed dodaniem wpisu do loga, gdy ktos stara sie dodac ta sama lub inna wartosc do instniejacego juz keya
        if (props.getProperty(key) == null) {
            log.addProperty(key, value);
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
    }

    public List<String> getLog() {
        return log.getChangesList();
    }

    public Properties getProps() {
        return props;
    }
}


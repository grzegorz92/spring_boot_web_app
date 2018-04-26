package com.testprojects.firstapp.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.*;
import java.util.*;
import org.slf4j.Logger;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesReaderTest {

    @Mock
    ChangesLog log;


    @Mock
    Properties properties;

    @Mock
    InputStream in;

    @Mock
    Logger logger;

    @Mock
    OutputStream out;


    @Mock
    ObjectMapper mapper;



    PropertiesReader pr;

    @Before
   public void setUp() {

        pr = new PropertiesReader();
       // pr.setIn(in); //individual for each test method
        pr.setProps(this.properties);
        pr.setLog(this.log);
        pr.setLogger(this.logger);
        pr.setJsonMapper(this.mapper);
        pr.setYamlMapper(this.mapper);

    }


    @Test
    public void getFile_whenInputStreamIsNull_thenPropsClearIsNeverInvoked() throws Exception {

        pr.setIn(null);
        pr.getFile("File Name");
        verify(properties,never()).clear();
    }

    @Test
    public void getFile_whenInputStreamIsNull_thenLogClearChangesListIsNeverInvoked() throws Exception {

        pr.setIn(null);
        pr.getFile("File Name");
        verify(log,never()).clearChangesList();
    }

    @Test
    public void getFile_whenInputStreamIsNotNull_thenPropsClearIsInvoked() throws Exception {

       pr.setIn(in);
       pr.getFile("File Name");
       verify(properties,times(1)).clear();
    }

    @Test
    public void getFile_whenInputStreamIsNotNull_thenLogClearChangesListIsInvoked() throws Exception {

        pr.setIn(in);
        pr.getFile("File Name");
        verify(log,times(1)).clearChangesList();
    }



    @Test
    public void getFile_whenInputStreamIsNotNull_andIOExIsNotCaught_thenPropsLoadIsInvoked() throws Exception{

       pr.setIn(in);
       pr.getFile("File Name");
       verify(properties, atLeastOnce()).load(in);
    }

    @Test(expected = IOException.class)
    public void getFile_whenInputStreamIsNotNull_andIOExIsCaught_thenIOExIsThrown() throws Exception{

        pr.setIn(in);
        doThrow(IOException.class).when(properties).load(in);
        pr.getFile("File Name");
    }

    @Test
    public void getFile_logLoadFileInvocationVerifying() throws Exception {

        pr.setIn(in);
        String fileName = "File_name";
        pr.getFile(fileName);

        verify(log,atLeastOnce()).loadFile(fileName);
    }

    @Test
    public void getFile_loggerInfoInvocationVerifying() throws Exception {

        pr.setIn(in);
        String fileName = "File_name";
        pr.getFile(fileName);

        verify(logger,atLeastOnce()).info("FILE LOADED: " +fileName);
    }

    @Test
    public void saveFileAsProperties_whenIOExIsNotCaught_thenPropsStoreIsInvoked() throws Exception {

        pr.saveFileAsProperties(out);
        verify(properties,times(1)).store(out,null);
    }

    @Test(expected = IOException.class)
    public void saveFileAsProperties_whenIOExIsCaught_thenIOExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(properties).store(out,null);
        pr.saveFileAsProperties(out);
    }

    @Test
    public void saveFileAsJson_whenIOExIsNotCaught_thenMapperWriteValueAsStringIsInvoked() throws Exception {

        pr.saveFileAsJson(out);
        verify(mapper,times(1)).writeValueAsString(properties);
    }

    @Test(expected = JsonProcessingException.class)
    public void saveFileAsJson_whenJsonProcessingExIsCaught_thenJsonProcessingExceptionIsThrown() throws Exception {

        when(mapper.writeValueAsString(properties)).thenThrow(JsonProcessingException.class);
        pr.saveFileAsJson(out);
    }

    @Test
    public void saveFileAsYaml_whenIOExIsNotCaught_thenMapperWriteValueAsStringIsInvoked() throws Exception {

        pr.saveFileAsYaml(out);
        verify(mapper,times(1)).writeValue(out, properties);
    }

    @Test(expected = IOException.class)
    public void saveFileAsYaml_whenIOExIsCaught_thenIOExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(mapper).writeValue(out,properties);
        pr.saveFileAsYaml(out);
    }


    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenPropertiesSetPropertyIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        pr.editProperties(key,oldValue,newValue);
        verify(properties,never()).setProperty(key,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenPropertiesSetPropertyIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        pr.editProperties(key,oldValue,newValue);
        verify(properties,times(1)).setProperty(key,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenLogEditPropertyIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        pr.editProperties(key,oldValue,newValue);
        verify(log,never()).editProperty(key,oldValue,newValue);

    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenLogEditPropertyIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        pr.editProperties(key,oldValue,newValue);
        verify(log,times(1)).editProperty(key,oldValue,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenLoggerInfoIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        pr.editProperties(key,oldValue,newValue);
        verify(logger,never()).info("EDITED: "+key+"###"+oldValue+"="+newValue);

    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenLoggerInfoIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        pr.editProperties(key,oldValue,newValue);
        verify(logger,times(1)).info("EDITED: "+key+"###"+oldValue+"="+newValue);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenLogAddPropertyIsInvoked(){ //whenGivenKeyIsNew - props.get(key)=null

        String key = "Name";
        String value = "Richard";

        pr.addProperties(key,value);
        verify(log,times(1)).addProperty(key,value);

    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenLogAddPropertyIsNeverInvoked(){ //whenGivenKeyIsNotNew - props.get(key)!=null

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        pr.setProps(props);

        pr.addProperties(key,value);
        verify(log,never()).addProperty(key,value);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenLoggerInfoIsInvoked(){

        String key = "Name";
        String value = "Richard";

        pr.addProperties(key,value);
        verify(logger,times(1)).info("ADDED: "+key+"###"+value);

    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenLoggerInfoIsNeverInvoked(){

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        pr.setProps(props);

        pr.addProperties(key,value);
        verify(logger,never()).info("ADDED: "+key+"###"+value);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenPropsSetPropertyIsInvoked(){

        String key = "Name";
        String value = "Richard";

        pr.addProperties(key,value);
        verify(properties,times(1)).setProperty(key,value);

    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenPropsSetPropertyIsNeverInvoked(){

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        pr.setProps(props);

        pr.addProperties(key,value);
        verify(properties,never()).setProperty(key,value);
    }


    @Test
    public void removeProperties_propsRemoveInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        pr.removeProperties(key,value);

        verify(properties,times(1)).remove(key);
    }

    @Test
    public void removeProperties_logRemovePropertyInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        pr.removeProperties(key,value);

        verify(log,times(1)).removeProperty(key,value);
    }

    @Test
    public void removeProperties_loggerInfoInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        pr.removeProperties(key,value);

        verify(logger,times(1)).info("REMOVED: "+key+"###"+value);
    }

}
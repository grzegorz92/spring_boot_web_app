package com.testprojects.firstapp.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testprojects.firstapp.exception.BusinessException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.*;
import java.util.*;
import org.slf4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.junit.Assert.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesServiceImplTest {

    @Mock
    ChangesLog log;

    @Mock
    Properties properties;

    @Mock
    Logger logger;

    @Mock
    OutputStream outputStream;

    @Mock
    ObjectMapper mapper;

    MockMultipartFile file;

    PropertiesServiceImpl propertiesService;

    @Before
   public void setUp() {

        propertiesService = new PropertiesServiceImpl();
        propertiesService.setProperties(this.properties);
        propertiesService.setLog(this.log);
        propertiesService.setLogger(this.logger);
        propertiesService.setJsonMapper(this.mapper);
        propertiesService.setYamlMapper(this.mapper);
        file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "hello".getBytes());
    }


    @Test
    public void getFile_clearingPropertiesMethodInvocation_verifying() throws BusinessException {

        propertiesService.getFile(file);
        verify(properties,times(1)).clear();
    }

    @Test
    public void getFile_clearingChangesLogMethodInvocation_verifying() throws BusinessException {

        propertiesService.getFile(file);
        verify(log,times(1)).clearChangesLog();
    }

    @Test
    public void getFile_whenIOExceptionIsNotCaught_LoadingPropertiesMethodIsInvoke() throws Exception {

        propertiesService.getFile(file);
        verify(properties,times(1)).load(any(InputStream.class));
    }

    @Test(expected = BusinessException.class)
    public void getFile_whenIOExceptionIsCaught_BusinessExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(properties).load(any(InputStream.class));
        propertiesService.getFile(file);
    }

    @Test
    public void getFile_ChangesLogMethodInvocation_verifying() throws BusinessException {

        propertiesService.getFile(file);
        verify(log,times(1)).logFileLoading(file.getOriginalFilename());
    }

    @Test
    public void getFile_LoggerMethodInvocation_verifying() throws BusinessException {

        propertiesService.getFile(file);
        verify(logger,times(1)).info("FILE LOADED: " + file.getOriginalFilename());
    }

    @Test
    public void saveFileAsProperties_whenIOExceptionIsNotCaught_thenStorePropertiesMethodIsInvoked() throws Exception {

        propertiesService.saveFileAsProperties(outputStream);
        verify(properties,times(1)).store(outputStream,null);
    }

    @Test(expected = BusinessException.class)
    public void saveFileAsProperties_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(properties).store(outputStream,null);
        propertiesService.saveFileAsProperties(outputStream);
    }

    @Test
    public void saveFileAsJson_whenIOExceptionIsNotCaught_thenSavingFileAsJsonMethodIsInvoked() throws Exception {

        propertiesService.saveFileAsJson(outputStream);
        verify(mapper,times(1)).writeValue(outputStream, properties);
    }

    @Test(expected = BusinessException.class)
    public void saveFileAsJson_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(mapper).writeValue(outputStream, properties);
        propertiesService.saveFileAsJson(outputStream);
    }

    @Test
    public void saveFileAsYaml_whenIOExceptionIsNotCaught_thenSavingFileAsYamlMethodIsInvoked() throws Exception {

        propertiesService.saveFileAsYaml(outputStream);
        verify(mapper,times(1)).writeValue(outputStream, properties);
    }

    @Test(expected = BusinessException.class)
    public void saveFileAsYaml_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(IOException.class).when(mapper).writeValue(outputStream,properties);
        propertiesService.saveFileAsYaml(outputStream);
    }

    @Test
    public void loadProperties_test(){

        Map<String, String> temporaryProperties = new HashMap<>();
        temporaryProperties.put("Name", "John");
        temporaryProperties.put("Last_Name","Smith");



        // HOW TO add HashMap to Enumerate??
        //when(properties.propertyNames()).thenReturn(temporaryProperties.keySet());

        propertiesService.loadProperties();

        assertEquals(temporaryProperties, propertiesService.loadProperties());
    }


    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenSetPropertyMethodIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(properties,never()).setProperty(key,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenSetPropertyMethodIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(properties,times(1)).setProperty(key,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenEditPropertyLogMethodIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(log,never()).logPropertyEdition(key,oldValue,newValue);

    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenEditPropertyLogMethodIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(log,times(1)).logPropertyEdition(key,oldValue,newValue);
    }

    @Test
    public void editProperties_whenOldValueIsEqualsNewValue_thenLoggerInfoMethodIsNeverInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "Richard";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(logger,never()).info("EDITED: "+key+"###"+oldValue+"="+newValue);

    }

    @Test
    public void editProperties_whenOldValueIsNotEqualsNewValue_thenLoggerInfoMethodIsInvoked(){

        String key = "Name";
        String oldValue = "Richard";
        String newValue = "John";

        propertiesService.editProperties(key,oldValue,newValue);
        verify(logger,times(1)).info("EDITED: "+key+"###"+oldValue+"="+newValue);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenAddPropertyLogIsInvoked(){ //whenGivenKeyIsNew - props.get(key)=null

        String key = "Name";
        String value = "Richard";

        propertiesService.addProperties(key,value);
        verify(log,times(1)).logPropertyAddition(key,value);

    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenAddPropertyLogMethodIsNeverInvoked(){ //whenGivenKeyIsNotNew - props.get(key)!=null

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        propertiesService.setProperties(props);

        propertiesService.addProperties(key,value);
        verify(log,never()).logPropertyAddition(key,value);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenLoggerInfoMethodIsInvoked(){

        String key = "Name";
        String value = "Richard";

        propertiesService.addProperties(key,value);
        verify(logger,times(1)).info("ADDED: "+key+"###"+value);

    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenLoggerInfoMethodIsNeverInvoked(){

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        propertiesService.setProperties(props);

        propertiesService.addProperties(key,value);
        verify(logger,never()).info("ADDED: "+key+"###"+value);
    }

    @Test
    public void addProperties_whenGivenKeyIsNew_thenSetPropertyMethodIsInvoked(){

        String key = "Name";
        String value = "Richard";

        propertiesService.addProperties(key,value);
        verify(properties,times(1)).setProperty(key,value);
    }

    @Test
    public void addProperties_whenGivenKeyAlreadyExistsInProperties_thenSetPropertyMethodIsNeverInvoked(){

        String key = "Name";
        String value = "Richard";

        Properties props = new Properties();
        props.put(key,value);
        propertiesService.setProperties(props);

        propertiesService.addProperties(key,value);
        verify(properties,never()).setProperty(key,value);
    }


    @Test
    public void removeProperties_RemovePropertiesMethodInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        propertiesService.removeProperties(key,value);

        verify(properties,times(1)).remove(key);
    }

    @Test
    public void removeProperties_logRemovePropertyInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        propertiesService.removeProperties(key,value);

        verify(log,times(1)).logPropertyRemoving(key,value);
    }

    @Test
    public void removeProperties_loggerInfoMethodInvocation_verifying(){
        String key = "Name";
        String value = "Richard";

        propertiesService.removeProperties(key,value);

        verify(logger,times(1)).info("REMOVED: "+key+"###"+value);
    }

}
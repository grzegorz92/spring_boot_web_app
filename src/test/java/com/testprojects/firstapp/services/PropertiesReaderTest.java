package com.testprojects.firstapp.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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


    //???? nie dziala
    @Test(expected = IOException.class)
    public void saveFileAsJson_whenIOExIsCaught_thenIOExceptionIsThrown() throws Exception {

        //doThrow(IOException.class).when(mapper).writeValueAsString(properties);
        //when(mapper.writeValueAsString(properties)).thenThrow(IOException.class);
        pr.saveFileAsJson(out);
    }






}
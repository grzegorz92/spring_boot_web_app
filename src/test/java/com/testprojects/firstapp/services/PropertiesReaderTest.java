package com.testprojects.firstapp.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesReaderTest {

    @Mock
    ChangesLog log;


    @Mock
    Properties properties;

    @Mock
    InputStream in;

    PropertiesReader pr;



    @Before
   public void setUp() {
        pr = new PropertiesReader();
    }


    @Test
    public void getFile_ifInputStreamIsNull_thenReturnsNull() throws IOException {

        when(in).thenReturn(null);




    }

    @Test
    public void loadingProperties_test(){


        List<String> list = new ArrayList<>();

        //when(log.getChangesList()).thenReturn(list);



        //when(properties.propertyNames()).thenReturn("d");

    }


}
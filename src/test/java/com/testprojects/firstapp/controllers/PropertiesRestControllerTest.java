package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.services.PropertiesReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class PropertiesRestControllerTest {

    @Mock
    PropertiesReader pr;

    PropertiesController propertiesController;

    @Before
    public void setUp(){
        propertiesController = new PropertiesController(pr);
    }

    @Test
    public void test1(){

    }





}
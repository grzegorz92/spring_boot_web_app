package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.services.PropertiesReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesControllerTest {

    @Mock
    PropertiesReader pr;



    PropertiesController propertiesController;

    MockMvc mockMvc; //mocking MVC infrastructure

    @Before
    public void setUp(){

        propertiesController = new PropertiesController(pr);
        mockMvc = MockMvcBuilders.standaloneSetup(propertiesController).build(); //mocMvc configuration
    }

    /*  OR
        @InjectMocks
        PropertiesController propertiesController;

     */

    @Test
    public void loading_properties_test(){



       // when(pr.loadProperties()).
       // verify(pr).loadProperties();

    }

    @Test
    public void editProperties_test() throws Exception {

        String key = "Name";
        String newValue ="John";
        String oldValue = "Frank";

       doNothing().when(pr).editProperties(key,oldValue,newValue);

        //verify(pr).editProperties(any(String.class),any(String.class),any(String.class));
       verify(pr, atLeastOnce());



        //testing MVC infrastructure
        mockMvc.perform(
                get("/edit")                         //url Template
               // .sessionAttr("NAME", OBJECT)                  // use it for addAtribute
                        .param("key", key)               //parameter from @RequestParam
                        .param("newValue", newValue)
                        .param("oldValue", oldValue))
              .andDo(print())                                  //print the request/response in the console
               .andExpect(status().isFound())                     //http status isFound (302) - redirect http code
               .andExpect(content().contentType(MediaType.TEXT_HTML)) // returned content type
               .andExpect(content().string("redirect:/properties")); //what is returned

        //or
        /*
        mockMvc.perform(get("/edit") //urlTemplate
                .contentType(MediaType.TEXT_PLAIN))//content type of the returned view/doc etc
                //.(("redirect:/properties"))) ??
                .andExpect(status().isOk()) //http status
         */



    }





}
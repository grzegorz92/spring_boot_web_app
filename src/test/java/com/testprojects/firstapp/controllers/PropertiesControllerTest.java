package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.services.PropertiesReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesControllerTest {

    @Mock
    PropertiesReader pr;

    MockMvc mockMvc;
    PropertiesController propertiesController;

    @Before
    public void setUp(){

        propertiesController = new PropertiesController(pr);
        mockMvc = MockMvcBuilders.standaloneSetup(propertiesController).build(); //mocMvc configuration
    }

    @Test
    public void editProperties_test() throws Exception {

        String key = "Name";
        String newValue ="John";
        String oldValue = "Frank";

        mockMvc.perform(
                get("/edit")
                        .param("key", key)
                        .param("newValue", newValue)
                        .param("oldValue", oldValue))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/properties"));

        verify(pr, atLeastOnce()).editProperties(key, oldValue, newValue);
    }

    @Test
    public void addProperties_test() throws Exception {

        String key = "Last_Name";
        String value = "Kowalsky";


        mockMvc.perform(
                get("/add")
                        .param("key", key)
                        .param("value", value))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/properties"));

        verify(pr, atLeast(1)).addProperties(key,value);

    }

    @Test
    public void deleteProperties_test() throws Exception {

        String key = "Salary";
        String value = "7500";

        mockMvc.perform(
                get("/delete")
                    .param("key",key)
                    .param("value",value))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/properties"));

        verify(pr, times(1)).removeProperties(key,value);
    }


    @Test
    public void getFile_setIn_method_test() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "hello".getBytes());

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploading").file(file))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/properties"));

        verify(pr, atLeast(1)).setIn(any(ByteArrayInputStream.class));
    }

    @Test
    public void getFile_getOriginalName_test() throws Exception {

        MockMultipartFile file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "some_file".getBytes()); //name has to be the same as parameter name in controller!

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploading").file(file))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/properties"));

        verify(pr,atLeastOnce()).getFile(file.getOriginalFilename());
    }

    @Test
    public void saveFileAsProperties_test() throws Exception {

        mockMvc.perform(get("/save_properties"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(pr,times(1)).saveFileAsProperties(isNotNull());
    }

    @Test
    public void saveFileAsJson_test() throws Exception {

        mockMvc.perform(get("/save_json"))
                .andDo(print())
                .andExpect(status().isOk());

       verify(pr,times(1)).saveFileAsJson(isNotNull());
    }

    @Test
    public void saveFileAsYaml_test() throws Exception {

        mockMvc.perform(get("/save_yaml"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(pr,times(1)).saveFileAsYaml(isNotNull());
    }

    @Test
    public void downloadLog_test() throws Exception {

       mockMvc.perform(get("/download_log"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(pr, times(1)).downloadLog(isNotNull());
    }


    @Test
    public void readProperties() throws Exception {

        Map<String, String> properties = new HashMap<>();
        properties.put("Name","John");
        properties.put("Last_Name", "Smith");

        List<String> log = new ArrayList<>();
        log.add("Log1");
        log.add("Log2");

        when(pr.loadProperties()).thenReturn(properties);
        when(pr.getLog()).thenReturn(log);

        mockMvc.perform(get("/properties/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("props",properties))
                .andExpect(model().attribute("changesLog",log))
                .andExpect(view().name("properties"))
                .andExpect(forwardedUrl("properties"));

        verify(pr,atLeastOnce()).loadProperties();


    }

}
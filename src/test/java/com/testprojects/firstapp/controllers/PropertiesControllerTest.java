package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.services.PropertiesServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PropertiesControllerTest {

    @Mock
    PropertiesServiceImpl propertiesService;

    MockMvc mockMvc;

    MockMultipartFile file;

    PropertiesController propertiesController;

    @Before
    public void setUp(){

        propertiesController = new PropertiesController(propertiesService);
        mockMvc = MockMvcBuilders.standaloneSetup(propertiesController).setControllerAdvice(new ControllerExceptionHandler()).build(); //mocMvc configuration
        file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "hello".getBytes());
    }

    @Test
    public void testGetFileMethod_whenIOExceptionIsNotCaught() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploading").file(file))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/properties"));

        verify(propertiesService, times(1)).getFile(file);

        assertEquals(file.getOriginalFilename(),propertiesController.getLoadedFileName());
    }

    @Test
    public void testGetFileMethod_whenIOExceptionIsCaught() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).getFile(file);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploading").file(file))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("ER"));
    }

    @Test
    public void readPropertiesMethod_test() throws Exception {

        Map<String, String> properties = new HashMap<>();
        properties.put("Name", "John");
        properties.put("Last_Name", "Smith");

        List<String> log = new ArrayList<>();
        log.add("Log1");
        log.add("Log2");

        when(propertiesService.loadProperties()).thenReturn(properties);
        when(propertiesService.getLog()).thenReturn(log);

        mockMvc.perform(get("/properties/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("props", properties))
                .andExpect(model().attribute("changesLog", log))
                .andExpect(view().name("properties"))
                .andExpect(forwardedUrl("properties"));

        verify(propertiesService, times(1)).loadProperties();
        verify(propertiesService, times(1)).getLog();
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

        verify(propertiesService, times(1)).removeProperties(key,value);
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

        verify(propertiesService, atLeastOnce()).editProperties(key, oldValue, newValue);
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

        verify(propertiesService, atLeast(1)).addProperties(key,value);
    }

    @Test
    public void saveFileAsProperties_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoPropertiesFile() throws Exception {

        mockMvc.perform(get("/save_properties"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsProperties(any(OutputStream.class));
    }

    @Test
    public void saveFileAsProperties_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsProperties(any(OutputStream.class));

        mockMvc.perform(get("/save_properties"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("ER"));
    }

    @Test
    public void saveFileAsJson_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoJsonFile() throws Exception {

        mockMvc.perform(get("/save_json"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsJson(any(OutputStream.class));
    }

    @Test
    public void saveFileAsJson_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsJson(any(OutputStream.class));

        mockMvc.perform(get("/save_json"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("ER"));
    }



    @Test
    public void saveFileAsYaml_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoYamlFile() throws Exception {

        mockMvc.perform(get("/save_yaml"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsYaml(any(OutputStream.class));
    }

    @Test
    public void saveFileAsYaml_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsYaml(any(OutputStream.class));

        mockMvc.perform(get("/save_yaml"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("ER"));
    }

    @Test
    public void downloadLog_whenIOExceptionIsNotCaught_thenLogIsBeingSavedIntoFile() throws Exception {

        mockMvc.perform(get("/download_log"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService, times(1)).downloadLog(any(OutputStream.class));
    }

    @Test
    public void downloadLog_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).downloadLog(any(OutputStream.class));

        mockMvc.perform(get("/download_log"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(view().name("ER"));
    }
}
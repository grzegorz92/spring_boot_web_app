package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.service.PropertiesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(MockitoJUnitRunner.class)
public class PropertiesRestControllerTest {

    @Mock
    PropertiesService propertiesService;

    MockMvc mockMvc;

    MockMultipartFile file;

    PropertiesRestController propertiesRestController;

    @Before
    public void setUp() {

        propertiesRestController = new PropertiesRestController(propertiesService);
        mockMvc = MockMvcBuilders.standaloneSetup(propertiesRestController).setControllerAdvice(new ControllerExceptionHandler()).build(); //mocMvc configuration
        file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "hello".getBytes());
    }


    @Test
    public void GetFileMethodTest_whenIOExceptionIsNotCaught() throws Exception {

        String loadedFileName = file.getOriginalFilename();

        mockMvc.perform(MockMvcRequestBuilders
                .multipart(PropertiesRestController.BASE_URL+"/upload").file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("File: "+ loadedFileName +" uploaded successfully"));

        verify(propertiesService, times(1)).getFile(file);

        assertEquals(file.getOriginalFilename(),propertiesRestController.getLoadedFileName());
    }



    @Test
    public void GetFileMethodTest_whenIOExceptionIsCaught() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).getFile(file);

        mockMvc.perform(MockMvcRequestBuilders
                .multipart(PropertiesRestController.BASE_URL+"/upload").file(file)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
                //.andExpect error message
    }

    @Test
    public void getPropertiesTest() throws Exception {

        Map<String, String> properties = new HashMap<>();
        properties.put("Name", "John");
        properties.put("Last_Name","Smith");

        when(propertiesService.getProperties()).thenReturn(properties);

        mockMvc.perform(get(PropertiesRestController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name", equalTo("John")))
                .andExpect(jsonPath("$.Last_Name", equalTo("Smith")));
    }

    @Test
    public void addPropertiesTest() throws Exception {

        String key = "City";
        String value = "London";

        Map<String, String> properties = new HashMap<>();
        properties.put("Name", "John");
        properties.put("Last_Name","Smith");
        properties.put(key,value);

        when(propertiesService.getProperties()).thenReturn(properties);

        mockMvc.perform(post(PropertiesRestController.BASE_URL)
                .param("key",key)
                .param("value",value)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Name", equalTo("John")))
                .andExpect(jsonPath("$.Last_Name", equalTo("Smith")))
                .andExpect(jsonPath("$."+key, equalTo(value)));

        verify(propertiesService,times(1)).addProperties(key, value);
    }

    @Test
    public void editPropertiesTest() throws Exception {

        String key = "Name";
        String oldValue = "John";
        String newValue = "Richard";

        Map<String, String> properties = new HashMap<>();
        properties.put(key, newValue);
        properties.put("Last_Name","Smith");

        when(propertiesService.getProperties()).thenReturn(properties);

        mockMvc.perform(put(PropertiesRestController.BASE_URL)
                .param("key",key)
                .param("oldValue",oldValue)
                .param("newValue",newValue)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$."+key, equalTo(newValue)))
                .andExpect(jsonPath("$.Last_Name", equalTo("Smith")));

        verify(propertiesService,times(1)).editProperties(key, oldValue, newValue);
    }

    @Test
    public void deletePropertiesTest() throws Exception {

        String key = "Name";
        String value = "John";

        Map<String, String> properties = new HashMap<>();
        properties.put("Last_Name","Smith");

        when(propertiesService.getProperties()).thenReturn(properties);

        mockMvc.perform(delete(PropertiesRestController.BASE_URL)
                .param("key",key)
                .param("value",value)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.Last_Name", equalTo("Smith")));

        verify(propertiesService, times(1)).removeProperties(key,value);
    }

    /////
    @Test
    public void saveFileAsProperties_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoPropertiesFile() throws Exception {

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_properties"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsProperties(any(OutputStream.class));
    }

    @Test
    public void saveFileAsProperties_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsProperties(any(OutputStream.class));

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_properties"))
                .andDo(print())
                .andExpect(status().isNotFound());
                //.andExpect error message
    }


    @Test
    public void saveFileAsJson_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoJsonFile() throws Exception {

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_json"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsJson(any(OutputStream.class));
    }

    @Test
    public void saveFileAsJson_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsJson(any(OutputStream.class));

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_json"))
                .andDo(print())
                .andExpect(status().isNotFound());
                //.andExpect error message
    }

    @Test
    public void saveFileAsYaml_whenIOExceptionIsNotCaught_thenPropertiesAreBeingSavedIntoYamlFile() throws Exception {

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_yaml"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService,times(1)).saveFileAsYaml(any(OutputStream.class));
    }

    @Test
    public void saveFileAsYaml_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).saveFileAsYaml(any(OutputStream.class));

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/save_yaml"))
                .andDo(print())
                .andExpect(status().isNotFound());
               // .andExpect error message
    }

    @Test
    public void downloadLog_whenIOExceptionIsNotCaught_thenLogIsBeingSavedIntoFile() throws Exception {

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/download_log"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(propertiesService, times(1)).downloadLog(any(OutputStream.class));
    }

    @Test
    public void downloadLog_whenIOExceptionIsCaught_thenBusinessExceptionIsThrown() throws Exception {

        doThrow(BusinessException.class).when(propertiesService).downloadLog(any(OutputStream.class));

        mockMvc.perform(get(PropertiesRestController.BASE_URL+"/download_log"))
                .andDo(print())
                .andExpect(status().isNotFound());
               // .andExpect error message


    }
}
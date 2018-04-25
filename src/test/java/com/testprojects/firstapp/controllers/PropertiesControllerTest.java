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

    MockMvc mockMvc; //mocking MVC infrastructure



    PropertiesController propertiesController;

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
    public void editProperties_test() throws Exception {

        String key = "Name";
        String newValue ="John";
        String oldValue = "Frank";


        //testing MVC infrastructure
        mockMvc.perform(
                get("/edit")                         //url Template
               // .sessionAttr("NAME", OBJECT)                  // use it for addAttribute
                        .param("key", key)               //parameter from @RequestParam
                        .param("newValue", newValue)
                        .param("oldValue", oldValue))
                .andDo(print())                                 //print the request/response in the console
                .andExpect(status().isFound())                  //http status isFound (302) - redirect http code
                .andExpect(redirectedUrl("/properties")); //expected redirected url that is gonna be returned
               // .andExpect(view().name("redirect:/properties")); //expect returned view which name is.. - works the same as above

                //when you expect some content to be returned
               // .andExpect(content().contentType(MediaType.TEXT_PLAIN)) // returned content type
              // .andExpect(content().string("redirect:/properties")); //what is returned

        //or
        /*
        mockMvc.perform(get("/edit") //urlTemplate
                .contentType(MediaType.TEXT_PLAIN))//content type of the returned view/doc etc
                //.(("redirect:/properties"))) ??
                .andExpect(status().isOk()) //http status
         */


        //verify(pr,times(1)).editProperties(key,oldValue,newValue);        //verify if pr.editProperties is called at least 1 time
        verify(pr, atLeastOnce()).editProperties(key, oldValue, newValue);  //-||-

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


        MockMultipartFile file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "hello".getBytes()); //name has to be the same as parameter name in controller!

        mockMvc.perform(MockMvcRequestBuilders
                .multipart("/uploading").file(file))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/properties"));


        verify(pr, atLeast(1)).setIn(any(ByteArrayInputStream.class)); //any() ? file.getInputStream() ??? isNotNull()



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

//    @Test(expected = IOException.class)
//    public void getFile_whenMultiPartFileCantBeRead_thenIOExIsThrown() throws Exception{
//
//
//        MockMultipartFile file = new MockMultipartFile("file", "originalFileName","multipart/form-data", "some_file".getBytes()); //name has to be the same as parameter name in controller!
//
//
//        when(file.getInputStream()).thenThrow(IOException.class);
//
//        mockMvc.perform(MockMvcRequestBuilders
//                .multipart("/uploading").file(file))
//                .andDo(print())
//                .andExpect(status().isFound())
//                .andExpect(view().name("redirect:/properties"));
//
//       verify(pr, atLeastOnce()).setIn(file.getInputStream());
//    }

    @Test
    public void saveFileAsProperties_test() throws Exception {

       //MockHttpServletResponse response = new MockHttpServletResponse();

        mockMvc.perform(get("/save_properties"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(pr,times(1)).saveFileAsProperties(isNotNull());
    }

    @Test(expected = IOException.class)
    public void saveFileAsProperties_whenCantSaveFile_thenIOExIsThrown() throws Exception{

        //doThrow(IOException.class).when(pr.saveFileAsProperties());

//        mockMvc.perform(get("/save_properties"))
//                .andDo(print())
//                .andExpect(status().isOk());





    }

    @Test
    public void saveFileAsJson_test() throws Exception {

       // MockHttpServletResponse response = new MockHttpServletResponse();


        mockMvc.perform(get("/save_json"))
                .andDo(print())
                .andExpect(status().isOk());



       verify(pr,times(1)).saveFileAsJson(isNotNull());
    }

    @Test
    public void saveFileAsYaml_test() throws Exception {

       // MockHttpServletResponse response = new MockHttpServletResponse();

        mockMvc.perform(get("/save_yaml"))
                .andDo(print())
                .andExpect(status().isOk());

        verify(pr,times(1)).saveFileAsYaml(isNotNull());
    }

    @Test
    public void downloadLog_test() throws Exception {

        // MockHttpServletResponse response = new MockHttpServletResponse();

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

        // w testach nie da sie nic dodawac do zmokowanych obiektow. jesli chcesz zeby metoda, ktora zwraca np mape zwrocila mape, to nie dajesz mock.put,
        // tylko when(mock.zwroc_mape).thenReturn(mapa_ktora_chcesz_zeby_zmockowany_obiekt_zwrocil)
        System.out.println("\n\nBEFORE:\n"+pr.loadProperties()); //zwroci null nawet jak dasz pr.add(..)

        when(pr.loadProperties()).thenReturn(properties); //ustalone zostaje, ze zmokowany obiekt pr przy wywolaniu metody loadProperties zwroci mape properties

        System.out.println("\nAFTER:\n"+pr.loadProperties());//zwroci to co zostalo ustawione w thenReturn(..)


        when(pr.getLog()).thenReturn(log);

        mockMvc.perform(get("/properties/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(model().attribute("props",properties))//
                //.andExpect(model().attributeExists("changesLog")) //does attribute exist?
                .andExpect(model().attribute("changesLog",log))
                .andExpect(view().name("properties"))
                .andExpect(forwardedUrl("properties"));

        verify(pr,atLeastOnce()).loadProperties();


    }

}
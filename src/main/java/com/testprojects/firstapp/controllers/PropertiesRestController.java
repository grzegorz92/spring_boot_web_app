package com.testprojects.firstapp.controllers;


import com.testprojects.firstapp.model.Props;
import com.testprojects.firstapp.services.PropertiesReader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/rest/properties")
public class PropertiesRestController {

    PropertiesReader pr;
    //Props props;
    String loadedFileName = null;

    public PropertiesRestController(PropertiesReader pr){//, Props props) {
        this.pr = pr;
        //this.props = props;
    }

    //GET: GET FILE (and load file?)

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file){

        try {
            pr.setIn(file.getInputStream());
            pr.getFile(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }

        loadedFileName = file.getOriginalFilename();



        return "File: "+ loadedFileName +" uploaded successfully";
    }

    @GetMapping
    public Map<String, String> getProperties(){

        return pr.loadProperties();
    }

    @PostMapping
    public Map<String, String> addProperties(@RequestParam String key, @RequestParam String value){

        pr.addProperties(key, value);

        return pr.loadProperties();
    }

    @PutMapping
    public Map<String, String> editProperties(@RequestParam String key, @RequestParam String oldValue, @RequestParam String newValue){

        pr.editProperties(key, oldValue, newValue);

        return pr.loadProperties();
    }

    @DeleteMapping
    public Map<String, String> deleteProperties(@RequestParam String key, @RequestParam String value){

        pr.removeProperties(key, value);

        return pr.loadProperties();
    }





    //POST: ADD NEW PROPERTY

    //PUT: EDIT PROPERTY

    //DELETE: REMOVE PROPERTY

    //GET: SAVE IN .property, .json, .yaml

    //GET: SAVE CHANGES LOG



}

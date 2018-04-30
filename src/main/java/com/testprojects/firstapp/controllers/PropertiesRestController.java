package com.testprojects.firstapp.controllers;


import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.services.PropertiesServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/rest/properties")
public class PropertiesRestController {

    PropertiesServiceImpl pr;
    //Props props;
    String loadedFileName = null;

    public PropertiesRestController(PropertiesServiceImpl pr){//, Props props) {
        this.pr = pr;
        //this.props = props;
    }

    //GET: GET FILE (and load file?)

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws BusinessException {

//        //
//        pr.setMultipartFile(file);
//        //
//        try {
//            //pr.setIn(file);
//            pr.getFile(file.getOriginalFilename());
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        pr.getFile(file);
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

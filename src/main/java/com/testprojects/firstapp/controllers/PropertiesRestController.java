package com.testprojects.firstapp.controllers;


import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.service.PropertiesServiceImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/rest/properties")
public class PropertiesRestController {

    private PropertiesServiceImpl propertiesService;
    private String loadedFileName = "unknown.properties";
    public static final String BASE_URL = "/rest/properties";


    public PropertiesRestController(PropertiesServiceImpl propertiesService) {//, Props props) {
        this.propertiesService = propertiesService;
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws BusinessException {

        propertiesService.getFile(file);
        loadedFileName = file.getOriginalFilename();

        return "File: " + loadedFileName + " uploaded successfully";
    }

    @GetMapping
    public Map<String, String> getProperties() {

        return propertiesService.getProperties();
    }

    @PostMapping
    public Map<String, String> addProperties(@RequestParam String key, @RequestParam String value) throws BusinessException {

        propertiesService.addProperties(key, value);

        return propertiesService.getProperties();
    }

    @PutMapping
    public Map<String, String> editProperties(@RequestParam String key, @RequestParam String oldValue, @RequestParam String newValue) {

        propertiesService.editProperties(key, oldValue, newValue);

        return propertiesService.getProperties();
    }

    @DeleteMapping
    public Map<String, String> deleteProperties(@RequestParam String key, @RequestParam String value) {

        propertiesService.removeProperties(key, value);

        return propertiesService.getProperties();
    }

    @GetMapping("/save_properties")
    public void saveFileAsProperties(HttpServletResponse response) throws Exception {

        response.setHeader("Content-disposition", "attachment; filename=" + loadedFileName);

        propertiesService.saveFileAsProperties(response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping("/save_yaml")
    public void saveFileAsYaml(HttpServletResponse response) throws Exception {

        response.setHeader("Content-disposition", "attachment; filename=new_file.yaml");
        propertiesService.saveFileAsYaml(response.getOutputStream());
    }

    @GetMapping("/save_json")
    public void saveFileAsJson(HttpServletResponse response) throws Exception {


        response.setHeader("Content-disposition", "attachment; filename=new_file.json");
        propertiesService.saveFileAsJson(response.getOutputStream());
    }

    @GetMapping("/download_log")
    public void downloadLog(HttpServletResponse response) throws Exception {

        response.setHeader("Content-disposition", "attachment; filename=audit_log.log");
        propertiesService.downloadLog(response.getOutputStream());
    }

    public String getLoadedFileName() {
        return loadedFileName;
    }
}

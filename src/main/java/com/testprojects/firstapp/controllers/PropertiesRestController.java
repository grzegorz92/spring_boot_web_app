package com.testprojects.firstapp.controllers;


import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.utils.JsonFormatter;
import com.testprojects.firstapp.utils.PropertiesService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/rest/properties")
public class PropertiesRestController {

    private PropertiesService propertiesService;
    private JsonFormatter jsonFormatter = new JsonFormatter();
    private String loadedFileName = "unknown";
    private String loadedFileNameNoExt = "unknown";
    public static final String BASE_URL = "/rest/properties";


    public PropertiesRestController(PropertiesService propertiesService) {
        this.propertiesService = propertiesService;
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestBody MultipartFile file) throws BusinessException {

        propertiesService.getFile(file);
        loadedFileName = file.getOriginalFilename();
        loadedFileNameNoExt = FilenameUtils.getBaseName(loadedFileName);


        return "File: '" + loadedFileName + "' uploaded successfully!";
    }


    @GetMapping
   // public Map<String, String> getProperties() {
    public List<Map<String,String>> getProperties(){

        Map<String,String> propertiesMap = propertiesService.getProperties();

        //return propertiesService.getProperties();
        return jsonFormatter.formatPropertiesToJson(propertiesMap);
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

        response.setHeader("Content-disposition", "attachment; filename=" + loadedFileNameNoExt+".properties");

        propertiesService.saveFileAsProperties(response.getOutputStream());
        response.flushBuffer();
    }

    @GetMapping("/save_yaml")
    public void saveFileAsYaml(HttpServletResponse response) throws Exception {

        response.setHeader("Content-disposition", "attachment; filename="+loadedFileNameNoExt+".yaml");
        propertiesService.saveFileAsYaml(response.getOutputStream());
    }

    @GetMapping("/save_json")
    public void saveFileAsJson(HttpServletResponse response) throws Exception {

        response.setHeader("Content-disposition", "attachment; filename="+loadedFileNameNoExt+".json");
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


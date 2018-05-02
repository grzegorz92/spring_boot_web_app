package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.service.PropertiesServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;


@Controller
public class PropertiesController {

    private PropertiesServiceImpl propertiesService;
    private String loadedFileName="unknown_file.properties";
    private Logger logger =  LoggerFactory.getLogger(getClass().getName());


    public PropertiesController(PropertiesServiceImpl propertiesService) {
        this.propertiesService = propertiesService;
    }

    @RequestMapping("/uploading")
    public String getFile(@RequestParam("file") MultipartFile file) throws BusinessException {

        propertiesService.getFile(file);
        loadedFileName = file.getOriginalFilename();

        return "redirect:/properties";
    }

    @RequestMapping("/properties")
    public String readProperties(Model model){

       model.addAttribute("properties", propertiesService.loadProperties());
       model.addAttribute("changesLog", propertiesService.getLog());

        return "properties";
    }

    @RequestMapping("/delete")
    public String deleteProperties(@RequestParam String key, @RequestParam String value){

        propertiesService.removeProperties(key, value);
        return "redirect:/properties";
    }

    @RequestMapping("/edit")
    public String editProperties(@RequestParam("key") String key, @RequestParam("oldValue") String oldValue, @RequestParam("newValue") String newValue){

        propertiesService.editProperties(key, oldValue, newValue);
        return "redirect:/properties";
    }

    @RequestMapping("/add")
    public String addProperties(@RequestParam("key") String key, @RequestParam("value") String value){

        propertiesService.addProperties(key, value);
        return "redirect:/properties";
    }

    @RequestMapping("/save_properties")
    public void saveFileAsProperties(HttpServletResponse response) throws Exception {

            response.setHeader("Content-disposition", "attachment; filename="+loadedFileName); // instead of this, in front: <a href="/save_properties" download="filename.properties">

            propertiesService.saveFileAsProperties(response.getOutputStream());
            response.flushBuffer();
    }

    @RequestMapping("/save_yaml")
    public void saveFileAsYaml(HttpServletResponse response) throws Exception {

            response.setHeader("Content-disposition", "attachment; filename=new_file.yaml");
            propertiesService.saveFileAsYaml(response.getOutputStream());
    }

    @RequestMapping("/save_json")
    public void saveFileAsJson(HttpServletResponse response) throws Exception {


            response.setHeader("Content-disposition", "attachment; filename=new_file.json");
            propertiesService.saveFileAsJson(response.getOutputStream());
    }

    @RequestMapping("/download_log")
    public void downloadLog(HttpServletResponse response) throws Exception{

            response.setHeader("Content-disposition", "attachment; filename=audit_log.log");
            propertiesService.downloadLog(response.getOutputStream());
    }

    public String getLoadedFileName() {
        return loadedFileName;
    }

}


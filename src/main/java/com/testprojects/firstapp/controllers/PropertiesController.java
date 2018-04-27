package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.exception.BusinessException;
import com.testprojects.firstapp.services.PropertiesReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
public class PropertiesController {

    private PropertiesReader pr;
    private String loadedFileName="unknown_file.properties";
    private Logger logger =  LoggerFactory.getLogger(getClass().getName());


    public PropertiesController(PropertiesReader pr) {
        this.pr = pr;
    }

    @RequestMapping("/uploading")
    public String getFile(@RequestParam("file") MultipartFile file) throws BusinessException {

        pr.getFile(file);
        loadedFileName = file.getOriginalFilename();

        return "redirect:/properties";
    }

    @RequestMapping("/properties")
    public String readProperties(Model model){

       model.addAttribute("props", pr.loadProperties());
       model.addAttribute("changesLog", pr.getLog());

       // System.out.println(pr.loadProperties());

        return "properties";
    }

    @RequestMapping("/delete")
    public String deleteProperties(@RequestParam String key, @RequestParam String value){

        pr.removeProperties(key, value);
        return "redirect:/properties";
    }


    @RequestMapping("/edit")
    public String editProperties(@RequestParam("key") String key, @RequestParam("oldValue") String ov, @RequestParam("newValue") String nv){

        pr.editProperties(key, ov, nv);
        return "redirect:/properties";
    }

    @RequestMapping("/add")
    public String addProperties(@RequestParam("key") String key, @RequestParam("value") String value){

        pr.addProperties(key, value);
        return "redirect:/properties";
    }

    @RequestMapping("/save_properties")
    public void saveFileAsProperties(HttpServletResponse response){

            response.setHeader("Content-disposition", "attachment; filename="+loadedFileName); // instead of this, in front: <a href="/save_properties" download="filename.properties">

        try {
            pr.saveFileAsProperties(response.getOutputStream());
            response.flushBuffer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/save_yaml")
    public void saveFileAsYaml(HttpServletResponse response) throws Exception {

        try {
            response.setHeader("Content-disposition", "attachment; filename=new_file.yaml");
            pr.saveFileAsYaml(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/save_json")
    public void saveFileAsJson(HttpServletResponse response) throws Exception {

        try {
            response.setHeader("Content-disposition", "attachment; filename=new_file.json");
            pr.saveFileAsJson(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/download_log")
    public void downloadLog(HttpServletResponse response) throws Exception {

        try {
            response.setHeader("Content-disposition", "attachment; filename=audit_log.log");
            pr.downloadLog(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(IOException.class)
    public ModelAndView iOExHandler(){

        ModelAndView mav = new ModelAndView();
        mav.setViewName("ER");
        return mav;
    }

}


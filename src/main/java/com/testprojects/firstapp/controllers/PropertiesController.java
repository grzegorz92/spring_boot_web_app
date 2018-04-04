package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.config.PropertiesReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;


@Controller
public class PropertiesController {

    private PropertiesReader pr; //reading properties file


    public PropertiesController(PropertiesReader pr) {
        this.pr = pr;
    }


    @RequestMapping("/uploading")
    public String getFile(@RequestParam("file") MultipartFile file){

        try {
            pr.setIn(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "redirect:/properties";
    }

    @RequestMapping("/properties")
    public String readProperties(Model model){

       model.addAttribute("props", pr.loadProperties());

        return "properties";
    }

    @RequestMapping("/delete")
    public String deleteProperties(@RequestParam String key){

        pr.removeProperties(key);

        return "redirect:/properties";
    }

//    public String editProperties(){
//
//        pr.editProperties(k, v);
//        return "redirect:/properties";
//    }


}


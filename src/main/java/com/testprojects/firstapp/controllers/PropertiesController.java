package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.config.ChangesLog;
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
            pr.getFile(file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/properties";
    }

    @RequestMapping("/properties")
    public String readProperties(Model model){

       model.addAttribute("props", pr.loadProperties());
       model.addAttribute("changesLog", pr.getLog());
        return "properties";
    }

    @RequestMapping("/delete")
    public String deleteProperties(@RequestParam String key, @RequestParam String value){

        pr.removeProperties(key, value);
        return "redirect:/properties";
    }


    @RequestMapping("/edit")
    public String editProperties(@RequestParam("key") String k, @RequestParam("oldValue") String ov, @RequestParam("newValue") String nv){

        pr.editProperties(k, ov, nv);
        return "redirect:/properties";
    }

    @RequestMapping("/add")
    public String addProperties(@RequestParam("key") String k, @RequestParam("value") String v){

        pr.addProperties(k, v);
        return "redirect:/properties";
    }

    @RequestMapping("/save_file")
    public String saveFile(Model model){
        model.addAttribute("props", pr.getProps());
        System.out.println(pr.getProps());

        return "redirect:/properties";
    }




}

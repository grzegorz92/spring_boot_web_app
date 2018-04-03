package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.config.PropertiesReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class PropertiesController {

    private PropertiesReader pr; //reading properties file


    public PropertiesController(PropertiesReader pr) {
        this.pr = pr;
    }

    @RequestMapping("/properties")
    public String getProperties(Model model){

        model.addAttribute("props", pr.loadProperties());

        return "properties";
    }


    @RequestMapping("/testy")
    public String testing(Model model){

        model.addAttribute("props",pr.loadProperties());


        return "testing";
    }
}


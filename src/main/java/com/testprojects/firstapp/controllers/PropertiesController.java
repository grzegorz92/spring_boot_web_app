package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.config.PropertiesReader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;


@Controller
public class PropertiesController {

    private PropertiesReader pr; //reading properties file


    public PropertiesController(PropertiesReader pr) {
        this.pr = pr;
    }

    @RequestMapping("/upload")
    public String getFile(){

        return "upload-file";
    }

    @RequestMapping("/uploading")
    public String getFile(@RequestParam("file") MultipartFile file){ //RedirectAttributes redirectAttributes


//        if (file.isEmpty()) {
//            redirectAttributes.addFlashAttribute("message", "Upload file!");
//            return "redirect:/properties";
//        }

        try {
            pr.setIn(file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }


        return "redirect:/properties";
    }

    @RequestMapping("/properties")
    public String getProperties(Model model){

       model.addAttribute("props", pr.loadProperties());

        return "properties";
    }

}


package com.testprojects.firstapp.controllers.v1;

import com.testprojects.firstapp.repositories.v1.TestEmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class TestEmployeeController {


    private TestEmployeeRepository ter;

    public TestEmployeeController(TestEmployeeRepository ter) {
        this.ter = ter;
    }

    @RequestMapping("/test")
    public String getEmployees(Model model){

        //System.out.println("Employee " + this.propConf != null ? true : false);

        model.addAttribute("employee",ter.findAll());

        //System.out.println("\nHERE"+propConf.getName());



        return "test-page";
    }
}

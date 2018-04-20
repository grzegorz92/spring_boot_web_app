package com.testprojects.firstapp.controllers.v1;


import com.testprojects.firstapp.repositories.v1.EmployeeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class EmployeeController {

    private EmployeeRepository employeeRepository;

    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @RequestMapping("/employees")
    public String getEmployees(Model model){

        model.addAttribute("employees", employeeRepository.findAll());
        return "employees";
    }

}

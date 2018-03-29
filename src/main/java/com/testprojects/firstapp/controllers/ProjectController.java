package com.testprojects.firstapp.controllers;

import com.testprojects.firstapp.repositories.ProjectRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProjectController {

    private ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @RequestMapping("/projects")
    public String getProjects(Model model){

        model.addAttribute("projects", projectRepository.findAll());

        return "projects";
    }
}

package com.testprojects.firstapp.bootstrap;

import com.testprojects.firstapp.model.v1.Employee;
import com.testprojects.firstapp.model.v1.Project;
import com.testprojects.firstapp.model.v1.ProjectManager;
import com.testprojects.firstapp.repositories.v1.EmployeeRepository;
import com.testprojects.firstapp.repositories.v1.ProjectManagerRepository;
import com.testprojects.firstapp.repositories.v1.ProjectRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component //wiring with spring context
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;
    private ProjectManagerRepository projectManagerRepository;


    //constructor for DI (no @Autowired is needed above Sring 4.3)
   public DevBootstrap(EmployeeRepository employeeRepository, ProjectRepository projectRepository, ProjectManagerRepository projectManagerRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
        this.projectManagerRepository = projectManagerRepository;
    }

    /* Can be also like that - no repositores in repository package are needed

    private CrudRepository<Employee, Integer> employerRepository;
    private CrudRepository<Project, Integer> projectRepository;

    public DevBootstrap(CrudRepository<Employee, Integer> employerRepository, CrudRepository<Project, Integer> projectRepository) {
        this.employerRepository = employerRepository;
        this.projectRepository = projectRepository;
    }

     */


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initData();

    }


    private void initData(){


        //PMs creating
        ProjectManager pm1 = new ProjectManager("Janusz", "Kowalski", "19.02.2015");
        projectManagerRepository.save(pm1);

        ProjectManager pm2 = new ProjectManager("Grazyna", "Nowak", "05.08.2016");
        projectManagerRepository.save(pm2);

        //employee 1, project 1

        //create first employee
        Employee emp1One = new Employee("John", "Smiths", "20.02.2012", "2000");

        //create first project
        Project project1 = new Project("Project One", pm1, "20.12.2018");

        //add project1 object to employee 1 object
        emp1One.getProjects().add(project1);

        //saving data to DB
        employeeRepository.save(emp1One);
        projectRepository.save(project1);


        //employee 2, project 2

        //create first employee
        Employee emp1Two = new Employee("Peter", "Rodriguez", "12.07.2011", "3500");

        //create first project
        Project project2 = new Project("Project Two", pm2, "13.05.2018");

        //add project1 object to employee 1 object
        emp1One.getProjects().add(project2);

        //saving data to DB
        employeeRepository.save(emp1Two);
        projectRepository.save(project2);


        //






    }

}

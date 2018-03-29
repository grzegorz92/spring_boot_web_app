package com.testprojects.firstapp.model;


import javax.persistence.*;
import java.util.*;

@Entity
//@Table(NAME="project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String projectName;
    private String deadlineDate;

    @ManyToMany
    @JoinTable(name ="employee_project", joinColumns = @JoinColumn(name="employee_id"), inverseJoinColumns = @JoinColumn(name="project_id"))
    private Set<Employee> employees = new HashSet<>();

    @OneToOne
    private ProjectManager projectManager;




    public Project() {
    }

    public Project(String projectName, ProjectManager projectManager, String deadlineDate) {
        this.projectName = projectName;
        this.projectManager = projectManager;
        this.deadlineDate = deadlineDate;
    }

    public Project(String projectName, ProjectManager projectManager, String deadlineDate, Set<Employee> employees) {
        this.projectName = projectName;
        this.projectManager = projectManager;
        this.deadlineDate = deadlineDate;
        this.employees = employees;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public ProjectManager getProjectManager() {
        return projectManager;
    }

    public void setProjectManager(ProjectManager projectManagerId) {
        this.projectManager = projectManager;
    }

    public String getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(String deadLineDate) {
        this.deadlineDate = deadLineDate;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }



    //Koles mowi, zeby wygenerowac hashcody i equals na podstawie id i jednoczenie mowi, ze jest to odradzane. WTF????
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return id == project.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectManagerId='" + projectManager + '\'' +
                ", deadlineDate='" + deadlineDate + '\'' +
                ", employees=" + employees +
                '}';
    }
}

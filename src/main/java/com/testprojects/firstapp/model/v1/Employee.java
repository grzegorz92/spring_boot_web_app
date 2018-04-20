package com.testprojects.firstapp.model.v1;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String lastName;
    private String hireDate;
    private String salary;

    @ManyToMany(mappedBy = "employees")
    private Set<Project> projects = new HashSet<>();



    public Employee() {
    }

    public Employee(String name, String lastName, String hireDate, String salary) {
        this.name = name;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.salary = salary;
    }

    public Employee(String name, String lastName, String hireDate, String salary, Set<Project> projects) {
        this.name = name;
        this.lastName = lastName;
        this.hireDate = hireDate;
        this.salary = salary;
       this.projects = projects;

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", hireDate='" + hireDate + '\'' +
                ", salary='" + salary + '\'' +
                ", projects=" + projects +
                '}';
    }
}

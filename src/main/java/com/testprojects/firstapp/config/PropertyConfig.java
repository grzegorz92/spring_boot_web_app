package com.testprojects.firstapp.config;


import com.testprojects.firstapp.model.Employee;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
@PropertySource("classpath:employees.properties")
@ConfigurationProperties(prefix = "e")
public class PropertyConfig {



    private List<String> name = new ArrayList<>();
    private List<String> lastName = new ArrayList<>();
    private List<String> hireDate = new ArrayList<>();
    private List<String> salary = new ArrayList<>();

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public List<String> getLastName() {
        return lastName;
    }

    public void setLastName(List<String> lastName) {
        this.lastName = lastName;
    }

    public List<String> getHireDate() {
        return hireDate;
    }

    public void setHireDate(List<String> hireDate) {
        this.hireDate = hireDate;
    }

    public List<String> getSalary() {
        return salary;
    }

    public void setSalary(List<String> salary) {
        this.salary = salary;
    }


    /*

    //@Value("${e.name}") -- not required if @ConfigurationProperties is used
    private String name;

    //@Value("${e.lastName}")
    private String lastName;

    //@Value("${e.hireDate}")
    private String hireDate;

    //@Value("${e.salary}")
    private String salary;

    */

    /*
    @Bean
    public Employee init(){
    Employee employee = new Employee(name,lastName,hireDate,salary);
    return employee;
    }


    @Bean
    public PropertySourcesPlaceholderConfigurer properties(){

        PropertySourcesPlaceholderConfigurer pspc = new PropertySourcesPlaceholderConfigurer();
        return pspc;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public String getHireDate() {
        return hireDate;
    }

    public String getSalary() {
        return salary;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    */
}

package com.testprojects.firstapp.repositories;

import com.testprojects.firstapp.model.Employee;
import org.springframework.data.repository.CrudRepository;

//for spring data
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}

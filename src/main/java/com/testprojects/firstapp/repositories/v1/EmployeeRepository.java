package com.testprojects.firstapp.repositories.v1;

import com.testprojects.firstapp.model.v1.Employee;
import org.springframework.data.repository.CrudRepository;

//for spring data
public interface EmployeeRepository extends CrudRepository<Employee, Integer> {

}

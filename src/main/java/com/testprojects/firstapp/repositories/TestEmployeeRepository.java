package com.testprojects.firstapp.repositories;

import com.testprojects.firstapp.model.TestEmployee;
import org.springframework.data.repository.CrudRepository;

public interface TestEmployeeRepository extends CrudRepository<TestEmployee, Integer> {
}


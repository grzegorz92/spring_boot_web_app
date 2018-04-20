package com.testprojects.firstapp.repositories.v1;

import com.testprojects.firstapp.model.v1.TestEmployee;
import org.springframework.data.repository.CrudRepository;

public interface TestEmployeeRepository extends CrudRepository<TestEmployee, Integer> {
}


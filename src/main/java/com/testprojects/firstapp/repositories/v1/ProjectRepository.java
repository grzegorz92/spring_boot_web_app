package com.testprojects.firstapp.repositories.v1;

import com.testprojects.firstapp.model.v1.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}

package com.testprojects.firstapp.repositories;

import com.testprojects.firstapp.model.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepository extends CrudRepository<Project, Integer> {
}

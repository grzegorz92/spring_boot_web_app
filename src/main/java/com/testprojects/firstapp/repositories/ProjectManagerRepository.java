package com.testprojects.firstapp.repositories;

import com.testprojects.firstapp.model.ProjectManager;
import org.springframework.data.repository.CrudRepository;

public interface ProjectManagerRepository extends CrudRepository<ProjectManager, Integer> {
}

package com.testprojects.firstapp.repositories.v1;

import com.testprojects.firstapp.model.v1.ProjectManager;
import org.springframework.data.repository.CrudRepository;

public interface ProjectManagerRepository extends CrudRepository<ProjectManager, Integer> {
}

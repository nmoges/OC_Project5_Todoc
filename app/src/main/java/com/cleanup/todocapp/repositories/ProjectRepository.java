package com.cleanup.todocapp.repositories;

import androidx.lifecycle.LiveData;
import com.cleanup.todocapp.dao.ProjectDao;
import com.cleanup.todocapp.model.Project;
import java.util.List;

/**
 * <p>Repository class to communicate with project_table from TodocDatabase,
 * using ProjectDao interface methods</p>
 */
public class ProjectRepository {

    /**
     * DAO interface
     */
    private final ProjectDao projectDao;

    public ProjectRepository(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    // Accessing DAO methods
    public void insertProject(Project project) {
        projectDao.insertProject(project);
    }

    public LiveData<List<Project>> loadAllProjects() {
        return projectDao.loadAllProject();
    }

}

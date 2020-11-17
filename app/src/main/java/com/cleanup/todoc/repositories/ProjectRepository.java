package com.cleanup.todoc.repositories;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.model.Project;
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

    // Defined only for testing
    @VisibleForTesting
    public Project getProject(int projectId) {
        return projectDao.getProject(projectId);
    }

    public LiveData<List<Project>> loadAllProjects() {
        return projectDao.loadAllProject();
    }

}

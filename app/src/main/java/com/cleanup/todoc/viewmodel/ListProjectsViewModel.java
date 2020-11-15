package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import java.util.List;

/**
 * ViewModel class containing the list of existing Projects, wrapped in LiveData
 */
public class ListProjectsViewModel extends ViewModel {

    /**
     * Repository to access ProjectDao interface methods
     */
    private final ProjectRepository projectRepository;

    /**
     * LiveData containing the list of existing Projects
     */
    private LiveData<List<Project>> listProjects = new MutableLiveData<>();

    public ListProjectsViewModel(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public LiveData<List<Project>> getListProjects() {
        if (listProjects == null) {
            listProjects = new MutableLiveData<>();
        }
        else {
            listProjects = projectRepository.loadAllProjects();
        }
        return listProjects;
    }

    // Project Repository methods
    public void insertProject(Project project) {
        TodocDatabase.executorService.execute(() -> projectRepository.insertProject(project));
    }

    public void deleteProject(Project project) {
        TodocDatabase.executorService.execute(() -> projectRepository.deleteProject(project));
    }

    public void update(Project project) {
        TodocDatabase.executorService.execute(() -> projectRepository.updateProject(project));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

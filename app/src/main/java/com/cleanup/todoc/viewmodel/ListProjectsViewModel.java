package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * <p>ViewModel class containing the list of existing Projects, wrapped in LiveData</p>
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

    /**
     * Executor to access Database in another thread than UI thread
     */
    private final Executor executor;

    public ListProjectsViewModel(final ProjectRepository projectRepository, final Executor executor) {
        this.projectRepository = projectRepository;
        this.executor = executor;
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
        executor.execute(() -> projectRepository.insertProject(project));
    }

}

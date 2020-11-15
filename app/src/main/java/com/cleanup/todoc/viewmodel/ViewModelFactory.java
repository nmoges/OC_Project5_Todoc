package com.cleanup.todoc.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;

/**
 * Factory class used to create LiveDataListTasksVM ViewModel
 */
public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    public ViewModelFactory(Context context) {

        TodocDatabase database = TodocDatabase.getInstance(context);

        taskRepository = new TaskRepository(database.taskDao());
        projectRepository = new ProjectRepository(database.projectDao());
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(ListTasksViewModel.class)) return (T) new ListTasksViewModel(taskRepository);

        if(modelClass.isAssignableFrom(ListProjectsViewModel.class)) return (T) new ListProjectsViewModel(projectRepository);

       throw new IllegalArgumentException("Unknown ViewModel class");
    }
}

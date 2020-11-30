package com.cleanup.todocapp.viewmodel;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.cleanup.todocapp.database.TodocDatabase;
import com.cleanup.todocapp.di.DI;
import com.cleanup.todocapp.repositories.ProjectRepository;
import com.cleanup.todocapp.repositories.TaskRepository;
import java.util.concurrent.Executor;

/**
 * <p>Factory class used to create LiveDataListTasksVM ViewModel</p>
 */

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;

    private final Executor executor;

    public ViewModelFactory(Context context) {

        TodocDatabase database = DI.provideDatabase(context);

        this.executor = DI.provideExecutor();

        this.taskRepository = new TaskRepository(database.taskDao());
        this.projectRepository = new ProjectRepository(database.projectDao());
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        if(modelClass.isAssignableFrom(ListTasksViewModel.class)) return (T) new ListTasksViewModel(taskRepository, executor);

        if(modelClass.isAssignableFrom(ListProjectsViewModel.class)) return (T) new ListProjectsViewModel(projectRepository, executor);

        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
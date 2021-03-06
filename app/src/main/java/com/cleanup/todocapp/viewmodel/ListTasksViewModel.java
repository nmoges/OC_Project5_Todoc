package com.cleanup.todocapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cleanup.todocapp.model.Task;
import com.cleanup.todocapp.repositories.TaskRepository;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * <p>ViewModel class containing the list of existing Tasks, wrapped in LiveData</p>
 */
public class ListTasksViewModel extends ViewModel {

    /**
     * Repository to access TaskDao interface methods
     */
    private final TaskRepository taskRepository;

    /**
     * LiveData containing the list of existing Tasks
     */
    private LiveData<List<Task>> listTasks = new MutableLiveData<>();

    /**
     * Executor to access Database in another thread than UI thread
     */
    private final Executor executor;


    public ListTasksViewModel(final TaskRepository taskRepository, final Executor executor) {
        this.taskRepository = taskRepository;
        this.executor = executor;
    }

    public LiveData<List<Task>> getListTasks() {
        if (listTasks == null) {
            listTasks = new MutableLiveData<>();
        }
        else {
            listTasks = taskRepository.loadAllTasks();
        }
        return listTasks;
    }

    // Task Repository methods
    public void insertTask(Task task) {
        executor.execute(() -> taskRepository.insertTask(task));
    }

    public void updateTask(Task task) {
        executor.execute(() -> taskRepository.updateTask(task));
    }

    public void deleteTask(Task task) {
        executor.execute(() -> taskRepository.deleteTask(task));
    }

    public void deleteAllTasks() {
        executor.execute(taskRepository::deleteAllTask);
    }
}

package com.cleanup.todoc.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.TaskRepository;
import java.util.List;

/**
 * ViewModel class containing the list of existing Tasks, wrapped in LiveData
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

    public ListTasksViewModel(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
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
        TodocDatabase.executorService.execute(() -> taskRepository.insertTask(task));
    }

    public void deleteTask(Task task) {
        TodocDatabase.executorService.execute(() -> taskRepository.deleteTask(task));
    }

    public void updateTask(Task task) {
        TodocDatabase.executorService.execute(() -> taskRepository.updateTask(task));
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}

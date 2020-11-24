package com.cleanup.todoc.repositories;

import androidx.lifecycle.LiveData;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Task;
import java.util.List;

/**
 * <p>Repository class to communicate with task_table from TodocDatabase,
 * using TaskDao interface methods</p>
 */
public class TaskRepository {

    /**
     * DAO interface
     */
    private final TaskDao taskDao;

    public TaskRepository(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    // Accessing DAO methods
    public void insertTask(Task task) {
        taskDao.insertTask(task);
    }

    public int updateTask(Task task) {
        return taskDao.updateTask(task);
    }

    public int deleteTask(Task task) {
        return taskDao.deleteTask(task);
    }

    public LiveData<List<Task>> loadAllTasks() {
        return taskDao.loadAllTask();
    }

    public void deleteAllTask() {
        taskDao.deleteAllTasks();
    }
}

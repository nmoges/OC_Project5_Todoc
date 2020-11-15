package com.cleanup.todoc;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * This file tests all TaskDAO interface methods to access task_database
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    private TodocDatabase database;
    private Project[] allProjects;
    private final static int FIRST_TASK_ID = 1;

    @Before
    public void initDatabase() {
        Context context = ApplicationProvider.getApplicationContext();

        // Initialize database
        this.database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
        allProjects = Project.getAllProjects();

        // Initialize project_table in database
        for (Project allProject : allProjects) {
            database.projectDao().insertProject(allProject);
        }
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    /**
     * This test :
     *          - creates a new task to insert in task_table
     *          - uses taskDao interface to insert the task
     *          - uses taskDAO interface to read back the task
     *          - checks if the read data is correct
     */
    @Test
    public void insertNewTaskInTable() {

        // Create Task to insert in task_table
        long creationTimestamp = new Date().getTime();
        String nameTask = "Appeler le client";

        //Task task = new Task(FIRST_TASK_ID, 1L, nameTask, creationTimestamp);
        Task task = new Task(1, nameTask, creationTimestamp);
        // Insert task
        database.taskDao().insertTask(task);

        // Read task from database
        Task taskRead = database.taskDao().getTask(FIRST_TASK_ID);
        assertEquals(FIRST_TASK_ID, taskRead.getId());
        assertEquals(1, taskRead.getProjectId());
        assertEquals(nameTask, taskRead.getName());
        assertEquals(creationTimestamp, taskRead.getCreationTimestamp());
    }

    /**
     * This test :
     *      - creates three Tasks
     *      - inserts these Tasks in task_table
     *      - removes second task from task_table
     *      - checks if operation succeed (1 row deleted)
     *      - tries to remove second task again
     *      - checks if operation does not succeed (0 row deleted)
     */
    @Test
    public void removeTaskFromTable() {
        // Create Tasks to insert in task_table
        Task firstTask = new Task(1, "Appeler le client", new Date().getTime());
        Task secondTask = new Task(2, "Intégrer Google Analytics", new Date().getTime());
        Task thirdTask = new Task(3, "Modifier la couleur des textes", new Date().getTime());

        // Insert tasks
        database.taskDao().insertTask(firstTask);
        database.taskDao().insertTask(secondTask);
        database.taskDao().insertTask(thirdTask);

        // Get the task to remove with primaryKey incremented
        Task taskToRemove = database.taskDao().getTask(1);

        // Remove task from database
        int result = database.taskDao().deleteTask(taskToRemove);
        assertEquals(result, 1); // 1 row deleted

        // Check if same operation doesn't work (task already removed)
        result = database.taskDao().deleteTask(taskToRemove);
        assertEquals(result, 0); // 0 row deleted
    }
}

package com.cleanup.todoc;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.viewmodel.ListTasksViewModel;
import com.cleanup.todoc.viewmodel.ViewModelFactory;
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

    @Before
    public void initDatabase() {
        Context context = ApplicationProvider.getApplicationContext();

        // Initialize database
        this.database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();

        // Initialize Repositories
        ProjectRepository projectRepository = new ProjectRepository(database.projectDao());

        // Initialize project_table in database
        Project[] allProjects = DI.providesProjects(context);
        for (Project allProject : allProjects) {
            projectRepository.insertProject(allProject);
        }
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    /**
     * This test :
     *          - creates a new task to insert in task_table
     *          - uses TaskDao to insert the task
     *          - uses TaskDao to read back the task
     *          - checks if the read data is correct
     */
    @Test
    public void insert_new_task_in_table() {

        // Create Task to insert in task_table
        Task task = new Task(1, "Appeler le client", new Date().getTime());

        // Insert task
        database.taskDao().insertTask(task);

        // Read task from database
        Task taskRead = database.taskDao().getTask(1);

        // Check fields
        assertEquals(1, taskRead.getId());
        assertEquals(1, taskRead.getProjectId());
        assertEquals("Appeler le client", taskRead.getName());
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
    public void remove_task_from_table() {
        // Create Tasks to insert in task_table
        Task[] tasks = new Task[]{
                new Task(1, "Appeler le client", new Date().getTime()),
                new Task(2, "Intégrer Google Analytics", new Date().getTime()),
                new Task(3, "Modifier la couleur des textes", new Date().getTime())
        };

        for(Task task : tasks) {
            database.taskDao().insertTask(task);
        }

        // Get the task to remove with primaryKey incremented
        Task taskToRemove = database.taskDao().getTask(1);

        // Remove task from database
        int result = database.taskDao().deleteTask(taskToRemove);
        assertEquals(1, result); // 1 row deleted

        // Check if same operation doesn't work (task already removed)
        result = database.taskDao().deleteTask(taskToRemove);
        assertEquals(0, result); // 0 row deleted
    }

    @Test
    public void update_task_in_table() {
        // Create Tasks to insert in task_table
        Task[] tasks = new Task[]{
                new Task(1, "Appeler le client", new Date().getTime()),
                new Task(2, "Intégrer Google Analytics", new Date().getTime()),
                        new Task(3, "Modifier la couleur des textes", new Date().getTime())
        };

        // Store in database
        for(Task task : tasks) {
            database.taskDao().insertTask(task);
        }

        // Get task to modify
        Task taskToUpdate = database.taskDao().getTask(2);
        taskToUpdate.setName("Validation software");

        int result = database.taskDao().updateTask(taskToUpdate);

        // Get task modified
        taskToUpdate = database.taskDao().getTask(2);

        assertEquals(1, result);
        assertEquals("Validation software", taskToUpdate.getName());
    }

}

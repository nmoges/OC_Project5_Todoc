package com.cleanup.todoc;

import android.content.Context;
import android.util.Log;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.repositories.TaskRepository;
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
// TODO() : replace current file with file testing ViewModelTask

@RunWith(AndroidJUnit4.class)
public class TaskRepositoryTest {

    private TodocDatabase database;
    private Project[] allProjects;
    private final static int FIRST_TASK_ID = 1;

    private TaskRepository taskRepository;

    private ListTasksViewModel listTasksViewModel;


    @Before
    public void initDatabase() {
        Context context = ApplicationProvider.getApplicationContext();

        // Initialize database
        this.database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();

        // Initiates a Factory to create ViewModel instances
        ViewModelFactory factory = new ViewModelFactory(context);
        listTasksViewModel = factory.create(ListTasksViewModel.class);

        // Initialize Repositories
        ProjectRepository projectRepository = new ProjectRepository(database.projectDao());
        taskRepository = new TaskRepository(database.taskDao());

        // Initialize project_table in database
        allProjects = DI.providesProjects(context);
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
     *          - uses TaskRepository to insert the task
     *          - uses TaskRepository to read back the task
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
        listTasksViewModel.insertTask(task);

        // Read task from database
        Task taskRead = taskRepository.getTask(FIRST_TASK_ID);

        if(taskRead == null) Log.i("TASKREAD", "NULL");
        else Log.i("TASKREAD", "NOT NULL");

        // Check fields
        //assertEquals(FIRST_TASK_ID, taskRead.getId());
        //assertEquals(1, taskRead.getProjectId());
        //assertEquals(nameTask, taskRead.getName());
        //assertEquals(creationTimestamp, taskRead.getCreationTimestamp());
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
        Task[] tasks = new Task[]{
                new Task(1, "Appeler le client", new Date().getTime()),
                new Task(2, "Intégrer Google Analytics", new Date().getTime()),
                new Task(3, "Modifier la couleur des textes", new Date().getTime())
        };

        for(Task task : tasks) {
            taskRepository.insertTask(task);
        }

        // Get the task to remove with primaryKey incremented
        Task taskToRemove = taskRepository.getTask(1);

        // Remove task from database
        int result = taskRepository.deleteTask(taskToRemove);
        assertEquals(result, 1); // 1 row deleted

        // Check if same operation doesn't work (task already removed)
        result = taskRepository.deleteTask(taskToRemove);
        assertEquals(result, 0); // 0 row deleted
    }

    @Test
    public void test() {
        // Create Tasks to insert in task_table
        Task firstTask = new Task(1, "Appeler le client", new Date().getTime());
        Log.i("TASKTEST", firstTask.getName());
        // Insert task
        taskRepository.insertTask(firstTask);

        // Update task
        firstTask.setName("Réunion d'avancement");

        Log.i("TASKTEST", firstTask.getName());
        taskRepository.updateTask(firstTask);

        // Read back updated task
        Task taskToRead = taskRepository.getTask(FIRST_TASK_ID);
        Log.i("TASKTEST", taskToRead.getName());

        //assertEquals(firstTask.getName(), taskToRead.getName());
        //assertEquals(firstTask.getCreationTimestamp(), taskToRead.getCreationTimestamp());
        //assertEquals(firstTask.getProjectId(), taskToRead.getProjectId());

    }
}


/*

        Task firstTask = new Task(1, "Appeler le client", new Date().getTime());
        Task secondTask = new Task(2, "Intégrer Google Analytics", new Date().getTime());
        Task thirdTask = new Task(3, "Modifier la couleur des textes", new Date().getTime());

        // Insert tasks
        taskRepository.insertTask(firstTask);
        taskRepository.insertTask(secondTask);
        taskRepository.insertTask(thirdTask);
 */
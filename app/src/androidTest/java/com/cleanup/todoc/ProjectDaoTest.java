package com.cleanup.todoc;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.database.TodocDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;

/**
 * This file tests all ProjectDAO interface methods to access project_database
 */
@RunWith(AndroidJUnit4.class)
public class ProjectDaoTest {

    private TodocDatabase database;
    private final Project[] allProjects = Project.getAllProjects();

    @Before
    public void initDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        this.database = Room.inMemoryDatabaseBuilder(context, TodocDatabase.class).build();
    }

    @After
    public void closeDatabase() {
        database.close();
    }

    /**
     * This test :
     *  - initializes project_table by inserting all existing projects,
     *  - read first project from database using its projectId
     *  - checks if read data is correct
     */
    @Test
    public void insertProjectsInDatabaseThenRead() {

        // Insert all existing projects
        for (int i = 0; i < allProjects.length; i++) {
            database.projectDao().insertProject(allProjects[i]);
        }

        // Get first project from database (Project Projet Tartampion)
        Project project = database.projectDao().getProject(1L);

        // Read values
        assertEquals(project.getProjectId(), allProjects[0].getProjectId());
        assertEquals(project.getName(), allProjects[0].getName());
        assertEquals(project.getColor(), allProjects[0].getColor());
    }

}

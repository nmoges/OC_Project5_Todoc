package com.cleanup.todoc;

import android.content.Context;
import android.content.res.Resources;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

/**
 * <p> Unit tests for projects
 *     This file tests if the DI class provides a Project[] object with correct information for each Project
 * </p>
 */
@RunWith(JUnit4.class)
public class ProjectUnitTest {

    @Mock
    private Context mockContext;

    @Mock
    private Resources mockResources;

    /**
     * This test :
     *      - defines mock Context and Resources objects needed to call the DI method
     *      - calls the DI method providing a Project[] object
     *      - checks size of Project[]
     *      - check each item of Project[]
     */
    @Test
    public void test_di_projects_provider() {
        mockContext = Mockito.mock(Context.class);
        mockResources = Mockito.mock(Resources.class);

        // Define Context mock properties
        Mockito.when(mockContext.getResources()).thenReturn(mockResources);

        // Define Resources mock properties
        Mockito.when(mockResources.getColor(R.color.project_tartampion)).thenReturn(R.color.project_tartampion);
        Mockito.when(mockResources.getColor(R.color.project_lucidia)).thenReturn(R.color.project_lucidia);
        Mockito.when(mockResources.getColor(R.color.project_circus)).thenReturn(R.color.project_circus);
        Mockito.when(mockResources.getString(R.string.project_tartampion)).thenReturn("Projet Tartampion");
        Mockito.when(mockResources.getString(R.string.project_lucidia)).thenReturn("Projet Lucidia");
        Mockito.when(mockResources.getString(R.string.project_circus)).thenReturn("Projet Circus");

        // Get projets from dependency injector
        Project[] projects = DI.providesProjects(mockContext);
        assertEquals(3, projects.length);

        // Check projects informations
        assertEquals(R.color.project_tartampion, projects[0].getColor());
        assertEquals("Projet Tartampion", projects[0].getName());

        assertEquals(R.color.project_lucidia, projects[1].getColor());
        assertEquals("Projet Lucidia", projects[1].getName());

        assertEquals(R.color.project_circus, projects[2].getColor());
        assertEquals("Projet Circus", projects[2].getName());
    }
}

package com.cleanup.todocapp;

import android.content.Context;
import android.content.res.Resources;
import com.cleanup.todocapp.model.Project;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.junit.Assert.assertEquals;

/**
 * <p>
 *     Unit tests for projects
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
     *      - creates 3 projects
     *      - checks if Projects objects are correctly created
     */
    @Test
    public void test_project_objects() {
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

        // Create Project objects
        final Project project1 = new Project(mockContext.getResources().getString(R.string.project_tartampion),
                                             mockContext.getResources().getColor(R.color.project_tartampion));

        final Project project2 = new Project(mockContext.getResources().getString(R.string.project_lucidia),
                mockContext.getResources().getColor(R.color.project_lucidia));

        final Project project3 = new Project(mockContext.getResources().getString(R.string.project_circus),
                mockContext.getResources().getColor(R.color.project_circus));

        // Check objects informations
        assertEquals(R.color.project_tartampion, project1.getColor());
        assertEquals("Projet Tartampion", project1.getName());

        assertEquals(R.color.project_lucidia, project2.getColor());
        assertEquals("Projet Lucidia", project2.getName());

        assertEquals(R.color.project_circus, project3.getColor());
        assertEquals("Projet Circus", project3.getName());
    }
}

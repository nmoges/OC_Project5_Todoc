package com.cleanup.todoc.model;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * <p>Models for project in which tasks are included.</p>
 *
 * @author Gaëtan HERFRAY
 */
@Entity(tableName = "project_table")
public class Project {
    /**
     * The unique identifier of the project
     */
    @PrimaryKey(autoGenerate = true)
    private final long projectId;

    /**
     * The name of the project
     */
    @ColumnInfo(name = "name_project")
    @NonNull
    private final String name;

    /**
     * The hex (ARGB) code of the color associated to the project
     */
    @ColumnInfo(name = "color_project")
    @ColorInt
    private final int color;

    /**
     * Instantiates a new Project.
     *
     * @param projectId    the unique identifier of the project to set
     * @param name  the name of the project to set
     * @param color the hex (ARGB) code of the color associated to the project to set
     */
    public Project(long projectId, @NonNull String name, @ColorInt int color) {
        this.projectId = projectId;
        this.name = name;
        this.color = color;
    }

    /**
     * Returns all the projects of the application.
     *
     * @return all the projects of the application
     */
    @NonNull
    public static Project[] getAllProjects() {
        return new Project[]{
                new Project(1L, "Projet Tartampion", 0xFFEADAD1),
                new Project(2L, "Projet Lucidia", 0xFFB4CDBA),
                new Project(3L, "Projet Circus", 0xFFA3CED2),
        };
    }

    /**
     * Returns the project with the given unique identifier, or null if no project with that
     * identifier can be found.
     *
     * @param projectId the unique identifier of the project to return
     * @return the project with the given unique identifier, or null if it has not been found
     */
    @Nullable
    public static Project getProjectById(long projectId) {
        for (Project project : getAllProjects()) {
            if (project.projectId == projectId)
                return project;
        }
        return null;
    }

    /**
     * Returns the unique identifier of the project.
     *
     * @return the unique identifier of the project
     */
    public long getProjectId() {
        return projectId;
    }

    /**
     * Returns the name of the project.
     *
     * @return the name of the project
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Returns the hex (ARGB) code of the color associated to the project.
     *
     * @return the hex (ARGB) code of the color associated to the project
     */
    @ColorInt
    public int getColor() {
        return color;
    }

    @Override
    @NonNull
    public String toString() {
        return getName();
    }
}

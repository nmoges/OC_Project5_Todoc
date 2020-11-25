package com.cleanup.todoc.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

/**
 * <p>Model for the tasks of the application.</p>
 *
 * @author Gaëtan HERFRAY (update : Nicolas MOGES)
 */
@Entity(tableName = "task_table",
        foreignKeys = @ForeignKey(entity = Project.class, parentColumns = "id", childColumns = "project_id"))
public class Task {
    /**
     * The unique identifier of the task
     */
    @PrimaryKey(autoGenerate = true)
    public int id;

    /**
     * The unique identifier of the project associated to the task
     */
    @ColumnInfo(name = "project_id")
    private int projectId;

    /**
     * The name of the task
     */
    @NonNull
    private String name = "";

    /**
     * The timestamp when the task has been created
     */
    @ColumnInfo(name = "timestamp_creation")
    private long creationTimestamp;

    /**
     * Instantiates a new Task.
     *
     * @param projectId         the unique identifier of the project associated to the task to set
     * @param name              the name of the task to set
     * @param creationTimestamp the timestamp when the task has been created to set
     */

    public Task(int projectId, @NonNull String name, long creationTimestamp) {
        this.setProjectId(projectId);
        this.setName(name);
        this.setCreationTimestamp(creationTimestamp);
    }

    /**
     * Return the id of the associated Project
     * @return : id of the Project
     */
    public int getProjectId() {
        return projectId;
    }

    /**
     * Return timestamp of the task
     * @return : time when task was created
     */
    public long getCreationTimestamp() {
        return creationTimestamp;
    }

    /**
     * Returns the unique identifier of the task.
     * @return the unique identifier of the task
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the project associated to the task.
     * @param projectId the unique identifier of the project associated to the task to set
     */
    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    /**
     * Returns the name of the task.
     * @return the name of the task
     */
    @NonNull
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the name of the task to set
     */
    public void setName(@NonNull String name) {
        this.name = name;
    }

    /**
     * Sets the timestamp when the task has been created.
     *
     * @param creationTimestamp the timestamp when the task has been created to set
     */
    public void setCreationTimestamp(long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }
}

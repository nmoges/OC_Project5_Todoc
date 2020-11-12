package com.cleanup.todoc.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.cleanup.todoc.model.Project;

/**
 * DAO interface defining methods to access project_table data
 */
@Dao
public interface ProjectDao {


    @Insert
    void insertProject(Project project);

    @Update
    void updateProject(Project project);

    @Delete
    void deleteProject(Project project);

    @Query("SELECT * FROM project_table WHERE projectId = :projectId")
    Project getProject(long projectId);

}

package com.cleanup.todoc.dao;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import com.cleanup.todoc.model.Project;
import java.util.List;

/**
 * DAO interface defining methods to access project_table data
 */
@Dao
public interface ProjectDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProject(Project project);

    @VisibleForTesting
    @Query("SELECT * FROM project_table WHERE id = :id")
    Project getProject(int id);

    @Query("SELECT * FROM project_table ORDER BY id")
    LiveData<List<Project>> loadAllProject();
}

package com.cleanup.todoc.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.cleanup.todoc.model.Task;
import java.util.ArrayList;

/**
 * DAO interface defining methods to access task_table data
 */
@Dao
public interface TaskDao {

   @Insert
   void insertTask(Task task);

   @Update
   void updateTask(Task task);

   @Delete
   int deleteTask(Task task);

   @Query("SELECT * FROM task_table WHERE taskId = :taskId")
   Task getTask(long taskId);
}

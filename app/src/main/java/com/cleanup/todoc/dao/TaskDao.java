package com.cleanup.todoc.dao;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.cleanup.todoc.model.Task;
import java.util.List;

/**
 * <p>DAO interface defining methods to access task_table data</p>
 */
@Dao
public interface TaskDao {

   @Insert(onConflict = OnConflictStrategy.IGNORE)
   void insertTask(Task task);

   @Update
   int updateTask(Task task);

   @Delete
   int deleteTask(Task task);

   @VisibleForTesting
   @Query("SELECT * FROM task_table WHERE id = :id")
   Task getTask(int id);

   @Query("SELECT * FROM task_table ORDER BY id")
   LiveData<List<Task>> loadAllTask();

   @Query("DELETE FROM task_table")
   void deleteAllTasks();
}

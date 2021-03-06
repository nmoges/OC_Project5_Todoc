package com.cleanup.todocapp.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cleanup.todocapp.dao.ProjectDao;
import com.cleanup.todocapp.dao.TaskDao;
import com.cleanup.todocapp.model.Project;
import com.cleanup.todocapp.model.Task;

/**
 * <p>This class defines Todoc database which contains two tables :
 *      - project_table
 *      - task_table</p>
 */
@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    // DAO for both database table
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    // Application Database instance
    private static TodocDatabase instance;

    // Create Database singleton
    public static synchronized TodocDatabase getInstance(Context context) {
        if (instance == null) {

            // Create instance
            instance = Room.databaseBuilder(context, TodocDatabase.class, "todoc_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}

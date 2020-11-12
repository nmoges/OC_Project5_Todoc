package com.cleanup.todoc.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();

    private static TodocDatabase instance;


    public static synchronized TodocDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, TodocDatabase.class, "task_database")
                    .build();
        }
        return instance;
    }
}

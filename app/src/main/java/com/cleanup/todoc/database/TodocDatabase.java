package com.cleanup.todoc.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

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
            instance = Room.databaseBuilder(context, TodocDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .build();

            // Check if data already exists in parent table
            DI.provideExecutor().execute(() -> {
                    if (instance.projectDao().getProject(1) == null) {
                        Project[] projects = DI.providesProjects(context);
                        for (Project project : projects) {
                            instance.projectDao().insertProject(project);
                        }
                    }
                }
            );

        }
        return instance;
    }
}

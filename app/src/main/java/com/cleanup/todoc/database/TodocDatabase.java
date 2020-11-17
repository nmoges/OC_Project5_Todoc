package com.cleanup.todoc.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;

/**
 * This class defines Todoc database which contains two tables :
 *      - project_table
 *      - task_table
 */
// TODO() : Implement migration strategy
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
                    .build();

            // Check if Database has already be initialized
            SharedPreferences initialized = context.getSharedPreferences("database_status", Context.MODE_PRIVATE);

            // If not : Initialize by inserting items in project_table
            if(!initialized.getBoolean("database_initialized", false)){
                DI.provideExecutor().execute(() -> {
                            Project[] projects = DI.providesProjects(context);
                            for (Project project : projects) {
                                instance.projectDao().insertProject(project);
                            }
                        }
                );

                // Saving state
                SharedPreferences.Editor editor = initialized.edit();
                editor.putBoolean("database_initialized", true);
                editor.apply();
            }

        }
        return instance;
    }
}

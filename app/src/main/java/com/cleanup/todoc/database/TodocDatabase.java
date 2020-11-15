package com.cleanup.todoc.database;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.cleanup.todoc.dao.ProjectDao;
import com.cleanup.todoc.dao.TaskDao;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This class defines Todoc database which contains two tables :
 *      - project_table
 *      - task_table
 */
@Database(entities = {Task.class, Project.class}, version = 1)
public abstract class TodocDatabase extends RoomDatabase {

    // DAO for both databaase table
    public abstract TaskDao taskDao();
    public abstract ProjectDao projectDao();
    public static ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Application Database instance
    private static TodocDatabase instance;

    // Create Database singleton
    public static synchronized TodocDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, TodocDatabase.class, "task_database")
                    .addCallback(databaseCallback)
                    .build();
        }
        return instance;
    }

    // Callback to initialize Database
    private static final RoomDatabase.Callback databaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // Insert all pre-defined projects in database
            executorService.execute(() -> {
                    for (Project project : Project.getAllProjects()) {
                        instance.projectDao().insertProject(project);
                    }
                }
            );
        }
    };
}

package com.cleanup.todoc.di;

import android.content.Context;
import com.cleanup.todoc.R;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.model.Project;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * <p>Dependency injector</p>
 */
public class DI {

    /**
     * Provides singleton Todoc Database instance
     * @param context : Context of the view
     * @return : Database instance
     */
    public static TodocDatabase provideDatabase(Context context) {
        TodocDatabase instance = TodocDatabase.getInstance(context);

        // Check if data already exists in parent table
        DI.provideExecutor().execute(() -> {
                    if (instance.projectDao().getProject(1) == null) {
                        Project[] projects = provideProjects(context);
                        for (Project project : projects) {
                            instance.projectDao().insertProject(project);
                        }
                    }
                }
        );

        return instance;
    }

    /**
     * Provides a worker thread for SQLite database requests
     * @return : the Executor worker thread
     */
    public static Executor provideExecutor() {
        return Executors.newSingleThreadExecutor();
    }

    /**
     * Provides a table containing pre-defined projects to store in database
     * @return : table of pre-defined projects
     */
    public static Project[] provideProjects(Context context) {

        return new Project[]{
                new Project(context.getResources().getString(R.string.project_tartampion), context.getResources().getColor(R.color.project_tartampion)),
                new Project(context.getResources().getString(R.string.project_lucidia), context.getResources().getColor(R.color.project_lucidia)),
                new Project(context.getResources().getString(R.string.project_circus), context.getResources().getColor(R.color.project_circus))
        };

    }
}
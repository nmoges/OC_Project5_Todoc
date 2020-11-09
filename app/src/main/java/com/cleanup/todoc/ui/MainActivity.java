package com.cleanup.todoc.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.ActivityMainBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.dialogs.TaskDialog;
import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>Home activity of the application which is displayed when the user opens the app.</p>
 * <p>Displays the list of tasks.</p>
 *
 * @author Gaëtan HERFRAY
 */
public class MainActivity extends AppCompatActivity implements TasksAdapter.DeleteTaskListener, TaskActions {

    private ActivityMainBinding binding;

    /**
     * List of all projects available in the application
     */
    private final Project[] allProjects = Project.getAllProjects();

    /**
     * List of all current tasks of the application
     */
    @NonNull
    private final ArrayList<Task> tasks = new ArrayList<>();

    /**
     * The adapter which handles the list of tasks
     */
    private final TasksAdapter adapter = new TasksAdapter(tasks, this);

    /**
     * The sort method to be used to display tasks
     */
    @NonNull
    private SortMethod sortMethod = SortMethod.NONE;

    /**
     * The dialog to create a new task
     */
    @Nullable
    public TaskDialog taskDialog = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeRecyclerView();

        initializeTaskDialog();

        handleFab();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) sortMethod = SortMethod.ALPHABETICAL;
        else if (id == R.id.filter_alphabetical_inverted) sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        else if (id == R.id.filter_oldest_first) sortMethod = SortMethod.OLD_FIRST;
        else if (id == R.id.filter_recent_first) sortMethod = SortMethod.RECENT_FIRST;

        updateTasks();

        return super.onOptionsItemSelected(item);
    }

    /**
     * TaskAdapter.DeleteTaskListener interface implementaiton
     * @param task : the task that needs to be deleted
     */
    @Override
    public void onDeleteTask(Task task) {
        tasks.remove(task);
        updateTasks();
    }

    /**
     * Handles Floating Action Button click.
     * A click displays the Task Dialog
     */
    private void handleFab() {
        binding.fabAddTask.setOnClickListener((View view) ->  showAddTaskDialog() );
    }

    private void initializeRecyclerView() {
        binding.listTasks.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.listTasks.setAdapter(adapter);
    }

    private void initializeTaskDialog() {
        if( getSupportFragmentManager().findFragmentByTag(TaskDialog.TAG_TASK_DIALOG) != null) {
            taskDialog = (TaskDialog) getSupportFragmentManager().findFragmentByTag(TaskDialog.TAG_TASK_DIALOG);
            taskDialog.setAllProjects(allProjects);
            taskDialog.setTaskActions(this);
        }
        else{
            taskDialog = new TaskDialog(this, allProjects);
        }
    }
    /**
     * Displays Task Dialog
     */
    private void showAddTaskDialog() {
        taskDialog.show(getSupportFragmentManager(), TaskDialog.TAG_TASK_DIALOG);
    }

    /**
     * TaskActions interface implementation : Adds the given task to the list of created tasks.
     * @param task : the task to be added to the list
     */
    @Override
    public void addTask(@NonNull Task task) {
        tasks.add(task);
        updateTasks();
    }

    /**
     * Updates the list of tasks in the UI
     */
    private void updateTasks() {
        if (tasks.size() == 0) {
            binding.lblNoTask.setVisibility(View.VISIBLE);
            binding.listTasks.setVisibility(View.GONE);
        }
        else {
            binding.lblNoTask.setVisibility(View.GONE);
            binding.listTasks.setVisibility(View.VISIBLE);
            switch (sortMethod) {
                case ALPHABETICAL:
                    Collections.sort(tasks, new Task.TaskAZComparator());
                    break;
                case ALPHABETICAL_INVERTED:
                    Collections.sort(tasks, new Task.TaskZAComparator());
                    break;
                case RECENT_FIRST:
                    Collections.sort(tasks, new Task.TaskRecentComparator());
                    break;
                case OLD_FIRST:
                    Collections.sort(tasks, new Task.TaskOldComparator());
                    break;
            }
            adapter.updateTasks(tasks);
        }
    }
}

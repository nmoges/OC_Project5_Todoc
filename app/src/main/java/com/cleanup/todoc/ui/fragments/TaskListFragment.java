package com.cleanup.todoc.ui.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.cleanup.todoc.R;
import com.cleanup.todoc.databinding.FragmentTaskListBinding;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.MainActivityCallback;
import com.cleanup.todoc.utils.SortMethod;
import com.cleanup.todoc.ui.dialogs.TaskDialog;
import java.util.ArrayList;
import java.util.Collections;

public class TaskListFragment extends Fragment implements TasksAdapter.DeleteTaskListener, TaskActions {

    private FragmentTaskListBinding binding;

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

    public TaskListFragment() { /* Empty constructor */ }

    public static TaskListFragment newInstance() {
        return new TaskListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTaskListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeToolbar();

        initializeRecyclerView();

        initializeTaskDialog();

        handleFab();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.actions, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_alphabetical) sortMethod = SortMethod.ALPHABETICAL;
        else if (id == R.id.filter_alphabetical_inverted) sortMethod = SortMethod.ALPHABETICAL_INVERTED;
        else if (id == R.id.filter_oldest_first) sortMethod = SortMethod.OLD_FIRST;
        else if (id == R.id.filter_recent_first) sortMethod = SortMethod.RECENT_FIRST;

        updateTasks();

        return super.onOptionsItemSelected(item);
    }

    public void initializeToolbar(){
        ((MainActivityCallback) requireActivity()).setToolbarTitle(R.string.app_name);
    }

    /**
     * TaskAdapter.DeleteTaskListener interface implementation
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
        binding.fabAddTask.setOnClickListener((View view) ->  showAddTaskDialog());
    }

    /**
     * Handles RecyclerView initialization to display Task list
     */
    private void initializeRecyclerView() {
        binding.listTasks.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.listTasks.setAdapter(adapter);
    }

    /**
     * Handles TaskDialog initialization
     */
    private void initializeTaskDialog() {
        if( getParentFragmentManager().findFragmentByTag(TaskDialog.TAG_TASK_DIALOG) != null) {
            Fragment fragment =  getParentFragmentManager().findFragmentByTag(TaskDialog.TAG_TASK_DIALOG);
            ((TaskDialog) fragment).setAllProjects(allProjects);
            ((TaskDialog) fragment).setTaskActions(this);
        }
        else {
            taskDialog = new TaskDialog(this, allProjects);
        }

    }

    /**
     * Displays Task Dialog
     */
    private void showAddTaskDialog() {
        taskDialog.show(getParentFragmentManager(), TaskDialog.TAG_TASK_DIALOG);
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
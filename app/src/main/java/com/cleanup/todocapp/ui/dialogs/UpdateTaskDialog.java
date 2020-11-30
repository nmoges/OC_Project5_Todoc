package com.cleanup.todocapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import com.cleanup.todocapp.R;
import com.cleanup.todocapp.model.Project;
import com.cleanup.todocapp.model.Task;
import com.cleanup.todocapp.ui.fragments.TaskActions;
import com.cleanup.todocapp.utils.SpinnerInitializer;
import java.util.List;
import java.util.Objects;

/**
 * <p>Dialog used to modify an existing</p>
 */
public class UpdateTaskDialog extends DialogFragment {

    /**
     * UpdateTaskDialog TAG
     */
    public final static String TAG_UPDATE_TASK_DIALOG = "TAG_UPDATE_TASK_DIALOG";

    /**
     * UpdateTaskDialog EditText
     */
    private EditText dialogEditText;

    /**
     * UpdateTaskDialog Spinner
     */
    private Spinner dialogSpinner;

    /**
     * Task clicked by user, which need to be updated
     */
    private Task taskToUpdate;

    /**
     * TaskActions interface
     */
    private TaskActions taskActions = null;

    /**
     * List of all Projects
     */
    private List<Project> allProjects;

    /**
     * Selected item index in Spinner
     */
    private int item_selected = 0;

    public UpdateTaskDialog() {/* Empty constructor */}

    public UpdateTaskDialog(TaskActions taskActions, Task taskToUpdate, List<Project> allProjects) {
        this.taskActions = taskActions;
        this.taskToUpdate = taskToUpdate;
        this.allProjects = allProjects;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState != null) {
            item_selected = savedInstanceState.getInt("item_selected");
        }

        // Retain current DialogFragment instance
        setRetainInstance(true);

        final AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity(), R.style.Dialog);

        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        alertBuilder.setView(inflater.inflate(R.layout.dialog_update_task, null))
                    .setTitle(R.string.update_task)
                .setPositiveButton(R.string.ok, (DialogInterface dialog, int which) -> {
                        taskToUpdate.setName(dialogEditText.getText().toString());
                        taskToUpdate.setProjectId(dialogSpinner.getSelectedItemPosition()+1);
                        taskActions.onUpdateTask(taskToUpdate);
                    }
                )
                .setNegativeButton(R.string.cancel, (DialogInterface dialog, int which) -> {
                        dialogEditText = null;
                        dialogSpinner = null;
                    }
                );

        return alertBuilder.create();
    }


    @Override
    public void onResume() {
        super.onResume();
        initializeViews();
        populateDialogSpinner();
    }

    public void initializeViews() {

        dialogEditText = Objects.requireNonNull(getDialog()).findViewById(R.id.txt_update_task_name);
        dialogSpinner = Objects.requireNonNull(getDialog()).findViewById(R.id.update_project_spinner);
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    public void populateDialogSpinner() {

        SpinnerInitializer.populateDialogSpinner(getContext(), dialogSpinner, allProjects, item_selected);

        dialogEditText.setText(taskToUpdate.getName());
        dialogSpinner.setSelection(taskToUpdate.getProjectId()-1);
    }

    /**
     * Save item selection on Spinner
     * @param outState : Bundle
     */
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("item_selected", dialogSpinner.getSelectedItemPosition());
    }
}

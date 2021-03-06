package com.cleanup.todocapp.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.Spinner;
import com.cleanup.todocapp.R;
import com.cleanup.todocapp.model.Project;
import com.cleanup.todocapp.model.Task;
import com.cleanup.todocapp.ui.fragments.TaskActions;
import com.cleanup.todocapp.utils.SpinnerInitializer;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>Dialog used to create a new Task</p>
 */
public class TaskDialog extends DialogFragment {

    /**
     * TaskDialog TAG
     */
    public final static String TAG_TASK_DIALOG = "TAG_TASK_DIALOG";

    /**
     * TaskDialog EditText
     */
    private EditText dialogEditText;

    /**
     * TaskDialog Spinner
     */
    private Spinner dialogSpinner;

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


    public TaskDialog() {/* Empty constructor */}

    public TaskDialog(TaskActions taskActions, List<Project> allProjects) {

        this.taskActions = taskActions;
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

        alertBuilder.setView(inflater.inflate(R.layout.dialog_add_task, null))
                    .setTitle(R.string.add_task)
                    .setPositiveButton(R.string.add, (DialogInterface dialog, int which) ->  onPositiveButtonClick(dialog))
                    .setOnDismissListener((DialogInterface dialog) -> {
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

        dialogEditText = Objects.requireNonNull(getDialog()).findViewById(R.id.txt_task_name);
        dialogSpinner = Objects.requireNonNull(getDialog()).findViewById(R.id.project_spinner);
    }

    /**
     * Defines action on DialogFragment positive button
     * @param dialogInterface : DialogInterface with which user interact
     */
    public void onPositiveButtonClick(DialogInterface dialogInterface) {

        // If dialog is open
        if (dialogEditText != null && dialogSpinner != null) {
            // Get the name of the task
            String taskName = dialogEditText.getText().toString();

            // Get the selected project to be associated to the task
            Project taskProject = null;
            if(dialogSpinner.getSelectedItem() instanceof Project) {
                taskProject = (Project) dialogSpinner.getSelectedItem();
            }

            // If a name has not been set
            if (taskName.trim().isEmpty()) {
                dialogEditText.setError(getString(R.string.empty_task_name));
            }

            // If both project and name of the task have been set
            else if (taskProject != null) {
                Task task = new Task(taskProject.getId(), taskName, new Date().getTime());

                taskActions.onAddTask(task);
                dialogInterface.dismiss();
            }
            // If name has been set, but project has not been set (this should never occur)
            else {
                dialogInterface.dismiss();
            }
        }
        // If dialog is already closed
        else {
            dialogInterface.dismiss();
        }
    }


    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    public void populateDialogSpinner() {

        SpinnerInitializer.populateDialogSpinner(getContext(), dialogSpinner, allProjects, item_selected);
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

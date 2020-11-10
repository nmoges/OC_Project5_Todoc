package com.cleanup.todoc.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import com.cleanup.todoc.R;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.ui.fragments.TaskActions;
import java.util.Date;

public class TaskDialog extends DialogFragment {

    public final static String TAG_TASK_DIALOG = "TAG_TASK_DIALOG";

    private EditText dialogEditText;
    private Spinner dialogSpinner;
    private AlertDialog dialog = null;
    private TaskActions taskActions = null;

    private Project[] allProjects;

    public TaskDialog() {/* Empty constructor */}

    public TaskDialog(TaskActions taskActions, Project[] allProjects) {

        this.taskActions = taskActions;
        this.allProjects = allProjects;
    }

    public void setTaskActions(TaskActions taskActions) {

        this.taskActions = taskActions;
    }

    public void setAllProjects(Project[] allProjects) {

        this.allProjects = allProjects;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

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
                            //  dialog = null;
                        }
                    );

        dialog = alertBuilder.create();
        return dialog;
    }

    @Override
    public void onResume() {

        super.onResume();
        initializeViews();
        populateDialogSpinner();
    }

    public void initializeViews() {

        dialogEditText = getDialog().findViewById(R.id.txt_task_name);
        dialogSpinner = getDialog().findViewById(R.id.project_spinner);
    }


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
                // TODO(): Replace this by id of persisted task
                long id = (long) (Math.random() * 50000);

                Task task = new Task(id, taskProject.getId(), taskName, new Date().getTime());

                taskActions.addTask(task);
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

    public Dialog getDialog() {

        return this.dialog;
    }

    /**
     * Sets the data of the Spinner with projects to associate to a new task
     */
    public void populateDialogSpinner() {

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
        }
    }

}

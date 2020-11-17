package com.cleanup.todoc.utils;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import com.cleanup.todoc.model.Project;
import java.util.List;

/**
 * <p>Sets the data of the Spinner with projects to associate to a new task</p>
 */
public class SpinnerInitializer {

    public static void populateDialogSpinner(Context context, Spinner dialogSpinner, List<Project> allProjects, int selection) {

        final ArrayAdapter<Project> adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, allProjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if (dialogSpinner != null) {
            dialogSpinner.setAdapter(adapter);
            dialogSpinner.setSelection(selection, false);
        }
    }
}

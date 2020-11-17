package com.cleanup.todoc.ui.fragments;

import androidx.annotation.NonNull;
import com.cleanup.todoc.model.Task;

/**
 * <p>Interface used to define actions methods on list of Tasks</p>
 */
public interface TaskActions {
    void onAddTask(@NonNull Task task);
    void onDeleteTask(@NonNull Task task);
    void onOpenDialogForUpdateTask(@NonNull Task task);
    void onUpdateTask(@NonNull Task task);
}

package com.cleanup.todoc.ui.fragments;

import androidx.annotation.NonNull;
import com.cleanup.todoc.model.Task;

public interface TaskActions {
    void onAddTask(@NonNull Task task);
    void onDeleteTask(@NonNull Task task);
    void onOpenDialogForUpdateTask(@NonNull Task task);
    void onUpdateTask(@NonNull Task task);
}

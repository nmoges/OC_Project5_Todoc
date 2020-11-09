package com.cleanup.todoc.ui;

import androidx.annotation.NonNull;
import com.cleanup.todoc.model.Task;

public interface TaskActions {
    void addTask(@NonNull Task task);
}

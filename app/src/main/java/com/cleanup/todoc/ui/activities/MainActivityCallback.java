package com.cleanup.todoc.ui.activities;

import androidx.annotation.StringRes;

/**
 * <P>Interface used by TaskListFragment to update MainActivity Toolbar</P>
 */
public interface MainActivityCallback {
    void setToolbarTitle(@StringRes int title);
}

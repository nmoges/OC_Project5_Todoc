package com.cleanup.todocapp.testutils;

import android.view.View;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import com.cleanup.todocapp.R;
import org.hamcrest.Matcher;

public class DeleteViewAction implements ViewAction {
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    @Override
    public String getDescription() {
        return "Click on specific button";
    }

    @Override
    public void perform(UiController uiController, View view) {

        View button = view.findViewById(R.id.img_delete);

        if(button != null){
            button.performClick();
        }
    }
}

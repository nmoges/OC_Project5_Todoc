package com.cleanup.todoc;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.cleanup.todoc.database.TodocDatabase;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.repositories.ProjectRepository;
import com.cleanup.todoc.testutils.DeleteViewAction;
import com.cleanup.todoc.ui.activities.MainActivity;
import com.cleanup.todoc.viewmodel.ListTasksViewModel;
import com.cleanup.todoc.viewmodel.ViewModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.TestUtils.withRecyclerView;
import static com.cleanup.todoc.testutils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
public class ListTasksViewModelTest {

    private ListTasksViewModel listTasksViewModel;
    private MainActivity mainActivity;


    @Rule
    public final ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void initDatabase() {
        mainActivity = mMainActivityRule.getActivity();

        // Initialize database
        TodocDatabase database = Room.inMemoryDatabaseBuilder(mainActivity, TodocDatabase.class).build();

        // Initiates a Factory to create ViewModel instances
        ViewModelFactory factory = new ViewModelFactory(mainActivity);
        listTasksViewModel = factory.create(ListTasksViewModel.class);

        // Initialize Repositories
        ProjectRepository projectRepository = new ProjectRepository(database.projectDao());

        // Initialize parent table in database
        Project[] projects = DI.provideProjects(mainActivity);

        for(Project project : projects) {
            projectRepository.insertProject(project);
        }
    }


    @After
    public void cleanDatabase() {
        // Clean task_table for next test
        listTasksViewModel.deleteAllTasks();
    }

    /**
     * This test adds a new Task to the list through the following steps :
     *      - perform click on Floating Action Button
     *      - perform click on EditText from TaskDialog
     *      - write text on EditText
     *      - close software keyboard
     *      - perform click on TaskDialog positive button
     *      - check if RecyclerView is updated
     */
    @Test
    public void test_add_new_task_to_list() {

        // Perform click on Floating Action Button
        onView(withId(R.id.fab_add_task))
                .perform(click());

        // Perform click on Edit Text to enable focus
        onView(withId(R.id.txt_task_name))
                .perform(click());

        // Write text
        onView(withId(R.id.txt_task_name))
                .perform(typeText("Validation software"));

        // Close Software Keybord
        closeSoftKeyboard();

        // Close Dialog by clicking on Positive Button
        onView(withText("Ajouter"))
                .perform(click());

        // Check if RecycleView is updated
        onView(withId(R.id.list_tasks))
                .check(withItemCount(1));
    }

    /**
     * This test removes Task from list through the following steps :
     *      - add three Tasks using TaskDialog
     *      - check number of Tasks in RecyclerView
     *      - remove second Task from list
     *      - check new number of Tasks in RecyclerView
     */
    @Test
    public void test_remove_task_from_list() {

        for(int i = 0; i < 3; i++) {
            // Perform click on Floating Action Button
            onView(withId(R.id.fab_add_task))
                    .perform(click());

            // Perform click on Edit Text to enable focus
            onView(withId(R.id.txt_task_name))
                    .perform(click());

            // Write text
            if(i == 0) onView(withId(R.id.txt_task_name)).perform(typeText("Tache 1"));
            else if(i == 1) onView(withId(R.id.txt_task_name)).perform(typeText("Tache 2"));
            else onView(withId(R.id.txt_task_name)).perform(typeText("Tache 3"));

            // Close Software Keybord
            closeSoftKeyboard();

            // Close Dialog by clicking on Positive Button
            onView(withText("Ajouter"))
                    .perform(click());
        }

        // Check if RecycleView is updated
        onView(withId(R.id.list_tasks))
                .check(withItemCount(3));

        // Remove second item
        onView(withId(R.id.list_tasks))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Check if list of Tasks has changed
        onView(withId(R.id.list_tasks))
                .check(withItemCount(2));
    }


    /**
     * This test updates an existing Task in list through the following steps :
     *      - add a new Task in list using TaskDialog
     *      - click on existing Task display in RecyclerView
     *      - update EditText field from UpdateTaskDialog
     *      - close Dialog
     *
     */
    @Test
    public void test_update_existing_task() {

        // Perform click on Floating Action Button
        onView(withId(R.id.fab_add_task))
                .perform(click());

        // Perform click on Edit Text to enable focus
        onView(withId(R.id.txt_task_name))
                .perform(click());

        // Write text
        onView(withId(R.id.txt_task_name)).perform(typeText("Test"));

        // Close Software Keyboard
        closeSoftKeyboard();

        // Close Dialog by clicking on Positive Button
        onView(withText("Ajouter"))
                .perform(click());

        // Click on item
        onView(withId(R.id.list_tasks))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        // Perform click on Edit Text to enable focus
        onView(withId(R.id.txt_update_task_name))
                .perform(click());

        // Write text
        onView(withId(R.id.txt_update_task_name)).perform(typeText(" unitaire"));

        // Close Dialog by clicking on Positive Button
        onView(withText("OK"))
                .perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Test unitaire")));
    }

    /**
     * This test :
     *      - creates a new Task using TaskDialog
     *      - confirm Task creation by validating with TaskDialog positive button
     *      - removes Task from RecyclerView using its delete item
     *      - checks if SnackBar is displayed
     *      - clicks on action button from SnackBar to restore Task
     *      - checks if Task is restored in RecyclerView
     */
    @Test
    public void test_restore_task_with_snackbar_action() {
        // Perform click on Floating Action Button
        onView(withId(R.id.fab_add_task))
                .perform(click());

        // Perform click on Edit Text to enable focus
        onView(withId(R.id.txt_task_name))
                .perform(click());

        // Write text
        onView(withId(R.id.txt_task_name)).perform(typeText("Validation software"));

        // Close Software Keybord
        closeSoftKeyboard();

        // Close Dialog by clicking on Positive Button
        onView(withText("Ajouter"))
                .perform(click());

        // Click on delete icon
        onView(withId(R.id.list_tasks))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));

        // Check if SnackBar is displayed
        onView(withId(com.google.android.material.R.id.snackbar_text))
                .check(matches(isDisplayed()));

        // Click on SnackBar button to restore Task
        onView(withId(com.google.android.material.R.id.snackbar_action))
                .perform(click());

        // Check if list of Tasks contains the restored Task
        onView(withId(R.id.list_tasks))
                .check(withItemCount(1));

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("Validation software")));

        // Clean task_table for next test
        listTasksViewModel.deleteAllTasks();
    }

    /**
     * This test :
     *      - checks if background elements are displayed when list of Task is empty
     *      - adds a Task to the list
     *      - checks if background elements status display is updated
     *      - removes Task
     *      - checks if background elements status display is updated
     */
    @Test
    public void check_background_update() {

        TextView lblNoTask = mainActivity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = mainActivity.findViewById(R.id.list_tasks);

        // Check if background elements are initially visible
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));

        // Perform click on Floating Action Button
        onView(withId(R.id.fab_add_task))
                .perform(click());

        // Perform click on Edit Text to enable focus
        onView(withId(R.id.txt_task_name))
                .perform(click());

        // Write text
        onView(withId(R.id.txt_task_name))
                .perform(typeText("Validation software"));

        // Close Software Keybord
        closeSoftKeyboard();

        // Close Dialog by clicking on Positive Button
        onView(withText("Ajouter"))
                .perform(click());

        // Check if background elements are now gone
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));

        // Remove item
        onView(withId(R.id.img_delete)).perform(click());

        // Check if background elements are visible again
        assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
        assertThat(listTasks.getVisibility(), equalTo(View.GONE));
    }
}

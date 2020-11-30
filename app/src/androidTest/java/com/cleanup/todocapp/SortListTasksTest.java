package com.cleanup.todocapp;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import com.cleanup.todocapp.database.TodocDatabase;
import com.cleanup.todocapp.di.DI;
import com.cleanup.todocapp.model.Project;
import com.cleanup.todocapp.repositories.ProjectRepository;
import com.cleanup.todocapp.ui.activities.MainActivity;
import com.cleanup.todocapp.viewmodel.ListTasksViewModel;
import com.cleanup.todocapp.viewmodel.ViewModelFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isPlatformPopup;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todocapp.testutils.Utils.withRecyclerView;
import static org.hamcrest.Matchers.containsString;


/**
 * This file tests all sort methods for list of Tasks
 */
@RunWith(AndroidJUnit4.class)
public class SortListTasksTest {

    @Rule
    public final ActivityTestRule<MainActivity> mMainActivityRule = new ActivityTestRule<>(MainActivity.class);

    private ListTasksViewModel listTasksViewModel;

    private MainActivity mainActivity;

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
     * This test :
     *      - creates three Tasks
     *      - checks Sort alphabetical method
     *      - checks Sort alphabetical inverted method
     *      - checks Sort old first method
     *      - checks Sort recent first method
     *      - modifies Project items
     *      - checks Sort Project method
     */
    @Test
    public void check_if_sortTasks_methods_works() {

        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        // Modify Project items
        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(0, R.id.lbl_task_name))
                .perform(click());
        onView(withId(R.id.update_project_spinner))
                .perform(click());
        onView(withText(containsString(mainActivity.getResources().getString(R.string.project_circus))))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withText(mainActivity.getResources().getString(R.string.ok)))
                .perform(click());

        onView(withRecyclerView(R.id.list_tasks)
                .atPositionOnView(2, R.id.lbl_task_name))
                .perform(click());
        onView(withId(R.id.update_project_spinner))
                .perform(click());
        onView(withText(containsString(mainActivity.getResources().getString(R.string.project_lucidia))))
                .inRoot(isPlatformPopup())
                .perform(click());
        onView(withText(mainActivity.getResources().getString(R.string.ok)))
                .perform(click());

        // Sort by project
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_projects)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(0, R.id.lbl_project_name))
                .check(matches(withText(mainActivity.getResources().getString(R.string.project_circus))));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(1, R.id.lbl_project_name))
                .check(matches(withText(mainActivity.getResources().getString(R.string.project_lucidia))));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(2, R.id.lbl_project_name))
                .check(matches(withText(mainActivity.getResources().getString(R.string.project_tartampion))));
    }

}

package com.cleanup.todoc;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.cleanup.todoc.viewmodel.ListProjectsViewModel;
import com.cleanup.todoc.viewmodel.ListTasksViewModel;
import com.cleanup.todoc.viewmodel.ViewModelFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertNotNull;

/**
 * This file test ViewModelFactory class to ensure that this class is correctly instantiated,
 * and correctly generated ViewModel classes.
 */
@RunWith(AndroidJUnit4.class)
public class ViewModelFactoryTest {

    /**
     * This test :
     *  - instantiates a ViewModelFactory class
     *  - checks if instance is not null
     *  - uses ViewModelFactory intance to generate both ViewModel class instances
     *  - checks if both ViewModel instanecs are not null
     */
    @Test
    public void test_check_if_viewmodelfactory_generate_viewmodel_instances() {
        Context context = ApplicationProvider.getApplicationContext();

        ViewModelFactory factory = new ViewModelFactory(context);

        assertNotNull(factory);

        ListTasksViewModel listTasksViewModel = factory.create(ListTasksViewModel.class);
        ListProjectsViewModel listProjectsViewModel = factory.create(ListProjectsViewModel.class);

        assertNotNull(listTasksViewModel);
        assertNotNull(listProjectsViewModel);

    }
}

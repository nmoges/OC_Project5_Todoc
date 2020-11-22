package com.cleanup.todoc;

import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import com.cleanup.todoc.utils.TaskComparators;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

/**
 * <p> Unit tests for tasks
 *     This file tests all operations on list of Tasks
 * </p>
 * @author Gaëtan HERFRAY (update : Nicolas MOGES)
 */
@RunWith(JUnit4.class)
public class TaskUnitTest {

    /**
     * This test :
     *      - initializes 4 Task objects
     *      - check values of each item
     */
    @Test
    public void test_task_objects() {
        final Task task1 = new Task(1, "task 1", new Date().getTime());
        final Task task2 = new Task( 2, "task 2", new Date().getTime());
        final Task task3 = new Task(3, "task 3", new Date().getTime());
        final Task task4 = new Task( 4, "task 4", new Date().getTime());

        // Compare Id
        assertEquals(1, task1.getProjectId());
        assertEquals(2, task2.getProjectId());
        assertEquals(3, task3.getProjectId());
        assertEquals(4, task4.getProjectId());

        // Compare Name
        assertEquals("task 1", task1.getName());
        assertEquals("task 2", task2.getName());
        assertEquals("task 3", task3.getName());
        assertEquals("task 4", task4.getName());
    }

    /**
     * This test :
     *      - initializes 3 Task objects
     *      - initializes an ArrayList containing theses Tasks
     *      - sort the ArrayList using the TaskAZComparator method from TaskComparators class
     *      - check if sort is correctly done
     */
    @Test
    public void test_az_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2,  "zzz", 124);
        final Task task3 = new Task(3,  "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskComparators.TaskAZComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task2);
    }

    /**
     * This test :
     *      - initializes 3 Task objects
     *      - initializes an ArrayList containing theses Tasks
     *      - sort the ArrayList using the TaskZAComparator method from TaskComparators class
     *      - check if sort is correctly done
     */
    @Test
    public void test_za_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task( 2, "zzz", 124);
        final Task task3 = new Task( 3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskComparators.TaskZAComparator());

        assertSame(tasks.get(0), task2);
        assertSame(tasks.get(1), task3);
        assertSame(tasks.get(2), task1);
    }

    /**
     * This test :
     *      - initializes 3 Task objects
     *      - initializes an ArrayList containing theses Tasks
     *      - sort the ArrayList using the TaskRecentComparator method from TaskComparators class
     *      - check if sort is correctly done
     */
    @Test
    public void test_recent_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskComparators.TaskRecentComparator());

        assertSame(tasks.get(0), task3);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task1);
    }

    /**
     * This test :
     *      - initializes 3 Task objects
     *      - initializes an ArrayList containing theses Tasks
     *      - sort the ArrayList using the TaskOldComparator method from TaskComparators class
     *      - check if sort is correctly done
     */
    @Test
    public void test_old_comparator() {
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(2, "zzz", 124);
        final Task task3 = new Task(3, "hhh", 125);

        final ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        Collections.sort(tasks, new TaskComparators.TaskOldComparator());

        assertSame(tasks.get(0), task1);
        assertSame(tasks.get(1), task2);
        assertSame(tasks.get(2), task3);
    }

    /**
     * This test :
     *      - initializes 8 Task objects
     *      - initializes an ArrayList containing theses Tasks
     *      - sort the ArrayList using the ProjectComparator method from TaskComparators class
     *      - check if sort is correctly done
     */
    @Test
    public void test_project_comparator() {

        // Init list projects
        ArrayList<Project> projects = new ArrayList<>();
        projects.add(new Project("Projet Tartampion", 0xFFEADAD1));
        projects.add(new Project("Projet Lucidia", 0xFFB4CDBA));
        projects.add(new Project("Projet Circus", 0xFFA3CED2));

        // Init list tasks
        final ArrayList<Task> tasks = new ArrayList<>();
        final Task task1 = new Task(1, "aaa", 123);
        final Task task2 = new Task(1, "bbb", 124);
        final Task task3 = new Task(2, "kkk", 125);
        final Task task4 = new Task(1, "ccc", 126);
        final Task task5 = new Task(3, "ppp", 127);
        final Task task6 = new Task(2, "mmm", 128);
        final Task task7 = new Task(2, "ooo", 129);
        final Task task8 = new Task(3, "vvv", 130);
        tasks.add(task1);
        tasks.add(task2);
        tasks.add(task3);
        tasks.add(task4);
        tasks.add(task5);
        tasks.add(task6);
        tasks.add(task7);
        tasks.add(task8);

        // Sort list tasks
        TaskComparators.ProjectComparator comparator = new TaskComparators.ProjectComparator(projects);

        Collections.sort(tasks, comparator);
        assertEquals(tasks.get(0).getProjectId(), 3); // Task 5 : Project Circus
        assertEquals(tasks.get(1).getProjectId(), 3); // Task 8 : Project Circus
        assertEquals(tasks.get(2).getProjectId(), 2); // Task 3 : Project Lucidia
        assertEquals(tasks.get(3).getProjectId(), 2); // Task 6 : Project Lucidia
        assertEquals(tasks.get(4).getProjectId(), 2); // Task 7 : Project Lucidia
        assertEquals(tasks.get(5).getProjectId(), 1); // Task 1 : Project Tartampion
        assertEquals(tasks.get(6).getProjectId(), 1); // Task 2 : Project Tartampion
        assertEquals(tasks.get(7).getProjectId(), 1); // Task 4 : Project Tartampion
    }
}
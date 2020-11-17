package com.cleanup.todoc.utils;

import android.content.Context;
import com.cleanup.todoc.di.DI;
import com.cleanup.todoc.model.Project;
import com.cleanup.todoc.model.Task;
import java.util.Comparator;

/**
 * <p>Container for all sort methods</p>
 */
public class TaskComparators {

    /**
     * Comparator to sort task from A to Z
     */
    public static class TaskAZComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return left.getName().toUpperCase().compareTo(right.getName().toUpperCase());
        }
    }

    /**
     * Comparator to sort task from Z to A
     */
    public static class TaskZAComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return right.getName().toUpperCase().compareTo(left.getName().toUpperCase());
        }
    }

    /**
     * Comparator to sort tasks according to their project
     */
    public static class ProjectComparator implements Comparator<Task> {

        private final Context context;

        public ProjectComparator(Context context) {
            this.context = context;
        }

        @Override
        public int compare(Task left, Task right) {

            Project[] projects  = DI.providesProjects(context);
            String leftProjectName = null;
            String rightProjectName = null;

            // Get Left Project
            leftProjectName = getNameProject(left.getProjectId(), projects);

            // Get Right Project
            rightProjectName = getNameProject(right.getProjectId(), projects);

            // Comparison result
            return leftProjectName.toUpperCase().compareTo(rightProjectName.toUpperCase());
        }

        /**
         * Method to return associated project in table projects[] according to its id
         * @param idProject : id of the project to search
         * @param projects : table of existing projects
         * @return : project associated to the id
         */
        public String getNameProject(int idProject, Project[] projects) {
            String nameProject = null;
            switch (idProject) {
                case 1:
                    nameProject = projects[0].getName();
                    break;
                case 2:
                    nameProject = projects[1].getName();
                    break;
                case 3:
                    nameProject = projects[2].getName();
                    break;
            }
            return nameProject;
        }
    }
    /**
     * Comparator to sort task from last created to first created
     */
    public static class TaskRecentComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (right.getCreationTimestamp() - left.getCreationTimestamp());
        }
    }

    /**
     * Comparator to sort task from first created to last created
     */
    public static class TaskOldComparator implements Comparator<Task> {
        @Override
        public int compare(Task left, Task right) {
            return (int) (left.getCreationTimestamp() - right.getCreationTimestamp());
        }
    }

}

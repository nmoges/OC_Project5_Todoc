package com.cleanup.todocapp.ui.fragments;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.cleanup.todocapp.R;
import com.cleanup.todocapp.model.Project;
import com.cleanup.todocapp.model.Task;
import java.util.List;

/**
 * <p>Adapter which handles the list of tasks to display in the dedicated RecyclerView.</p>
 *
 * @author Gaëtan HERFRAY (update : Nicolas MOGES)
 */
public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TaskViewHolder> {

    /**
     * The list of tasks the adapter deals with
     */
    @NonNull
    private List<Task> tasks;

    /**
     * The listener for when a task needs to be deleted
     */
    @NonNull
    private final TaskActions taskActions;

    @NonNull
    private List<Project> projects;
    /**
     * Instantiates a new TasksAdapter.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    public TasksAdapter(@NonNull final List<Task> tasks, @NonNull final List<Project> projects, @NonNull final TaskActions taskActions) {

        this.tasks = tasks;
        this.projects = projects;
        this.taskActions = taskActions;
    }

    /**
     * Updates the list of tasks the adapter deals with.
     *
     * @param tasks the list of tasks the adapter deals with to set
     */
    public void updateTasks(@NonNull final List<Task> tasks) {

        this.tasks = tasks;
        notifyDataSetChanged();
    }

    public void updateProjects(@NonNull final List<Project> projects) {

        this.projects = projects;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_task, viewGroup, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int position) {

        taskViewHolder.bind(tasks.get(position));
    }

    @Override
    public int getItemCount() {

        return tasks.size();
    }

    /**
     * <p>ViewHolder for task items in the tasks list</p>
     *
     * @author Gaëtan HERFRAY
     */
    class TaskViewHolder extends RecyclerView.ViewHolder {

        /**
         * The circle icon showing the color of the project
         */
        private final AppCompatImageView imgProject;

        /**
         * The TextView displaying the name of the task
         */
        private final TextView lblTaskName;

        /**
         * The TextView displaying the name of the project
         */
        private final TextView lblProjectName;

        /**
         * The delete icon
         */
        private final AppCompatImageView imgDelete;

        /**
         * The clickable item layout
         */
        private final RelativeLayout itemLayout;
        /**
         * Instantiates a new TaskViewHolder.
         *
         * @param itemView : the view of the task item
         */
        TaskViewHolder(@NonNull View itemView) {

            super(itemView);

            imgProject = itemView.findViewById(R.id.img_project);
            lblTaskName = itemView.findViewById(R.id.lbl_task_name);
            lblProjectName = itemView.findViewById(R.id.lbl_project_name);
            imgDelete = itemView.findViewById(R.id.img_delete);
            itemLayout = itemView.findViewById(R.id.item_layout);
        }

        /**
         * Binds a task to the item view.
         *
         * @param task : the task to bind in the item view
         */
        void bind(Task task) {

            lblTaskName.setText(task.getName());
            imgDelete.setTag(task);

            final Project taskProject = getAssociatedProject(task.getProjectId());

            if (taskProject != null) {
                imgProject.setSupportImageTintList(ColorStateList.valueOf(taskProject.getColor()));
                lblProjectName.setText(taskProject.getName());
            } else {
                imgProject.setVisibility(View.INVISIBLE);
                lblProjectName.setText("");
            }

            // Listeners
            imgDelete.setOnClickListener((View v) -> taskActions.onDeleteTask(task));

            itemLayout.setOnClickListener((View v) -> taskActions.onOpenDialogForUpdateTask(task));
        }

        /**
         * Get Project of an associated Task, by searching in Projects ArrayList using its id
         * @param idProject : id of the Project to search
         * @return : Associated Project
         */
        private Project getAssociatedProject(long idProject) {

            boolean found = false;
            int indice = 0;
            Project project = null;

            while (!found && indice < projects.size()) {
                if (projects.get(indice).getId() == idProject) {
                    found = true;
                    project = projects.get(indice);
                }
                else {
                    indice++;
                }
            }
            return project;
        }
    }
}

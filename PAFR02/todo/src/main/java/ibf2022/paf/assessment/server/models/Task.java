package ibf2022.paf.assessment.server.models;

import java.time.LocalDate;

// Task 4
public class Task {

    private String task_id;
    private String description;
    private String priority;
    private LocalDate dueDate;

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Task() {
    }

    public Task(String description, String priority, LocalDate dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        return "Task [task_id=" + task_id + ", description=" + description + ", priority=" + priority + ", dueDate="
                + dueDate + "]";
    }

}

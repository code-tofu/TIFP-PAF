package ibf2022.paf.assessment.server.models;

import java.time.LocalDate;

// Task 4
public class Task {

    private int task_id;
    private String description;
    private int priority;
    private LocalDate dueDate;

    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Task() {
    }

    @Override
    public String toString() {
        return "Task [task_id=" + task_id + ", description=" + description + ", priority=" + priority + ", dueDate="
                + dueDate + "]";
    }

    public Task(String description, int priority, LocalDate dueDate) {
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
    }

}

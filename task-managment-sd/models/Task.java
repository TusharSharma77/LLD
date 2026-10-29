import java.time.LocalDateTime;
import java.util.UUID;

public class Task {
    private final String taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
    private String assignedTo;

    public Task(String title, String description, TaskStatus status, Priority priority, LocalDateTime dueDate) {
        this.taskId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }

    public Task(String title, String description, Priority priority) {
        this(title, description, TaskStatus.TODO, priority, null);
    }

    // getters and setters
    public String getTaskId() {
        return taskId;
    }

    public String getId() {
        return taskId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public Priority getPriority() {
        return priority;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public String getAssignedTo() {
        return assignedTo;
    }

    public void setAssignedTo(String assignedTo) {
        this.assignedTo = assignedTo;
    }

    public void setStatus(TaskStatus status) {
        if (this.status == TaskStatus.DONE) {
            throw new IllegalStateException("Cannot change status of a completed task.");
        }
        this.status = status;
    }
}

public class Task {
    private final String taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDateTime dueDate;
    private String assignedTo;

    public Task(String title, String description, TaskStatus status, Priority priority, LocalDateTime dueDate){
        this.taskId = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.dueDate = dueDate;
    }
    // getter
    public String getTaskId(){
        return taskId;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public TaskStatus getStatus(){
        return status;
    }
    public Priority getPriority(){
        return priority;
    }
    public LocalDateTime getDueDate(){
        return dueDate;
    }
    public String getAssignedTo(){
        return assignedTo;
    }

    public void setStatus(TaskStatus status){
        if(this.status == TaskStatus.DONE){
            throw new IllegalStateException("Cannot change status of a completed task.");
        }
        this.status = status;
    }
}
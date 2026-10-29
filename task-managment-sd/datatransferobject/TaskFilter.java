
public class TaskFilter{

    private TaskStatus status;
    private String assignedTo;
    private Priority priority;

    private TaskFilter(){
        // private constructor to prevent direct instantiation
    }
    pubblic TaskStatus getStatus(){
        return status;
    }
    public String getAssignedTo(){
        return assignedTo;
    }
    public Priority getPriority(){
        return priority;
    }
  // builder pattern for compose any subset of filters
    public static class Builder{
   
   public Builder setStatus(TaskStatus status){
            this.status = status;
            return this;
        }
        public Builder setAssignedTo(String assignedTo){
            this.assignedTo = assignedTo;
            return this;
        }
        public Builder setPriority(Priority priority){
            this.priority = priority;
            return this;
        }
        public TaskFilter build(){
            TaskFilter filter = new TaskFilter();
            filter.status = this.status;
            filter.assignedTo = this.assignedTo;
            filter.priority = this.priority;
            return filter;
        }
    }
}
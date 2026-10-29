

public interface TaskObserver {
    void onTaskEvent(TaskEventType eventType, Task task);
}
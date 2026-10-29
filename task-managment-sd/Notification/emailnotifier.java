

public class EmailNotifier implements TaskObserver {
    @Override
    public void onTaskEvent(TaskEventType eventType, Task task) {
        // Implement email notification logic here
        System.out.println("Email Notification: Task " + task.getId() + " has event " + eventType);
    }
}
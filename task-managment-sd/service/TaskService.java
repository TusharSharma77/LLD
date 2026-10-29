public class TaskService {
    private final TaskRepo taskRepo;
    private final UserRepo userRepo;
    private final NotificationService notificationService;

    public TaskService(TaskRepo taskRepo, UserRepo userRepo, NotificationService notificationService) {
        this.taskRepo = taskRepo;
        this.userRepo = userRepo;
        this.notificationService = notificationService;
    }

    public Task createTask(String title, String description, Priority priority) {
        Task task = new Task(title, description, priority);
        taskRepo.save(task);
        notificationService.notifyObservers(TaskEventType.CREATED, task);
        return task;
    }

    public Task assignTask(String taskId, String userId) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        task.setAssignedTo(user.getUserId());
        taskRepo.save(task);
        notificationService.notifyObservers(TaskEventType.ASSIGNED, task);
        return task;
    }

    public Task updateTaskStatus(String taskId, TaskStatus status) {
        Task task = taskRepo.findById(taskId).orElseThrow(() -> new IllegalArgumentException("Task not found"));
        task.setStatus(status);
        taskRepo.save(task);
        notificationService.notifyObservers(TaskEventType.STATUS_CHANGED, task);
        return task;
    }
}

import java.util.Optional;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

public class ImplementTaskRepo implements TaskRepo {
    private final Map<String, Task> tasks = new ConcurrentHashMap<>();

    @Override
    public void save(Task task) {
        tasks.put(task.getTaskId(), task);
    }

    @Override
    public Optional<Task> findById(String taskId) {
        return Optional.ofNullable(tasks.get(taskId));
    }

    @Override
    public List<Task> findAll(TaskFilter filter) {
        return tasks.values().stream()
                .filter(task -> filter.getStatus() == null || task.getStatus() == filter.getStatus())
                .filter(task -> filter.getPriority() == null || task.getPriority() == filter.getPriority())
                .filter(task -> filter.getAssignedTo() == null || (task.getAssignedTo() != null && task.getAssignedTo().equals(filter.getAssignedTo())))
                .toList();
    }

    @Override
    public void deletebyId(String taskId) {
        tasks.remove(taskId);
    }
}

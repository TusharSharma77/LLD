
import java.util.Optional;
import java.util.List;
public interface TaskRepo {
    void save(Task task);
    Optional<Task> findById(String taskId);
    List<Task> findAll(TaskFilter filter);
    void deletebyId(String taskId);
}
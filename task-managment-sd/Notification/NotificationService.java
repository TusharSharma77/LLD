import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NotificationService {
    private final List<TaskObserver> observers = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void addObserver(TaskObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(TaskObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(TaskEventType eventType, Task task) {
        for (TaskObserver observer : observers) {
            executorService.submit(() -> observer.onTaskEvent(eventType, task));
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}

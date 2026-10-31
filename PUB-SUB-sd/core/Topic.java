import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Topic {
    private String name;
    private List<Subscriber> subscribers;

    public Topic(String name) {
        this.name = name;
        // CopyOnWriteArrayList prevents ConcurrentModificationException when publishing while adding/removing subscribers
        this.subscribers = new CopyOnWriteArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void subscribe(Subscriber subscriber) {
        if (!subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
            System.out.println("Subscriber " + subscriber.getName() + " added to topic " + name);
        }
    }

    public void unsubscribe(Subscriber subscriber) {
        if (subscribers.contains(subscriber)) {
            subscribers.remove(subscriber);
            System.out.println("Subscriber " + subscriber.getName() + " removed from topic " + name);
        }
    }

    // Aliases for compatibility
    public void addSubscriber(Subscriber subscriber) {
        subscribe(subscriber);
    }

    public void removeSubscriber(Subscriber subscriber) {
        unsubscribe(subscriber);
    }

    public void notifySubscribers(Message message) {
        for (Subscriber subscriber : subscribers) {
            subscriber.onMessage(message);
        }
    }
}

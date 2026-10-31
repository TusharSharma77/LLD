import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MessageBroker {
    private static final MessageBroker INSTANCE = new MessageBroker();

    private MessageBroker() {
        // private constructor to prevent instantiation
    }

    public static MessageBroker getInstance() {
        return INSTANCE;
    }

    private final Map<String, Topic> topics = new ConcurrentHashMap<>();

    public void createTopic(String topicName) {
        topics.computeIfAbsent(topicName, name -> {
            System.out.println("Topic " + name + " created");
            return new Topic(name);
        });
    }

    public Topic getTopic(String topicName) {
        return topics.get(topicName);
    }

    public void subscribe(String topicName, Subscriber subscriber) {
        Topic topic = getOrThrow(topicName);
        topic.subscribe(subscriber);
    }

    public void unsubscribe(String topicName, Subscriber subscriber) {
        Topic topic = getOrThrow(topicName);
        topic.unsubscribe(subscriber);
    }

    public void publish(String topicName, String payLoad) {
        Topic topic = getOrThrow(topicName);
        Message message = new Message(topicName, payLoad, System.currentTimeMillis());
        topic.notifySubscribers(message);
    }

    private Topic getOrThrow(String topicName) {
        Topic topic = topics.get(topicName);
        if (topic == null) {
            throw new RuntimeException("Topic " + topicName + " does not exist");
        }
        return topic;
    }
}

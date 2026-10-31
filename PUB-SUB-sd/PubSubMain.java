public class PubSubMain {
    public static void main(String[] args) {
        MessageBroker broker = MessageBroker.getInstance();

        // 1. Create Topics
        broker.createTopic("tech");
        broker.createTopic("sports");

        // 2. Create Subscribers
        Subscriber devSubscriber = new FirstTypeSubscriber("DevSub");
        Subscriber newsSubscriber = new FirstTypeSubscriber("NewsSub");

        // 3. Subscribe to topics
        broker.subscribe("tech", devSubscriber);
        broker.subscribe("tech", newsSubscriber);
        broker.subscribe("sports", newsSubscriber);

        // 4. Create Publishers
        Publisher techPublisher = new Publisher("TechRadar");
        Publisher sportsPublisher = new Publisher("ESPN");

        // 5. Publish Messages
        System.out.println("\n--- Publishing to 'tech' ---");
        techPublisher.publish("tech", "Java 21 Virtual Threads released!");

        System.out.println("\n--- Publishing to 'sports' ---");
        sportsPublisher.publish("sports", "Championship final starts tonight!");

        // 6. Unsubscribe
        System.out.println("\n--- Unsubscribing NewsSub from 'tech' ---");
        broker.unsubscribe("tech", newsSubscriber);

        System.out.println("\n--- Publishing to 'tech' after unsubscribe ---");
        techPublisher.publish("tech", "Spring Boot 3.3 is out!");
    }
}

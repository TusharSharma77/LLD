public class Publisher {
    private String name;
    private MessageBroker messageBroker;

    public Publisher(String name) {
        this.name = name;
        this.messageBroker = MessageBroker.getInstance();
    }

    public void publish(String topicName, String payLoad) {
        messageBroker.publish(topicName, payLoad);
        System.out.println("Publisher " + name + " published message to topic " + topicName);
    }

    public String getName() {
        return name;
    }
}

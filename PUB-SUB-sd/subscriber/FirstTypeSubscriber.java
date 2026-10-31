public class FirstTypeSubscriber implements Subscriber {
    private String name;

    public FirstTypeSubscriber(String name) {
        this.name = name;
    }

    @Override
    public void onMessage(Message message) {
        System.out.println("Subscriber " + name + " received message: " + message);
    }

    @Override
    public String getName() {
        return name;
    }
}

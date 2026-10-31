

public class Message{
    private String topicName;
    private String payLoad;
    private long timeStamp;

    public Message(String topicName, String payLoad, long timeStamp) {
        this.topicName = topicName;
        this.payLoad = payLoad;
        this.timeStamp = timeStamp;
    }

    // getter
    public String getTopicName(){
        return topicName;
    }  

    public String getPayLoad(){
        return payLoad;
    }

    public long getTimeStamp(){
        return timeStamp;
    }

     @Override
    public String toString(){
      return "[" + topicName + ", " + payLoad + ", " + timeStamp + "]";
    }
}
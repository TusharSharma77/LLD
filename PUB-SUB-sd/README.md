# In-Memory Publish-Subscribe (Pub/Sub) System

A thread-safe in-memory Pub/Sub message broker built in Java for Low-Level Design (LLD).

---

## What is Pub/Sub?

Publish-Subscribe (Pub/Sub) is an asynchronous messaging pattern where:
- **Publishers** send messages without knowing who the receivers are.
- **Subscribers** express interest in one or more named channels (**Topics**) and only receive messages sent to those topics.
- **MessageBroker** sits in between as the mediator. It completely decouples senders from receivers in space and time.

### Why not direct method calls?
In a standard caller-callee setup, service A must know about service B, C, and D. If service D goes down or changes its interface, service A breaks. With Pub/Sub:
- **Decoupling**: Publishers and subscribers have zero compile-time or runtime dependencies on each other.
- **Scalability**: Multiple independent consumers can listen to the exact same stream of events without slowing down the producer.
- **Dynamic subscriptions**: Consumers can join or leave a topic at runtime without touching the publisher code.

---

## Architecture & Class Design

```
+---------------+                +---------------------------------------+
|   Publisher   | ----publish--> |             MessageBroker             |
+---------------+                | (Singleton, ConcurrentHashMap<Topics>) |
                                 +---------------------------------------+
                                                     |
                                                dispatches to
                                                     v
                                         +-----------------------+
                                         |      Topic: tech      |
                                         | (CopyOnWriteArrayList)|
                                         +-----------------------+
                                                     |
                                        +------------+------------+
                                        |                         |
                                        v                         v
                              +--------------------+    +--------------------+
                              | Subscriber: DevSub |    | Subscriber: NewsSub|
                              +--------------------+    +--------------------+
```

### Components
1. **`Message` (`model/`)**: Immutable data carrier containing `topicName`, string `payLoad`, and creation `timeStamp`.
2. **`Subscriber` (`core/`)**: Interface with `onMessage(Message)` and `getName()`. Any listener implements this contract.
3. **`FirstTypeSubscriber` (`subscriber/`)**: Concrete subscriber that prints received messages.
4. **`Topic` (`core/`)**: Named channel holding an active list of subscribers. Handles broadcast logic.
5. **`MessageBroker` (`core/`)**: Central registry managing topic lifecycle and message routing.
6. **`Publisher` (`publisher/`)**: Client that publishes events to a target topic via the broker.
7. **`PubSubMain.java`**: Driver showing topics creation, subscriptions, publishing, and unsubscription.

---

## Execution Flow

### 1. Subscription Flow
```
Client -> MessageBroker.subscribe("tech", devSubscriber)
           -> fetches Topic("tech")
           -> topic.subscribe(devSubscriber)
           -> adds to subscriber list
```

### 2. Message Dispatch Flow
```
Publisher.publish("tech", "Java 21 Released")
  -> MessageBroker.publish("tech", payload)
      -> creates new Message(topic, payload, timestamp)
      -> topic.notifySubscribers(message)
          -> iterates through subscribers
          -> subscriber.onMessage(message)
```

### 3. Unsubscribe Flow
```
Client -> MessageBroker.unsubscribe("tech", devSubscriber)
           -> fetches Topic("tech")
           -> topic.unsubscribe(devSubscriber)
```

---

## Concurrency & Thread-Safety Decisions

1. **`MessageBroker`**:
   - Implemented as a Singleton (`getInstance()`).
   - Stores topics in a `ConcurrentHashMap<String, Topic>` to support safe parallel topic lookups and dynamic topic creation using `computeIfAbsent`.

2. **`Topic`**:
   - Uses `CopyOnWriteArrayList<Subscriber>` for its subscriber list.
   - **Why?** In a multi-threaded system, subscribers may join or leave while a publisher is iterating and broadcasting messages. Standard `ArrayList` throws `ConcurrentModificationException`. `CopyOnWriteArrayList` allows fast, lock-free iterations without race conditions during event broadcast.

---

## Project Structure

```
PUB-SUB-sd/
├── PubSubMain.java                       # Demo entry point
├── README.md                             # System documentation
├── func-nonfunc.txt                      # Requirements notes
├── core/
│   ├── MessageBroker.java               # Singleton broker registry
│   ├── Subscriber.java                  # Subscriber interface contract
│   └── Topic.java                       # Named channel holding subscribers
├── model/
│   └── Message.java                     # Message entity (topic, payload, timestamp)
├── publisher/
│   └── Publisher.java                   # Client publisher
└── subscriber/
    └── FirstTypeSubscriber.java          # Concrete subscriber implementation
```

---

## How to Run

Compile and run from the `PUB-SUB-sd` directory:

```bash
javac -cp ".;core;model;publisher;subscriber" *.java core/*.java model/*.java publisher/*.java subscriber/*.java
java -cp ".;core;model;publisher;subscriber" PubSubMain
```

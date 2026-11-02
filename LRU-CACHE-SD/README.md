# LRU (Least Recently Used) Cache - Low Level Design

A generic, thread-safe in-memory LRU Cache implementation in Java with $O(1)$ time complexity for both `get` and `put` operations.

---

## 1. What is an LRU Cache?

An **LRU (Least Recently Used) Cache** is a caching mechanism that evicts the least recently accessed items first when its capacity limit is reached.

* When an item is accessed via `get()` or updated via `put()`, it becomes the **Most Recently Used (MRU)**.
* When the cache reaches full capacity and a new element is inserted, the **Least Recently Used (LRU)** item is evicted.

---

## 2. Core Architecture: HashMap + Doubly Linked List

To achieve **$O(1)$** performance for both reads and writes:
1. **`HashMap<K, Node<K, V>>`**:
   - Provides **$O(1)$ key lookup** to directly locate the corresponding node in memory.
2. **`DoublyLinkedList<K, V>`**:
   - Maintains the access order from **Head (MRU)** to **Tail (LRU)**.
   - Provides **$O(1)$ node removal** and **$O(1)$ insertion at head** using sentinel (dummy) `head` and `tail` nodes.

```
                  +-------------------------------------------------------+
                  |                      HashMap                          |
                  |  Key1 -> Node1,  Key2 -> Node2,  Key3 -> Node3        |
                  +-------------------------------------------------------+
                                              |
                                              v
           [Head (Dummy)] <--> [Node3 (MRU)] <--> [Node1] <--> [Node2 (LRU)] <--> [Tail (Dummy)]
                                 ^                               ^
                         Most Recently Used              Least Recently Used
                                                         (First to be evicted)
```

---

## 3. Operations & Time Complexity

| Operation | Flow | Time Complexity |
| :--- | :--- | :--- |
| **`get(key)`** | 1. Lookup `key` in HashMap.<br>2. If missing, return `null`.<br>3. If found, detach node and move to Head (MRU).<br>4. Return `node.value`. | **$O(1)$** |
| **`put(key, value)`** | 1. If key exists: update value and move node to Head.<br>2. If key is new:<br>&nbsp;&nbsp;&nbsp;&nbsp;a. If capacity full, remove node before Tail (`tail.prev`) and delete from HashMap.<br>&nbsp;&nbsp;&nbsp;&nbsp;b. Create new node, insert at Head, and record in HashMap. | **$O(1)$** |

---

## 4. Concurrency & Thread-Safety

- `get(key)` and `put(key, value)` methods are marked `synchronized` on the cache instance.
- This ensures mutual exclusion: multiple concurrent reader and writer threads cannot corrupt pointer references (`head`/`tail`) or cause inconsistent map states.

---

## 5. Project Structure

```
LRU-CACHE-SD/
├── LRUCacheMain.java            # Entry point driver demonstrating eviction and updates
├── README.md                    # Technical documentation
├── func-nonfunc.txt             # Requirements specification
├── cache/
│   └── LRUCache.java            # Core cache orchestration (HashMap + DoublyLinkedList)
├── ds/
│   └── DoublyLinkedList.java    # Custom Doubly Linked List with dummy head & tail
└── model/
    └── Node.java                # Doubly linked list node holding key and value
```

---

## 6. How to Compile & Run

From the `LRU-CACHE-SD` directory:

```bash
javac -cp ".;cache;ds;model" *.java cache/*.java ds/*.java model/*.java
java -cp ".;cache;ds;model" LRUCacheMain
```

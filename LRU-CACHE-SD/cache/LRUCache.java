import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private final DoublyLinkedList<K, V> list;
    private final Map<K, Node<K, V>> map;

    public LRUCache(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.list = new DoublyLinkedList<>();
        this.map = new HashMap<>();
    }

    public synchronized V get(K key) {
        if (!map.containsKey(key)) {
            return null;
        }
        Node<K, V> node = map.get(key);
        list.moveToHead(node);
        return node.value;
    }

    public synchronized void put(K key, V value) {
        if (map.containsKey(key)) {
            Node<K, V> node = map.get(key);
            node.value = value;
            list.moveToHead(node);
        } else {
            if (list.getSize() == capacity) {
                Node<K, V> tail = list.removeTail();
                if (tail != null) {
                    map.remove(tail.key);
                }
            }
            Node<K, V> newNode = new Node<>(key, value);
            list.addAtHead(newNode);
            map.put(key, newNode);
        }
    }

    public synchronized int size() {
        return list.getSize();
    }

    public synchronized boolean containsKey(K key) {
        return map.containsKey(key);
    }
}

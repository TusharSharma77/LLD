

public class DoublyLinkedList<K,V> { // generic class for doubly linked list
    private Node<K,V> head,tail;
    public int size;
   
   public DoublyLinkedList(){
     head = new Node<>(null,null);
     tail = new Node<>(null,null);
        head.next = tail;
        tail.prev = head;
   }
   // head -><- Node1-> <- Node2-><- tail
   public void addAtHead(Node<K,V> node){
    node.next = head.next;
    node.prev = head;
    head.next.prev = node;
    head.next = node;
    size++;
   }

   public void removeNode(Node<K,V> node){
    node.next.prev = node.prev;
    node.prev.next = node.next;
    size--;
   }
   public void moveToHead(Node<K,V> node){
    removeNode(node);
    addAtHead(node);
   }
   public Node<K,V> getTail(){
    return tail;
   }
   public int getSize(){
    return size;
   }
   public Node<K,V> getHead(){
    return head;
   }
   public boolean isEmpty(){
    return size == 0;
   }
   public Node<K,V> removeTail(){
    if(size == 0) return null;
    Node<K,V> node = tail.prev;
    removeNode(node);
    return node;
   }
}
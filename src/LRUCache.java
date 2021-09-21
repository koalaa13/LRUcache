import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class LRUCache<K, V> {
    private final int capacity;
    private int size;
    private final LRUCacheLinkedList elemsList;
    private final Map<K, Node> elemsMap;

    private void assertSize() {
        assert size <= capacity : "Size can not be greater then capacity";
    }

    public int getSize() {
        assertSize();
        return size;
    }

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.elemsList = new LRUCacheLinkedList();
        this.elemsMap = new HashMap<>();
    }

    public V get(K key) {
        if (!elemsMap.containsKey(key)) {
            return null;
        }
        V value = elemsMap.get(key).value;
        elemsList.moveElemToHead(elemsMap.get(key));
        assert elemsList.head.value == value : "Gotten elem should be in head";
        return value;
    }

    public void put(K key, V value) {
        if (elemsMap.containsKey(key)) {
            elemsMap.get(key).value = value;
            elemsList.moveElemToHead(elemsMap.get(key));
            assert elemsList.head.value == value && elemsList.head.key == key : "Put elem should be in head";
            return;
        }
        assertSize();
        // should remove the last element
        if (size == capacity) {
            K k = elemsList.getTail().key;
            elemsMap.remove(k);
            elemsList.eraseFromTail();
            size--;
        }
        assertSize();
        Node newNode = elemsList.addToHead(key, value);
        size++;
        elemsMap.put(key, newNode);
        assertSize();
        assert elemsList.head.value == value && elemsList.head.key == key : "Put elem should be in head";
    }

    private class Node {
        public Node next;
        public Node prev;
        public K key;
        public V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    private class LRUCacheLinkedList {
        private Node head;
        private Node tail;

        boolean isEmpty() {
            return tail == null;
        }

        void eraseFromTail() {
            // list is empty
            if (isEmpty()) {
                return;
            }
            // 1 elem in list
            if (head == tail) {
                head = null;
                tail = null;
                assert isEmpty() : "List should be empty";
            } else {
                tail = tail.prev;
                tail.next = null;
                assert tail.next == null : "Tail.next should be null";
            }
        }

        Node getTail() {
            return tail;
        }

        void moveElemToHead(Node elem) {
            assert elem != null : "Unexisting elem";
            assert !isEmpty() : "Can not move elem to head because list is empty";
            // elem is already first
            if (head == elem) {
                return;
            }
            if (tail == elem) {
                tail = tail.prev;
                tail.next = null;
            } else {
                elem.prev.next = elem.next;
                elem.next.prev = elem.prev;
            }
            elem.prev = null;
            elem.next = head;
            head.prev = elem;
            head = elem;
            assert head == elem : "Elem should be first";
        }

        Node addToHead(K key, V value) {
            Node newNode = new Node(key, value);
            if (isEmpty()) {
                assert head == tail : "Empty list should have head = tail";
                head = tail = newNode;
            } else {
                assert head != null : "Head can not be null";
                newNode.next = head;
                head.prev = newNode;
                head = newNode;
            }
            assert head == newNode : "Should add new element to head";
            return newNode;
        }
    }
}

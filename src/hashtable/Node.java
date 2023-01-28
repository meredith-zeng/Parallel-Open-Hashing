package hashtable;

import java.util.Map;

public class Node<K, V> implements Map.Entry<K, V>{
    public int hash;
    private K key;
    private V value;
    public Node<K, V> next;

    public Node(int hash, K key, V value, Node<K, V> next){
        this.hash = hash;
        this.key = key;
        this.value = value;
        this.next = next;
    }
    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public V setValue(V value) {
        this.value = value;
        return this.value;
    }
}

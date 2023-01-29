package hashtable;

import util.MathUtil;
import util.StringUtil;
import java.util.*;


// inheritance from Dictionary
// implement means implement all method from a template
public class ThreadSafeHashTable<K, V> extends Dictionary<K,V> {
    private int nodeCnt;
    Node<K, V>[] table;
    private final double loadFactor = 1.1;
    private final double reduceFactor = 0.4;
    private int capacity;
    private int binaryCapNum;

    public int getCapacity(){
        return this.capacity;
    }

    @Override
    public int size() {
        return this.nodeCnt;
    }

    @Override
    public boolean isEmpty() {
        return this.nodeCnt == 0;
    }


    /**
     * Constructor
     * */
    public ThreadSafeHashTable(int cap) {
        if(cap <= 0){
            throw new IllegalArgumentException("Negative Capacity or zero" + cap);
        }
        if(cap < 7){
            cap = 7;
        }
        this.table = new Node[cap];
        this.capacity = cap;
        this.binaryCapNum = 8;
    }

    public ThreadSafeHashTable(){
        this(7);
    }


    public synchronized int StringHashCode(String keyStr){
        if(keyStr == null){
            return 0;
        }

        List<String> chunks = StringUtil.chunkSplit(keyStr);

        for(int i = 0; i < chunks.size(); i += 2){
            String beforeReverseStr = chunks.get(i);
            String afterReverseStr = MathUtil.reverseBits(beforeReverseStr);

            Long curVal = Long.valueOf(afterReverseStr, 16);
            if(curVal >= Integer.MAX_VALUE){
                curVal = curVal % Integer.MAX_VALUE;
            }
            afterReverseStr = Long.toHexString(curVal);

            chunks.set(i, afterReverseStr.toUpperCase());
        }
        Long hashCode = Long.valueOf(chunks.get(0), 16);
        for(int i = 1; i < chunks.size(); i++){
            hashCode ^= Long.valueOf(chunks.get(i), 16);
        }
        if(hashCode >= Integer.MAX_VALUE){
            hashCode = hashCode % Integer.MAX_VALUE;
        }
        int hash = new Long(hashCode).intValue();
        int hashIdx = (hash & Integer.MAX_VALUE) % capacity;
        return hashIdx;
    }

    @Override
    public synchronized V get(Object key) {
        Node<K, V>[] curTable = table;
        int hash = hashCode(key);
        int idx = getIdx(key, hash);

        Node<?, ?> node = curTable[idx];
        while(node != null){
            if(node.hash == hash && node.getKey().equals(key)){
                return (V) node.getValue();
            }
            node = node.next;
        }
        return null;
    }

    @Override
    public synchronized V put(K key, V value) {
        if(value == null || key == null){
            throw new NullPointerException();
        }

        Node<K, V>[] curTable = table;
        int hash = hashCode(key);
        int idx = getIdx(key, hash);

        Node<K, V> node = curTable[idx];
        while (node != null){
            if(node.hash == hash && node.getKey().equals(key)){
                V temp = node.getValue();
                node.setValue(value);
                return temp;
            }
            node = node.next;
        }
        // current idx doesn't have node yet, just add to the table
        addNode(key, value);
        return null;
    }

    @Override
    public synchronized V remove(Object key) {
        int hash = hashCode(key);
        int idx = getIdx(key, hash);

        Node<K, V> node = table[idx];
        Node<K, V> preNode = null;

        while(node != null){
            if(node != null
                    && node.hash == hash
                    && node.getKey().equals(key)){
                if(preNode != null){
                    preNode.next = node.next;
                }else if(preNode == null){
                    table[idx] = node.next;
                }

                nodeCnt--;
                V temp = node.getValue();
                node.setValue(null);
                return temp;
            }
            preNode = node;
            node = node.next;
        }
        return null;
    }

    public synchronized int hashCode(Object key){
        int hash;
        if(key instanceof String){
            hash = StringHashCode((String) key);
        }else{
            hash = key.hashCode();
        }
        return hash;
    }


    public synchronized boolean containsKey(Object key) {
        Node<K, V>[] curTable = table;
        int hash = hashCode(key);
        int idx = getIdx(key, hash);

        Node<K, V> curNode = curTable[idx];
        while(curNode != null){
            if(curNode != null
                    && curNode.hash == hash
                    && curNode.getKey().equals(key)){
                return true;
            }
            curNode = curNode.next;
        }
        return false;
    }

    private synchronized int getIdx(Object key, int hash){
        int idx;
        if(!(key instanceof String)){
            idx = hash % capacity;
        }else {
            idx = hash;
        }
        return idx;
    }

    private synchronized void addNode(K key, V value) {
        int hash = hashCode(key);
        int idx = getIdx(key, hash);
        Node<K, V> node = table[idx];

        Node<K, V> newNode = new Node<>(hash, key, value, node);
        table[idx] = newNode;
        nodeCnt++;

        if(nodeCnt >= capacity * loadFactor) {
            int newCapacity = MathUtil.floorPrime(binaryCapNum);
            if(newCapacity == capacity
                    || (newCapacity / 10  >= Integer.MAX_VALUE)
                    || newCapacity < 7){
                return;
            }
            increaseRehash(newCapacity);
        } else if(nodeCnt >= 7 && nodeCnt < capacity * reduceFactor){
            int newCapacity = MathUtil.ceilingPrime(binaryCapNum);
            if(newCapacity == capacity
                    || (newCapacity >= Integer.MAX_VALUE)
                    || newCapacity < 7){
                return;
            }
            reduceRehash(newCapacity);
        }


    }

    private synchronized void reduceRehash(int newCapacity) {
        int oldCap = table.length;
        Node<K, V>[] curTable = table;
        Node<K, V>[] newTable = new Node[newCapacity];

        this.binaryCapNum = (binaryCapNum << 1);
        this.capacity = newCapacity;
        this.table = newTable;

        for(int i = 0; i < oldCap; i++){
            Node<K, V> oldNode = curTable[i];
            while(oldNode != null){
                Node<K, V> curNode = oldNode;
                oldNode = oldNode.next;
                int hash = hashCode(curNode.getKey());
                int idx;
                if(!(curNode.getKey() instanceof String)){
                    idx = hash % newCapacity;
                }else {
                    idx = hash;
                }
                curNode.next = newTable[idx];
                newTable[idx] = curNode;
            }

        }
        System.out.println("HashTable rehash and decrease the capacity from " + oldCap + "to " + newCapacity);


    }

    private synchronized void increaseRehash(int newCapacity) {
        int oldCap = table.length;
        Node<K, V>[] curTable = table;
        Node<K, V>[] newTable = new Node[newCapacity];

        this.binaryCapNum = (binaryCapNum >> 1);
        this.capacity = newCapacity;
        this.table = newTable;

        for(int i = 0; i < oldCap; i++){

            Node<K, V> oldNode = curTable[i];
            while(oldNode != null){
                Node<K, V> curNode = oldNode;
                oldNode = oldNode.next;

                int idx = (curNode.hash & Integer.MAX_VALUE) % newCapacity;
                curNode.next = newTable[idx];
                newTable[idx] = curNode;
            }

        }
        System.out.println("HashTable rehash and increase the capacity from " + oldCap + " to " + newCapacity);
    }


    // don't require by P1 requirement
    @Override
    public Enumeration<K> keys() {
        return null;
    }

    @Override
    public Enumeration<V> elements() {
        return null;
    }


}


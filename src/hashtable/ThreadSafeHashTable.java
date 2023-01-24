package hashtable;

import util.MathUtil;
import util.StringUtil;


import java.math.BigInteger;
import java.util.*;


// inheritance from Dictionary
// implement means implement all method from a template
public class ThreadSafeHashTable<K, V> extends Dictionary<K,V> {
    int nodeCnt;
    Node<K, V>[] table;
    double loadFactor = 1.1;
    double reduceFactor = 0.4;
    int capacity;
    int MAX_ARRAY_SIZE = Integer.MAX_VALUE;

    /**
     * Constructor
     * */
    public ThreadSafeHashTable(int cap) {
        if(cap < 0){
            throw new IllegalArgumentException("Negative Capacity" + cap);
        }
        if(cap < 7){
            capacity = 7;
        }
        table = new Node[cap];
    }

    public ThreadSafeHashTable(){
        this(7);
    }

    @Override
    public int size() {
        return this.nodeCnt;
    }

    @Override
    public boolean isEmpty() {
        return this.nodeCnt == 0;
    }

    @Override
    public synchronized V get(Object key) {
        Node<K, V>[] curTable = table;
        int hash = hashCode(key);
        int idx = (hash & Integer.MAX_VALUE) % curTable.length;
        Node<?, ?> node = curTable[idx];
        while(node != null){
            if(node.hash == hash && node.key.equals(key)){
                return (V) node.value;
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
        int idx = (hash & Integer.MAX_VALUE) % curTable.length;
        Node<K, V> node = curTable[idx];
        while(node != null){
            if(node.hash == hash && node.key.equals(key)){
                nodeCnt++;
                V temp = node.value;
                node.value = value;
                return temp;
            }
            node = curTable[idx];
            addToTable(hash, key, value, idx);
            node = node.next;
        }
        nodeCnt++;
        return null;
    }

    private void addToTable(int hash, K key, V value, int idx) {
        Node<K, V> node = table[idx];
        table[idx] = new Node<>(hash, key, value, node);
        nodeCnt++;
        if(nodeCnt >= capacity * loadFactor) {
//            increaseRehash(capacity * loadFactor);
        }else if(nodeCnt < capacity * reduceFactor){
//            reduceRehash(capacity * reduceFactor);
        }
    }

    private void reduceRehash(int v) {
        int curCapacity = table.length;
        Node<K, V>[] curTable = table;
        int newCapacity = MathUtil.getClosestPrimeLess(curCapacity);
        if(newCapacity == curCapacity){
            return;
        }
        Node<K, V>[] newTable = new Node[newCapacity];

        this.capacity = newCapacity;
        this.table = newTable;

        for(int i = 0; i < curCapacity; i++){

        }


    }

    private void increaseRehash(int v) {
    }


    @Override
    public synchronized V remove(Object key) {
        Node<K, V>[] curTable = table;
        int hash = key.hashCode();
        if(key.getClass() == String.class){
            hash = StringHashCode((String) key);
        }else{
            hash = key.hashCode();
        }
        int idx = hash % table.length;
        Node<K, V> node = table[idx];
        Node<K, V> preNode = null;
        while(node != null){
            if(node.hash == hash && node.key.equals(key)){
                if(preNode != null){
                    preNode.next = node.next;
                }else if(preNode == null){
                    table[idx] = node.next;
                }

                nodeCnt--;
                V temp = node.value;
                node.value = null;
                return temp;
            }
            preNode = node;
            node = node.next;
        }
        return null;
    }

    public int hashCode(Object key){
        int hash;
        if(key.getClass() == String.class){
            hash = StringHashCode((String) key);
        }else{
            hash = key.hashCode();
        }
        return hash;
    }


    public int StringHashCode(String keyStr){
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

        return new Long(hashCode).intValue();
    }

    public synchronized boolean containsKey(Object key) {
        return false;
    }


    @Override
    public synchronized Enumeration<K> keys() {
        return null;
    }

    @Override
    public synchronized Enumeration<V> elements() {
        return null;
    }



}


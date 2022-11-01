package com.tsa.list.interfaces;

import com.tsa.list.implementations.HashMap;
import com.tsa.list.implementations.MyLinkedList;

import java.util.Iterator;

public interface Map <K, V> extends Iterable<HashMap.MyEntry<K, V>>  {
    V put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
    int size();
    public MyLinkedList<K> getKeyArray();
    default Iterator<HashMap.MyEntry<K, V>> iterator() {
        return null;
    }
}

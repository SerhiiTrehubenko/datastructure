package com.tsa.list.interfaces;

import com.tsa.list.implementations.MyLinkedList;

import java.util.Iterator;

public interface Map <K, V> extends Iterable <K> {
    V put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
    int size();
    public MyLinkedList<K> getKeyArray();
    default Iterator<K> iterator() {
        return null;
    }
}

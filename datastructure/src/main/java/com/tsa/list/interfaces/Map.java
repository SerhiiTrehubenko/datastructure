package com.tsa.list.interfaces;

import com.tsa.list.implementations.MyLinkedList;

import java.util.Iterator;

public interface Map <K, V> extends Iterable<Map.MyEntry<K, V>>  {
    V put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
    int size();
    MyLinkedList<K> getKeyArray();

    default Iterator<Map.MyEntry<K, V>> iterator() {
        return null;
    }
    interface MyEntry<K, V> {
        K getKey();
        void setValue(V value);
        V getValue();
    }
}

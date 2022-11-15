package com.tsa.list.interfaces;

import java.util.Iterator;

public interface Map <K, V> extends Iterable<Map.Entry<K, V>>  {
    V put(K key, V value);
    V get(K key);
    boolean containsKey(K key);
    V remove(K key);
    int size();

    default Iterator<Entry<K, V>> iterator() {
        return null;
    }
    interface Entry<K, V> {
        K getKey();
        void setValue(V value);
        V getValue();
    }
}

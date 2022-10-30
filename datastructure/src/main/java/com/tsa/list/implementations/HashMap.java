package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;
import com.tsa.list.interfaces.Map;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {
    private static final double INITIAL_LOAD_FACTOR = 0.75;
    private static final int INITIAL_GROW_FACTOR = 2;
    private final double loadFactor;
    private static final int INITIAL_CAPACITY = 5;
    private Object[] buckets;

    public HashMap() {
        this(INITIAL_CAPACITY, INITIAL_LOAD_FACTOR);
    }
    public HashMap(int initialCapacity) {
        this(initialCapacity, INITIAL_LOAD_FACTOR);
    }
    public HashMap(double loadFactor) {
        this(INITIAL_CAPACITY, loadFactor);
    }
    public HashMap(int initialCapacity, double loadFactor) {
        this.buckets = new Object[initialCapacity];
        this.loadFactor = loadFactor;
        for (int i = 0; i < initialCapacity; i++) {
            buckets[i] = new MyArrayList<MyEntry<K,V>>();
        }
    }

    @Override
    public V put(K key, V value) {
        if (buckets.length * loadFactor <= size()) {
            grow();
        }
        int index = getIndex(key, buckets.length);
        boolean isAdd = false;
        V retrievedValue = null;
        var foundBucked = getBucket(index);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if(Objects.equals(myEntry.getKey(), key)) {
                    retrievedValue = myEntry.getValue();
                    myEntry.setValue(value);
                    isAdd = true;
                }
            }
        } else {
            foundBucked.add(new MyEntry<>(key, value));
            isAdd = true;
        }
        if (!isAdd){
            foundBucked.add(new MyEntry<>(key, value));
        }

        return retrievedValue;
    }

    @Override
    public V get(K key) {
        int index = getIndex(key, buckets.length);
        V retrievedValue = null;
        var foundBucked = getBucket(index);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if (Objects.equals(myEntry.getKey(), key)) {
                    retrievedValue = myEntry.getValue();
                }
            }
        }
        return retrievedValue;
    }
    @Override
    public boolean containsKey(K key) {
        int index = getIndex(key , buckets.length);
        var foundBucked = getBucket(index);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if (Objects.equals(myEntry.getKey(), key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public V remove(K key) {
        int index = getIndex(key, buckets.length);
        V retrievedValue = null;
        var foundBucked = getBucket(index);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if (Objects.equals(myEntry.getKey(), key)) {
                    retrievedValue = myEntry.getValue();
                    myEntry.setValue(null);
                }
            }
        }
        return retrievedValue;
    }

    @Override
    public int size() {
        int size = 0;
        for (Object bucket : buckets) {
            size = size + ((MyArrayList<MyEntry<K, V>>)bucket).size();
        }
        return size;
    }
    @Override
    public MyLinkedList<K> getKeyArray() {
        MyLinkedList<K> arrayKey = new MyLinkedList<>();
        for (Object bucket : buckets) {
            var retrievedBucket = (List<MyEntry<K, V>>) bucket;
            for (MyEntry<K, V> entry : retrievedBucket) {
                arrayKey.add(entry.getKey());
            }
        }
        return !arrayKey.isEmpty() ? arrayKey : null;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private final List<K> arrayKey = getKeyArray();
            private final Iterator<K> iterator = arrayKey.iterator();
            int counter;

            @Override
            public boolean hasNext() {
                return counter < arrayKey.size();

            }

            @Override
            public K next() {
                counter++;
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (K k : this) {
            V value = get(k);
            stringJoiner.add(value == null ? "null" : value.toString());
        }
        return stringJoiner.toString();
    }
    private int getIndex(K key, int size) {
        return key != null ? Math.abs(key.hashCode() % size) : Math.abs("null".hashCode() % size);
    }
    private List<MyEntry<K, V>> getBucket(int index) {
        return (List<MyEntry<K, V>>) buckets[index];
    }

    private void grow() {
        Object[] newBuckets = new Object[buckets.length * INITIAL_GROW_FACTOR];
        //System.out.println(newBuckets.length);
        for (int i = 0; i < newBuckets.length; i++) {
            newBuckets[i] = new MyArrayList<MyEntry<K,V>>();
        }
        for (Object bucket : buckets) {
            if (((List<MyEntry<K, V>>) bucket).size() > 0) {
                for (MyEntry<K, V> entry : ((List<MyEntry<K, V>>) bucket)) {
                    ((List<MyEntry<K, V>>) newBuckets[getIndex(entry.getKey(), newBuckets.length)]).add(entry);
                }
            }
        }
        buckets = newBuckets;
    }
    @SuppressWarnings("unchecked")
    private static class MyEntry<K, V> {
        private final K key;
        private V value;

        public MyEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public void setValue(V value) {
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()){
                return false;
            }

            MyEntry<K, V> myEntry = (MyEntry<K, V>) o;

            if (key != null && myEntry.key != null ? !key.equals(myEntry.key) : myEntry.key != null) return false;
            return value != null && myEntry.value != null? value.equals(myEntry.value) : myEntry.value == null;
        }

        @Override
        public int hashCode() {
            int result = 17;
            result = (37 * result) + (key != null ? Objects.hashCode(key) : Objects.hashCode("null"));
            result = (37 * result) + (value != null ? Objects.hashCode(value) : Objects.hashCode("null"));
            return result;
        }
    }
}

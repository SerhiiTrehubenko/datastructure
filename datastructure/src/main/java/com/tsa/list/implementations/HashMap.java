package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;
import com.tsa.list.interfaces.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
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
    }

    @Override
    public V put(K key, V value) {
        if (buckets.length * loadFactor <= size()) {
            grow();
        }
        boolean isAdd = false;
        V retrievedValue = null;
        var foundBucked = getBucket(getIndex(key, buckets.length));
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
        V retrievedValue = null;
        var foundBucked = getBucket(getIndex(key, buckets.length));
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
        var foundBucked = getBucket(getIndex(key, buckets.length));
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
        V retrievedValue = null;
        var foundBucked = getBucket(getIndex(key, buckets.length));
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
            if (bucket != null) size = size + ((MyArrayList<MyEntry<K, V>>)bucket).size();
        }
        return size;
    }
    @Override
    public MyLinkedList<K> getKeyArray() {
        MyLinkedList<K> arrayKey = new MyLinkedList<>();
        for (Object bucket : buckets) {
            if (bucket != null) {
                var retrievedBucket = (List<MyEntry<K, V>>) bucket;
                for (MyEntry<K, V> entry : retrievedBucket) {
                    arrayKey.add(entry.getKey());
                }
            }
        }
        return !arrayKey.isEmpty() ? arrayKey : null;
    }

    @Override
    public Iterator<MyEntry<K, V>> iterator() {
        return new Iterator<>() {
            private int removedLast;
            private int counter;
            private int bucketsLength = buckets.length;
            private int position;
            private int totalSize = size();
            private Object bucket;
            private Iterator<MyEntry<K, V>> iteratorList;

            @Override
            public boolean hasNext() {
                return counter < totalSize;
            }

            @Override
            public MyEntry<K, V> next() {
                if(counter >= totalSize) throw new NoSuchElementException("There is no more elements in the List");
                if (bucket == null || !iteratorList.hasNext()) {
                    bucketsLength = buckets.length;
                    findBucket();
                }
                counter++;
                return iteratorList.next();
            }

            @Override
            public void remove() {
                if (counter == 0) throw new IllegalStateException("\"You have called remove() before next()\"");
                if(removedLast > 0) throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                iteratorList.remove();
                totalSize = size();
                if(counter >= totalSize) removedLast++;
            }

            private void findBucket() {
                while (position < bucketsLength) {
                    if(buckets[position] != null) {
                        bucket = buckets[position];
                        List<MyEntry<K, V>> list = (List<MyEntry<K, V>>) bucket;
                        iteratorList = list.iterator();
                        position++;
                        break;
                    }
                    position++;
                }
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Object bucket : this.buckets) {
            if (bucket != null) {
                for (MyEntry<K,V> entry : (List<MyEntry<K,V>>)bucket) {
                    stringJoiner.add(entry.toString());
                }
            }
        }
        return stringJoiner.toString();
    }
    private int getIndex(K key, int size) {
        return key != null ? Math.abs(key.hashCode() <= Integer.MIN_VALUE ? Integer.MIN_VALUE : key.hashCode() % size) : Math.abs("null".hashCode() % size);
    }
    private List<MyEntry<K, V>> getBucket(int index) {
        if(buckets[index] == null) buckets[index] = new MyArrayList<MyEntry<K,V>>();
        return (List<MyEntry<K, V>>) buckets[index];
    }

    private void grow() {
        Object[] newBuckets = new Object[buckets.length * INITIAL_GROW_FACTOR];
        //System.out.println(newBuckets.length);
        for (Object bucket : buckets) {
            if (bucket != null) {
                for (MyEntry<K, V> entry : ((List<MyEntry<K, V>>) bucket)) {
                    int index = getIndex(entry.getKey(), newBuckets.length);
                    if (newBuckets[index] == null) newBuckets[index] = new MyArrayList<MyEntry<K,V>>();
                    List<MyEntry<K, V>> foundNewBucket = (List<MyEntry<K, V>>) newBuckets[index];
                    foundNewBucket.add(entry);
                }
            }
        }
        buckets = newBuckets;
    }
    @SuppressWarnings("unchecked")
    public static class MyEntry<K, V> {
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

        @Override
        public String toString() {
            StringJoiner stringJoiner = new StringJoiner("=");
            stringJoiner.add(getKey() == null ? "null" : getKey().toString());
            stringJoiner.add(getValue() == null ? "null" : getValue().toString());
            return stringJoiner.toString();
        }
    }
}

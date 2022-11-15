package com.tsa.list.implementations;

import com.tsa.list.interfaces.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap<K, V> implements Map<K, V> {
    private static final double NATIVE_LOAD_FACTOR = 0.75;
    private static final int NATIVE_GROW_FACTOR = 2;
    private final double loadFactor;
    private static final int INITIAL_CAPACITY = 5;
    private Object[] buckets;
    private int sizeMap;

    public HashMap() {
        this(INITIAL_CAPACITY, NATIVE_LOAD_FACTOR);
    }

    public HashMap(int initialCapacity) {
        this(initialCapacity, NATIVE_LOAD_FACTOR);
    }

    public HashMap(double loadFactor) {
        this(INITIAL_CAPACITY, loadFactor);
    }

    public HashMap(int initialCapacity, double loadFactor) {
        this.buckets = new Object[initialCapacity];
        this.loadFactor = loadFactor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V put(K key, V value) {
        if (buckets.length * loadFactor <= sizeMap) {
            grow();
        }
        V retrievedValue;
        int index = getIndex(key, buckets.length);

        if (buckets[index] != null) {
            retrievedValue = putValueToExistEntity(buckets[index], key, value);
            if (retrievedValue != null) return retrievedValue;
        }
        var newElement = new Entry<>(key, value);
        newElement.next = (Entry<K, V>)buckets[index];
        buckets[index] = newElement;
        sizeMap++;
        return null;
    }

    @Override
    public V get(K key) {
        var foundEntry = getEntryByKey(key);
        return foundEntry == null ? null : foundEntry.value;
    }

    @Override
    public boolean containsKey(K key) {
        return getEntryByKey(key) != null;
    }

    @Override
    public V remove(K key) {
        var removedEntry = removeEntryByKey(key);
        return removedEntry == null ? null : removedEntry.value;
    }

    @Override
    public int size() {
        return sizeMap;
    }

    @Override
    public Iterator<Map.Entry<K, V>> iterator() {
        return new Iterator<>() {
            private boolean removedLast;
            private int counter;
            private int position;
            private Entry<K, V> returnedEntry;
            private Entry<K, V> currentEntry;

            @Override
            public boolean hasNext() {
                return counter < sizeMap;
            }

            @SuppressWarnings("unchecked")
            @Override
            public Entry<K, V> next() {
                if (counter >= sizeMap) throw new NoSuchElementException("There is no more elements");

                while (currentEntry == null && position < buckets.length) {
                    currentEntry = (Entry<K, V>) buckets[position];
                    position++;
                }

                returnedEntry = currentEntry;
                currentEntry = currentEntry == null ? null : currentEntry.next;

                counter++;
                return returnedEntry;
            }

            @Override
            public void remove() {
                if (counter == 0) {
                    throw new IllegalStateException("\"You have called remove() before next()\"");
                }
                if (removedLast) {
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                }
                HashMap.this.remove(returnedEntry.key);
                if (counter >= sizeMap) {
                    removedLast = true;
                }
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Map.Entry<K, V> myEntry : this) {
            stringJoiner.add(myEntry.toString());
        }
        return stringJoiner.toString();
    }

    int getBucketsLength() {
        return buckets.length;
    }

    private int getIndex(K key, int size) {
        int hashCode = Objects.hashCode(key);
        return Math.abs(hashCode == Integer.MIN_VALUE ? 0 : Objects.hashCode(key) % size);
    }
    @SuppressWarnings("unchecked")
    private V putValueToExistEntity (Object bucket, K key, V value) {
        V retrievedValue;
        var currentEntity = (Entry<K, V>) bucket;
        if (Objects.equals(currentEntity.hashCode(), Objects.hashCode(key)) &&
                Objects.equals(currentEntity.key, key)) {
            retrievedValue = currentEntity.value;
            currentEntity.value = value;
            return retrievedValue;
        }
        if (currentEntity.next != null) {
            while (currentEntity.next != null) {
                if (Objects.equals(currentEntity.next.hashCode(), Objects.hashCode(key)) &&
                        Objects.equals(currentEntity.next.key, key)) {
                    retrievedValue = currentEntity.next.value;
                    currentEntity.next.value = value;
                    return retrievedValue;
                }
                currentEntity = currentEntity.next;
            }
        }
        return null;
    }

    private void grow() {
        Object[] newBuckets = new Object[buckets.length * NATIVE_GROW_FACTOR];
        fillBucketsWithDoubledCapacity(newBuckets);
        buckets = newBuckets;
    }

    @SuppressWarnings("unchecked")
    private void fillBucketsWithDoubledCapacity(Object[] newBuckets) {
        for (Map.Entry<K, V> myEntry : this) {
            var castedEntry = (Entry<K, V>) myEntry;
            int index = getIndex(castedEntry.key, newBuckets.length);
            if (newBuckets[index] == null) {
                castedEntry.next = null;
            } else {
                var retrievedMyEntry = newBuckets[index];
                castedEntry.next = (Entry<K, V>) retrievedMyEntry;
            }
            newBuckets[index] = castedEntry;
        }
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> getEntryByKey(K key) {
        int index = getIndex(key, buckets.length);

        if (buckets[index] != null) {
            var entry = (Entry<K, V>) buckets[index];
            if (Objects.equals(entry.hashCode(), Objects.hashCode(key)) && // checking the first Entry
                    Objects.equals(entry.key, key)) {
                return entry;
            }
            if (entry.next != null) {
                while (entry.next != null) { // checking Entries from the second to the end
                    if (Objects.equals(entry.next.hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(entry.next.key, key)) {
                        return entry.next;
                    }
                    entry = entry.next;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private Entry<K, V> removeEntryByKey(K key) {
        int index = getIndex(key, buckets.length);
        var foundBucket = buckets[index];
        Entry<K, V> retrievedEntry;
        if (foundBucket != null) {
            var entry = (Entry<K, V>) foundBucket;
            if (Objects.equals(entry.hashCode(), Objects.hashCode(key)) && // checking the first Entry
                    Objects.equals(entry.key, key)) {
                buckets[index] = entry.next;
                sizeMap--;
                return entry;
            }
            if (entry.next != null) {
                while (entry.next != null) { // checking Entries from the second to the end
                    if (Objects.equals(entry.next.hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(entry.next.key, key)) {
                        retrievedEntry = entry.next;
                        entry.next = entry.next.next;
                        sizeMap--;
                        return retrievedEntry;
                    }
                    entry = entry.next;
                }
            }
        }
        return null;
    }

    public static class Entry<K, V> implements Map.Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;

        private Entry<K, V> next;

        public Entry(K key, V value) {
            this.key = key;
            this.hash = Objects.hashCode(key);
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }
}

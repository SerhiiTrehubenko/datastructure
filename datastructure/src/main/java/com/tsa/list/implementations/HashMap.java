package com.tsa.list.implementations;

import com.tsa.list.interfaces.Map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap<K, V> implements Map<K, V> {
    private static final double NATIVE_LOAD_FACTOR = 0.75;
    private static final int NATIVE_GROW_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 5;
    private final double loadFactor;
    private Object[] buckets;
    private int size;

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


    @Override
    public V put(K key, V value) {
        if (buckets.length * loadFactor <= size) {
            grow();
        }
        var foundEntity = getEntryByKey(key);
        if (foundEntity != null) {
            var oldValue = foundEntity.value;
            foundEntity.value = value;
            return oldValue;
        }
        int index = getIndex(key);
        var newElement = new Entry<>(key, value);
        @SuppressWarnings("unchecked")
        var retrievedEntity = (Entry<K, V>) buckets[index];
        newElement.next = retrievedEntity;
        buckets[index] = newElement;
        size++;
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
        return size;
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
                return counter < size;
            }


            @Override
            public Entry<K, V> next() {
                if (counter >= size) {
                    throw new NoSuchElementException("There is no more elements");
                }

                while (currentEntry == null && position < buckets.length) {
                    @SuppressWarnings("unchecked")
                    var retrievedBucket = (Entry<K, V>) buckets[position];
                    currentEntry = retrievedBucket;
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
                if (counter >= size) {
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

    private int getIndex(K key) {
        return getIndex(key, buckets.length);
    }

    private int getIndex(K key, int bucketsLength) {
        int hashCode = Objects.hashCode(key);
        return Math.abs(hashCode == Integer.MIN_VALUE ? 0 : Objects.hashCode(key) % bucketsLength);
    }

    private boolean checkKeyCoincide(Entry<K, V> entry, K key) {
        return Objects.equals(entry.hashCode(), Objects.hashCode(key)) &&
                Objects.equals(entry.key, key);
    }

    private boolean checkNextKeyCoincide(Entry<K, V> entry, K key) {
        return Objects.equals(entry.next.hashCode(), Objects.hashCode(key)) &&
                Objects.equals(entry.next.key, key);
    }

    private void grow() {
        Object[] newBuckets = new Object[buckets.length * NATIVE_GROW_FACTOR];
        fillBucketsWithDoubledCapacity(newBuckets);
        buckets = newBuckets;
    }


    private void fillBucketsWithDoubledCapacity(Object[] newBuckets) {
        for (Map.Entry<K, V> myEntry : this) {
            var castedEntry = (Entry<K, V>) myEntry;
            int index = getIndex(castedEntry.key, newBuckets.length);
            if (newBuckets[index] == null) {
                castedEntry.next = null;
            } else {
                @SuppressWarnings("unchecked")
                var retrievedMyEntry = (Entry<K, V>) newBuckets[index];
                castedEntry.next = retrievedMyEntry;
            }
            newBuckets[index] = castedEntry;
        }
    }


    private Entry<K, V> getEntryByKey(K key) {
        int index = getIndex(key);

        if (buckets[index] == null) {
            return null;
        }
        @SuppressWarnings("unchecked")
        var entry = (Entry<K, V>) buckets[index];

        if (checkKeyCoincide(entry, key)) {          // checking the first Entry
            return entry;
        }

        while (entry.next != null) {                // checking Entries from the second to the end
            if (checkNextKeyCoincide(entry, key)) {
                return entry.next;
            }
            entry = entry.next;
        }
        return null;
    }


    private Entry<K, V> removeEntryByKey(K key) {
        int index = getIndex(key);

        Entry<K, V> retrievedEntry;

        if (buckets[index] == null) {
            return null;
        }

        @SuppressWarnings("unchecked")
        var entry = (Entry<K, V>) buckets[index];

        if (checkKeyCoincide(entry, key)) {         // checking the first Entry
            buckets[index] = entry.next;
            size--;
            return entry;
        }

        while (entry.next != null) {                // checking Entries from the second to the end
            if (checkNextKeyCoincide(entry, key)) {
                retrievedEntry = entry.next;
                entry.next = entry.next.next;
                size--;
                return retrievedEntry;
            }
            entry = entry.next;
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

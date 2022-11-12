package com.tsa.list.implementations;

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
    private int sizeMap;

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
        if (buckets.length * loadFactor <= sizeMap) {
            grow();
        }
        boolean isAdd = false;
        V retrievedValue = null;
        int index = getIndex(key, buckets.length);


        if (buckets[index] != null) {
            var currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
            if (currentMyEntity.getNext() != null) {
                while (currentMyEntity.getNext() != null) {
                    if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(currentMyEntity.getKey(), key)) {
                        retrievedValue = currentMyEntity.getValue();
                        currentMyEntity.setValue(value);
                        isAdd = true;
                        break;
                    }
                    currentMyEntity = currentMyEntity.getNext();
                }
            } else {
                if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                        Objects.equals(currentMyEntity.getKey(), key)) {
                    retrievedValue = currentMyEntity.getValue();
                    currentMyEntity.setValue(value);
                    isAdd = true;
                }
            }
        }
        if (!isAdd && buckets[index] != null) {
            var currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
            buckets[index] = new MyEntry<>(key, value);
            ((Map.MyEntry<K, V>) buckets[index]).setNext(currentMyEntity);
            sizeMap++;
            isAdd = true;
        }
        if (!isAdd) {
            buckets[index] = new MyEntry<>(key, value);
            sizeMap++;
        }
        return retrievedValue;
    }

    @Override
    public V get(K key) {
        V retrievedValue = null;
        int index = getIndex(key, buckets.length);

        if (buckets[index] != null) {
            var currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
            if (currentMyEntity.getNext() != null) {
                while (currentMyEntity.getNext() != null) {
                    if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(currentMyEntity.getKey(), key)) {
                        retrievedValue = currentMyEntity.getValue();
                        break;
                    }
                    currentMyEntity = currentMyEntity.getNext();
                }
            }
            if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                    Objects.equals(currentMyEntity.getKey(), key)) {
                retrievedValue = currentMyEntity.getValue();
            }

        }
        return retrievedValue;
    }

    @Override
    public boolean containsKey(K key) {

        int index = getIndex(key, buckets.length);

        if (buckets[index] != null) {
            var currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
            if (currentMyEntity.getNext() != null) {
                while (currentMyEntity.getNext() != null) {
                    if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(currentMyEntity.getKey(), key)) {
                        return true;
                    }
                    currentMyEntity = currentMyEntity.getNext();
                }
            }
            return Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                    Objects.equals(currentMyEntity.getKey(), key);
        }
        return false;
    }

    @Override
    public V remove(K key) {
        int count = 0;
        V retrievedValue = null;
        int index = getIndex(key, buckets.length);
        boolean isRemoved = false;
        if (buckets[index] != null) {
            var currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
            if (currentMyEntity.getNext() != null) {
                while (currentMyEntity.getNext() != null) {
                    if (Objects.equals(currentMyEntity.getNext().hashCode(), Objects.hashCode(key)) &&
                            Objects.equals(currentMyEntity.getNext().getKey(), key)) {
                        retrievedValue = currentMyEntity.getNext().getValue();
                        currentMyEntity.setNext(currentMyEntity.getNext().getNext());
                        isRemoved = true;
                        sizeMap--;
                        break;
                    }
                    currentMyEntity = currentMyEntity.getNext();
                    count++;
                }
            }
            if (!isRemoved && count > 0) {
                currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
                if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                        Objects.equals(currentMyEntity.getKey(), key)) {
                    retrievedValue = currentMyEntity.getValue();
                    sizeMap--;
                    buckets[index] = ((Map.MyEntry<K, V>) buckets[index]).getNext();
                }
            } else if (!isRemoved){
                if (Objects.equals(currentMyEntity.hashCode(), Objects.hashCode(key)) &&
                        Objects.equals(currentMyEntity.getKey(), key)) {
                    currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
                    retrievedValue = currentMyEntity.getValue();
                    buckets[index] = null;
                    sizeMap--;
                }
            }

        }
        return retrievedValue;
    }
    @Override
    public int size() {
        return sizeMap;
    }

    @Override
    public Iterator<Map.MyEntry<K, V>> iterator() {
        return new Iterator<>() {
            private int removedLast;
            private int removedQuantity;
            private int counter;
            private int position;
            private Object returnedEntry;
            private Object currentEntry;

            @Override
            public boolean hasNext() {
                return counter < sizeMap;
            }

            @Override
            public Map.MyEntry<K, V> next() {
                if (counter >= sizeMap) throw new NoSuchElementException("There is no more elements");

                while (currentEntry == null && position < buckets.length/*|| buckets[position] == null*/) {
                    currentEntry = buckets[position];
                    position++;

                }

                returnedEntry = currentEntry;
                currentEntry = currentEntry == null ? null : ((Map.MyEntry<K, V>)currentEntry).getNext();

                counter++;
                if (counter == sizeMap) sizeMap = sizeMap - removedQuantity;
                return (Map.MyEntry<K, V>) returnedEntry;
            }

            @Override
            public void remove() {
                int count = 0;
                if (counter == 0) throw new IllegalStateException("\"You have called remove() before next()\"");
                if (removedLast > 0)
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                Map.MyEntry<K, V> inBuckets = null;
                for (Object bucket : buckets) {
                    Map.MyEntry<K, V> returned = (Map.MyEntry<K, V>) returnedEntry;
                    Map.MyEntry<K, V> currentBucket = (Map.MyEntry<K, V>) bucket;
                    if (currentBucket != null) {
                        if (Objects.equals(returned.hashCode(), currentBucket.hashCode()) &&
                                Objects.equals(returned.getKey(), currentBucket.getKey())) {
                            inBuckets = currentBucket;
                            break;
                        }
                    }
                    count++;
                }
                if (inBuckets != null) {
                    buckets[count] = ((Map.MyEntry<K, V>) returnedEntry).getNext();
                } else {
                    Map.MyEntry<K, V> returned = (Map.MyEntry<K, V>) returnedEntry;
                    int index = getIndex(returned.getKey(), buckets.length);
                    Map.MyEntry<K, V> currentMyEntity = (Map.MyEntry<K, V>) buckets[index];
                    while (currentMyEntity.getNext() != null) {
                        if (Objects.equals(currentMyEntity.getNext().hashCode(), Objects.hashCode(returned.hashCode())) &&
                                Objects.equals(currentMyEntity.getNext().getKey(), returned.getKey())) {
                            currentMyEntity.setNext(returned.getNext());
                            break;
                        }
                        currentMyEntity = currentMyEntity.getNext();
                    }

                }
                removedQuantity++;
                if (counter >= sizeMap) {
                    removedLast++;
                    sizeMap--;
                }
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (Map.MyEntry<K, V> myEntry : this) {
            stringJoiner.add(myEntry.toString());
        }
        return stringJoiner.toString();
    }
    public int getBucketsLength() {
        return buckets.length;
    }

    private int getIndex(K key, int size) {
        return Math.abs(Objects.hashCode(key) <= Integer.MIN_VALUE ? Integer.MIN_VALUE : Objects.hashCode(key) % size);
    }


    private void grow() {
        Object[] newBuckets = new Object[buckets.length * INITIAL_GROW_FACTOR];
        for (Map.MyEntry<K, V> myEntry : this) {
            int index = getIndex(myEntry.getKey(), newBuckets.length);
            if (newBuckets[index] == null) {
                myEntry.setNext(null);
                newBuckets[index] = myEntry;
            } else {
                var retrievedMyEntry = newBuckets[index];
                myEntry.setNext((Map.MyEntry<K, V>) retrievedMyEntry);
                newBuckets[index] = myEntry;
            }
        }
        buckets = newBuckets;
    }

    public static class MyEntry<K, V> implements Map.MyEntry<K, V> {
        private final int hash;
        private final K key;
        private V value;

        private Map.MyEntry<K, V> next;

        public MyEntry(K key, V value) {
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
        public Map.MyEntry<K, V> getNext() {
            return next == null ? null : next;
        }

        @Override
        public void setNext(Map.MyEntry<K, V> next) {
            this.next = next;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) {
                return false;
            }

            MyEntry<K, V> myEntry = (MyEntry<K, V>) o;

            if (key != null && myEntry.key != null ? !key.equals(myEntry.key) : myEntry.key != null) return false;
            return value != null && myEntry.value != null ? value.equals(myEntry.value) : myEntry.value == null;
        }

        @Override
        public int hashCode() {
            return hash;
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

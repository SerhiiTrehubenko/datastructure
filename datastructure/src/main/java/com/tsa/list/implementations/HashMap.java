package com.tsa.list.implementations;

import com.tsa.list.interfaces.Map;

import java.util.Iterator;
import java.util.Objects;
import java.util.StringJoiner;

@SuppressWarnings("unchecked")
public class HashMap<K, V> implements Map<K, V> {

    private static final int INITIAL_CAPACITY = 5;
    private final Object[] buckets;

    public HashMap() {
        this.buckets = new Object[INITIAL_CAPACITY];
        for (int i = 0; i < INITIAL_CAPACITY; i++) {
            buckets[i] = new MyArrayList<MyEntry<K,V>>();
        }
    }

    //    Associates the specified value with the specified key in this map (optional operation). If the map previously
//    contained a mapping for the key, the old value is replaced by the specified value. (A map m is said to contain
//    a mapping for a key k if and only if m.containsKey(k) would return true.)
//
//    Params:
    //    key – key with which the specified value is to be associated value – value to be associated with the
    //    specified key
//
//    Returns:
    //    the previous value associated with key, or null if there was no mapping for key. (A null return can also
    //    indicate that the map previously associated null with key, if the implementation supports null values.)
//
//    Throws:
//    UnsupportedOperationException – if the put operation is not supported by this map
//    ClassCastException – if the class of the specified key or value prevents it from being stored in this map
//    NullPointerException – if the specified key or value is null and this map does not permit null keys or values
//    IllegalArgumentException – if some property of the specified key or value prevents it from being
//    stored in this map
    @Override
    public V put(K key, V value) {
        int index = key != null ? key.hashCode() % buckets.length : "null".hashCode() % buckets.length;
        //System.out.println("index: "+index);
        boolean isAdd = false;
        V retrievedValue = null;
        var foundBucked = ((MyArrayList<MyEntry<K, V>>) buckets[index]);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if(Objects.equals(myEntry.getKey(), key)) {
                    retrievedValue = myEntry.getValue();
                    myEntry.setValue(value);
                    isAdd = true;
                }
            }
        } else {
            //System.out.println("empty bucket");
            foundBucked.add(new MyEntry<>(key, value));
            isAdd = true;
        }
        if (!isAdd){
            //System.out.println("new Entry");
            foundBucked.add(new MyEntry<>(key, value));
        }

        return retrievedValue;
    }
//    Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
//    More formally, if this map contains a mapping from a key k to a value v such that Objects.equals(key, k), then
//    this method returns v; otherwise it returns null. (There can be at most one such mapping.)
//    If this map permits null values, then a return value of null does not necessarily indicate that the map contains
//    no mapping for the key; it's also possible that the map explicitly maps the key to null. The containsKey operation
//    may be used to distinguish these two cases.
//
//    Params:
//    key – the key whose associated value is to be returned
//
//    Returns:
//    the value to which the specified key is mapped, or null if this map contains no mapping for the key
//
//    Throws:
//    ClassCastException – if the key is of an inappropriate type for this map (optional)
//    NullPointerException – if the specified key is null and this map does not permit null keys (optional)
    @Override
    public V get(K key) {
        int index = key != null ? key.hashCode() % buckets.length : "null".hashCode() % buckets.length;
        V retrievedValue = null;
        var foundBucked = ((MyArrayList<MyEntry<K, V>>) buckets[index]);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if (Objects.equals(myEntry.getKey(), key)) {
                    retrievedValue = myEntry.getValue();
                }
            }
        }
        return retrievedValue;
    }
//    Returns true if this map contains a mapping for the specified key. More formally, returns true if and only if
//    this map contains a mapping for a key k such that Objects.equals(key, k). (There can be at most one such mapping.)
//
//    Params:
//    key – key whose presence in this map is to be tested
//
//    Returns:
//            true if this map contains a mapping for the specified key
//
//    Throws:
//    ClassCastException – if the key is of an inappropriate type for this map (optional)
//    NullPointerException – if the specified key is null and this map does not permit null keys (optional)
    @Override
    public boolean containsKey(K key) {
        int index = key != null ? key.hashCode() % buckets.length : "null".hashCode() % buckets.length;
        var foundBucked = ((MyArrayList<MyEntry<K, V>>) buckets[index]);
        if (!foundBucked.isEmpty()) {
            for (MyEntry<K, V> myEntry : foundBucked) {
                if (myEntry.getKey() == null && key == null) {
                    return true;
                } else if (Objects.equals(myEntry.getKey(), key)) {
                    return true;
                }
            }
        }
        return false;
    }
//    Removes the mapping for a key from this map if it is present (optional operation). More formally, if this map
//    contains a mapping from key k to value v such that Objects.equals(key, k), that mapping is removed. (The map can
//    contain at most one such mapping.)
//    Returns the value to which this map previously associated the key, or null if the map contained no mapping for the key.
//    If this map permits null values, then a return value of null does not necessarily indicate that the map contained
//    no mapping for the key; it's also possible that the map explicitly mapped the key to null.
//    The map will not contain a mapping for the specified key once the call returns.
//
//    Params:
//    key – key whose mapping is to be removed from the map
//
//    Returns:
//    the previous value associated with key, or null if there was no mapping for key.
//
//    Throws:
//    UnsupportedOperationException – if the remove operation is not supported by this map
//    ClassCastException – if the key is of an inappropriate type for this map (optional)
//    NullPointerException – if the specified key is null and this map does not permit null keys (optional)
    @Override
    public V remove(K key) {
        int index = key != null ? key.hashCode() % buckets.length : "null".hashCode() % buckets.length;
        V retrievedValue = null;
        var foundBucked = ((MyArrayList<MyEntry<K, V>>) buckets[index]);
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
            //System.out.println(((MyArrayList<MyEntry<K, V>>)bucket).size());
            size = size + ((MyArrayList<MyEntry<K, V>>)bucket).size();
        }
        return size;
    }
    @Override
    public MyLinkedList<K> getKeyArray() {
        MyLinkedList<K> arrayKey = new MyLinkedList<>();
        for (Object bucket : buckets) {
            var retrievedBucket = (MyArrayList<MyEntry<K, V>>) bucket;
            for (MyEntry<K, V> entry : retrievedBucket) {
                arrayKey.add(entry.getKey());
            }
        }
        return !arrayKey.isEmpty() ? arrayKey : null;
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<>() {
            private final MyLinkedList<K> arrayKey = getKeyArray();
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

package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class MyArrayList<T> implements List<T> {

    private final static int INIT_CAPACITY = 10;
    private double GROW_FACTOR = 1.5;
    private int size = 0;
    @SuppressWarnings("unchecked")
    private T[] array = (T[]) new Object[INIT_CAPACITY];

    public MyArrayList() {
        this(null, (T[]) null);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(Double customCoefficientCapacity) {
        this(customCoefficientCapacity, (T) null);
    }

    @SuppressWarnings("unchecked")
    public MyArrayList(Double customCoefficientCapacity, T... arg) {
        if (arg != null) {
            if (arg.length > INIT_CAPACITY) this.array = (T[]) new Object[arg.length];

            for (T t : arg) {
                array[this.size++] = t;
            }
        }
        if (customCoefficientCapacity != null &&
                customCoefficientCapacity > 1) GROW_FACTOR = customCoefficientCapacity;
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkBoundaryIncludeIndex(index);
        if (size + 1 >= array.length) {
            grow();
        }

        if ((size - index) == 0) { // add to the tail
            array[index] = value;
            size++;
            return;
        }
        System.arraycopy(array, index, array, index + 1, size - index);

        size++;

        this.array[index] = value;
    }


    @Override
    public T remove(int index) {
        checkBoundaryExcludeIndex(index);
        T removedValue = array[index];

        if ((size - 1) == index) {
            array[index] = null;
            size--;
            return removedValue;
        }

        System.arraycopy(array, index + 1, array, index, size - index);

        size--;

        return removedValue;
    }

    @Override
    public T get(int index) {
        checkBoundaryExcludeIndex(index);
        return array[index];
    }

    @Override
    public T set(T value, int index) {
        checkBoundaryExcludeIndex(index);
        T removed = array[index];
        array[index] = value;
        return removed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void clear() {
        this.size = 0;
        array = (T[]) new Object[array.length];

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        int count = 0;
        for (T valuesInArray : array) {
            if (Objects.equals(valuesInArray, value)) return count;
            count++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        for (int i = size - 1; i > 0; i--) {
            if (Objects.equals(array[i], value)) return i;
        }
        return -1;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private int counter;
            private boolean removedLast;

            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public T next() {
                if (counter >= size) {
                    throw new NoSuchElementException("There are no more elements in the List");
                }
                T value = array[counter];
                counter++;

                return value;

            }

            @Override
            public void remove() {
                if (counter == 0) {
                    throw new IllegalStateException("\"You have called remove() before next()\"");
                }
                if (removedLast) {
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                }
                MyArrayList.this.remove(counter - 1);
                counter--;
                if (counter >= size) removedLast = true;
            }
        };
    }

    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (T values : this) {
            stringJoiner.add(String.valueOf(values));
        }
        return stringJoiner.toString();
    }

    @SuppressWarnings("unchecked")
    private void grow() {
        T[] newBody = (T[]) new Object[(int) ((this.array.length * GROW_FACTOR) + 1)];
        System.arraycopy(array, 0, newBody, 0, size);
        this.array = newBody;
    }

    private void checkBoundaryIncludeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Current size is: " + size +
                    "you have provided: " + index + " is out of the List boundary");
        }
    }

    private void checkBoundaryExcludeIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Current size is: " + size +
                    "you have provided: " + index + " is out of the List size boundary -1");
        }
    }
}

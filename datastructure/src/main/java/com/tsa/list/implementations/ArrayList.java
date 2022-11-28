package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList<T> implements List<T> {

    private final static int INITIAL_CAPACITY = 10;
    private double GROW_FACTOR = 1.5;
    private int size = 0;
    @SuppressWarnings("unchecked")
    private T[] array = (T[]) new Object[INITIAL_CAPACITY];

    public ArrayList() {
        this(null, (T[]) null);
    }

    public ArrayList(Double customCoefficientCapacity) {
        this(customCoefficientCapacity, (T[]) null);
    }

    @SuppressWarnings("unchecked")
    public ArrayList(Double customCoefficientCapacity, T... arg) {
        if (arg != null) {
            if (arg.length > INITIAL_CAPACITY) {
                array = (T[]) new Object[arg.length];
            }

            for (T value : arg) {
                array[size++] = value;
            }
        }
        if (customCoefficientCapacity != null && customCoefficientCapacity > 1) {
            GROW_FACTOR = customCoefficientCapacity;
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkBoundaryIncludeIndex(index);
        if (size == array.length) {
            grow();
        }
        if (size != index) { // add to the middle
            System.arraycopy(array, index, array, index + 1, size - index);
        }
        array[index] = value;
        size++;
    }


    @Override
    public T remove(int index) {
        checkBoundaryExcludeIndex(index);

        T removedValue = array[index];

        if (index != (size - 1)) {
            System.arraycopy(array, index + 1, array, index, size - index);
        }

        array[size - 1] = null;
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
        size = 0;
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
        for (T valueInArray : array) {
            if (Objects.equals(valueInArray, value)) {
                return count;
            }
            count++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(array[i], value)) {
                return i;
            }
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
                ArrayList.this.remove(counter - 1);
                counter--;
                if (counter >= size) {
                    removedLast = true;
                }
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

    private void checkBoundaryIncludeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index can be in range: [0, " + size + "]" + ", you have provided " + index);
        }
    }

    private void grow() {
        @SuppressWarnings("unchecked")
        T[] newArray = (T[]) new Object[(int) ((array.length * GROW_FACTOR) + 1)];
        System.arraycopy(array, 0, newArray, 0, size);
        array = newArray;
    }

    private void checkBoundaryExcludeIndex(int index) {
        if (index == 0 && size == 0) {
            throw new IndexOutOfBoundsException("There is nothing to do, Current size is: " + size);
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index can be in range: [0, " + size + ")" + ", you have provided " + index);
        }
    }
}

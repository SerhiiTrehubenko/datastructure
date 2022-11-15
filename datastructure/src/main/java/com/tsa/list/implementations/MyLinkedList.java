package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class MyLinkedList<T> implements List<T> {
    private Node<T> start;
    private Node<T> tail;
    private int size = 0;

    public MyLinkedList() {
    }

    public MyLinkedList(java.util.List<T> list) {
        for (T t : list) {
            add(t);
        }
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        checkBoundaryIncludeIndex(index);
        if (index == 0) { // add to the BEGINNING
            addToTheStart(value);
            size++;
            return;
        }
        if (index == size) { //add to the TAIL
            addToTheTail(value);
            size++;
            return;
        }
        if (index < (size / 2)) { //add to the MIDDLE
            addValueToTheFirstHalf(value, index);
        } else {
            addValueToTheSecondHalf(value, index);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        checkBoundaryExcludeIndex(index);
        T removedValue;
        if (index == 0) { // remove from The BEGINNING
            if (tail == null) {
                removedValue = start.value;
                start = null;
                size--;
                return removedValue;
            }
            removedValue = start.value;
            start = start.next;
            start.previous = null;
            size--;
            return removedValue;
        }
        if (index == size - 1) { // remove from The TAIL
            removedValue = tail.value;
            tail = tail.previous;
            tail.next = null;
            size--;
            return removedValue;
        }
        // remove from The MIDDLE
        var removedNode = getNodeByIndex(index);
        removedValue = removedNode.value;
        removedNode.previous.next = removedNode.next;
        removedNode.next.previous = removedNode.previous;
        size--;
        return removedValue;
    }

    @Override
    public T get(int index) {
        checkBoundaryExcludeIndex(index);
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        checkBoundaryExcludeIndex(index);
        T replacedValue;
        Node<T> currentNode = getNodeByIndex(index);
        replacedValue = currentNode.value;
        currentNode.value = value;
        return replacedValue;
    }

    @Override
    public void clear() {
        size = 0;
        start = null;
        tail = null;
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
        for (T valueInList : this) {
            if (Objects.equals(valueInList, value)) return true;
        }
        return false;
    }

    @Override
    public int indexOf(T value) {
        int nonCoincidence = -1;
        int count = 0;
        for (T valueInList : this) {
            if (Objects.equals(valueInList, value)) return count;
            count++;
        }
        return nonCoincidence;

    }

    @Override
    public int lastIndexOf(T value) {
        int count = size - 1;
        for (Node<T> node = tail; node != null; node = node.previous) {
            if (Objects.equals(node.value, value)) {
                return count;
            }
            count--;
        }
        return -1;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        } else {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            for (T value : this) {
                stringJoiner.add(String.valueOf(value));
            }
            return stringJoiner.toString();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int counter;
            private int removedLast;
            private Node<T> current = start;

            @Override
            public boolean hasNext() {
                return counter < size;
            }

            @Override
            public T next() {
                T value;
                if (counter >= size) throw new NoSuchElementException("There is no more elements in the List");
                value = current.value;
                current = current.next;
                counter++;

                return value;
            }

            @Override
            public void remove() {
                if (counter == 0) throw new IllegalStateException("\"You have called remove() before next()\"");
                if (removedLast > 0)
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                MyLinkedList.this.remove(counter - 1);
                counter--;
                if (counter >= size) removedLast++;
            }
        };
    }

    private void addToTheStart(T value) {
        if (start == null) {
            start = new Node<>(value);
        } else {
            Node<T> newElement = new Node<>(null, start, value);
            start.previous = newElement;
            start = newElement;
        }
    }

    private void addToTheTail(T value) {
        Node<T> newElement;
        if (tail == null) {
            newElement = new Node<>(start, null, value);
            start.next = newElement;
        } else {
            newElement = new Node<>(tail, null, value);
            tail.next = newElement;
        }
        tail = newElement;
    }

    private void addValueToTheFirstHalf(T value, int index) {
        int count = 0;
        var currentNode = start;
        while (count != index) {
            currentNode = currentNode.next;
            count++;
        }
        Node<T> newElement = new Node<>(currentNode.previous, currentNode, value);
        currentNode.previous.next = newElement;
        currentNode.previous = newElement;
    }

    private void addValueToTheSecondHalf(T value, int index) {
        int count = 0;
        var currentNode = tail;
        while (count != size - index) {
            currentNode = currentNode.previous;
            count++;
        }
        Node<T> newElement = new Node<>(currentNode, currentNode.next, value);
        currentNode.next.previous = newElement;
        currentNode.next = newElement;
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (index <= (size / 2)) {
            currentNode = start;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = tail;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void checkBoundaryIncludeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Current size is: " + size +
                    " you have provided: " + index + " is out of the List boundary");
        }
    }

    private void checkBoundaryExcludeIndex(int index) {
        if (index == 0 && size == 0) {
            throw new RuntimeException("There is nothing to do, Current size is: " + size);
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Current size is: " + size +
                    " you have provided: " + index + " is out of the List size boundary -1");
        }
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        public Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }

        public Node(T value) {
            this.value = value;
            this.previous = null;
            this.next = null;
        }
    }
}

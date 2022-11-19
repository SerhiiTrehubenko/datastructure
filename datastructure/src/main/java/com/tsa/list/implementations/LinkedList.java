package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T> {
    private Node<T> start;
    private Node<T> tail;
    private int size;

    public LinkedList() {
    }

    public LinkedList(java.util.List<T> list) {
        for (T value : list) {
            add(value);
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
        } else if (index == size) { //add to the TAIL
            addToTheTail(value);
        } else if (isFirstHalfOfList(index)) { //add to the MIDDLE
            addValueToTheFirstHalf(value, index);
        } else {
            addValueToTheSecondHalf(value, index);
        }
        size++;
    }

    @Override
    public T remove(int index) {
        checkBoundaryExcludeIndex(index);
        Node<T> removedNode;
        if (size == 1) {
            removedNode = start;
            start = tail = null;
        } else if (index == 0) {
            removedNode = start;
            start = start.next;
            start.previous = null;
        } else if (index == (size - 1)) {
            removedNode = tail;
            tail = tail.previous;
            tail.next = null;
        } else {
            Node<T> foundNode = getNodeByIndex(index);
            removedNode = foundNode;
            foundNode.previous.next = foundNode.next;
            foundNode.next.previous = foundNode.previous;
        }
        size--;
        return removedNode.value;
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
        return indexOf(value) != -1;
    }

    @Override
    public int indexOf(T value) {
        int count = 0;
        for (T valueInList : this) {
            if (Objects.equals(valueInList, value)) {
                return count;
            }
            count++;
        }
        return -1;
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
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (T value : this) {
            stringJoiner.add(String.valueOf(value));
        }
        return stringJoiner.toString();
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private boolean isLastNext;
            private Node<T> current = start;
            private Node<T> previous;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("There is no more elements in the List");
                }
                T value = current.value;
                previous = current;
                current = current.next;

                return value;
            }

            @Override
            public void remove() {
                if (current == start) {
                    throw new IllegalStateException("\"You have called remove() before next()\"");
                }
                if (isLastNext) {
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                }
                if (previous == tail) {
                    isLastNext = true;
                }
                removeNode(previous);
                size--;
            }
        };
    }

    private void addToTheStart(T value) {
        if (start == null) {
            start = new Node<>(value);
            tail = start;
        } else {
            Node<T> newElement = new Node<>(null, start, value);
            start.previous = newElement;
            start = newElement;
        }
    }

    private void addToTheTail(T value) {
        Node<T> newElement = new Node<>(tail, null, value);
        tail.next = newElement;

        tail = newElement;
    }

    private void addValueToTheFirstHalf(T value, int index) {
        var currentNode = getNodeByIndex(index);
        Node<T> newElement = new Node<>(currentNode.previous, currentNode, value);
        currentNode.previous.next = newElement;
        currentNode.previous = newElement;
    }

    private void addValueToTheSecondHalf(T value, int index) {
        var currentNode = getNodeByIndex(index - 1);
        Node<T> newElement = new Node<>(currentNode, currentNode.next, value);
        currentNode.next.previous = newElement;
        currentNode.next = newElement;
    }

    private boolean isFirstHalfOfList(int index) {
        return index < (size / 2);
    }

    private Node<T> getNodeByIndex(int index) {
        Node<T> currentNode;
        if (isFirstHalfOfList(index)) {
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

    private void removeNode(Node<T> foundNode) {
        if (foundNode == start && foundNode == tail) {
            start = tail = null;
        } else if (foundNode == start) {
            start = start.next;
            start.previous = null;
        } else if (foundNode == tail) {
            tail = tail.previous;
            tail.next = null;
        } else {
            foundNode.previous.next = foundNode.next;
            foundNode.next.previous = foundNode.previous;
        }
    }

    private void checkBoundaryIncludeIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("index can be in range: [0, " + size + "]" + ", you have provided " + index);
        }
    }

    private void checkBoundaryExcludeIndex(int index) {
        if (index == 0 && size == 0) {
            throw new IndexOutOfBoundsException("There is nothing to do, Current size is: " + size);
        }
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("index can be in range: [0, " + size + ")" + ", you have provided " + index);
        }
    }

    private static class Node<T> {
        private Node<T> previous;
        private Node<T> next;
        private T value;

        public Node(T value) {
            this(null, null, value);
        }

        public Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }
}

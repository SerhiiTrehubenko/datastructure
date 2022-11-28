package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList<T> implements List<T> {
    private Node<T> first;
    private Node<T> last;
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
        if (isEmpty()) {
            first = last = new Node<>(value);
        } else if (index == 0) { // add to the BEGINNING
            Node<T> newElement = new Node<>(value);
            newElement.next = first;
            first.previous = newElement;
            first = newElement;
        } else if (index == size) { //add to the TAIL
            Node<T> newElement = new Node<>(value);
            newElement.previous = last;
            last.next = newElement;
            last = newElement;
        } else { //add to the MIDDLE
            var currentNode = getNodeByIndex(index);
            Node<T> newElement = new Node<>(currentNode.previous, currentNode, value);
            currentNode.previous.next = newElement;
            currentNode.previous = newElement;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        Node<T> removedNode = getNodeByIndex(index);
        removeNode(removedNode);

        return removedNode.value;
    }

    @Override
    public T get(int index) {
        return getNodeByIndex(index).value;
    }

    @Override
    public T set(T value, int index) {
        Node<T> currentNode = getNodeByIndex(index);
        T replacedValue = currentNode.value;
        currentNode.value = value;
        return replacedValue;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
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
        int index = 0;
        for (T valueInList : this) {
            if (Objects.equals(valueInList, value)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        int index = size - 1;
        for (Node<T> node = last; node != null; node = node.previous) {
            if (Objects.equals(node.value, value)) {
                return index;
            }
            index--;
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
            private Node<T> current = first;
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
                if (current == first) {
                    throw new IllegalStateException("\"You have called remove() before next()\"");
                }
                if (isLastNext) {
                    throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                }
                if (previous == last) {
                    isLastNext = true;
                }
                removeNode(previous);
            }
        };
    }

    private boolean isFirstHalfOfList(int index) {
        return index < (size / 2);
    }

    private Node<T> getNodeByIndex(int index) {
        checkBoundaryExcludeIndex(index);
        Node<T> currentNode;
        if (isFirstHalfOfList(index)) {
            currentNode = first;
            for (int i = 0; i < index; i++) {
                currentNode = currentNode.next;
            }
        } else {
            currentNode = last;
            for (int i = size - 1; i > index; i--) {
                currentNode = currentNode.previous;
            }
        }
        return currentNode;
    }

    private void removeNode(Node<T> foundNode) {
        if (foundNode == first && foundNode == last) {
            first = last = null;
        } else if (foundNode == first) {
            first = first.next;
            first.previous = null;
        } else if (foundNode == last) {
            last = last.previous;
            last.next = null;
        } else {
            foundNode.previous.next = foundNode.next;
            foundNode.next.previous = foundNode.previous;
        }
        size--;
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

        private Node(T value) {
            this(null, null, value);
        }

        private Node(Node<T> previous, Node<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
    }
}

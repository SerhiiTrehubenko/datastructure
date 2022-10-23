package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;
@SuppressWarnings("unchecked")
public class MyLinkedList <T> implements List<T> {
    private Nodes<T> theFirstElement;
    private Nodes<T> theLastElement;
    private int population = 0;
    public MyLinkedList() {}

    public MyLinkedList(T... args) {
        for (T arg : args) {
            add(arg);
        }
    }
    public MyLinkedList(java.util.List<T> list) {
        for (T t : list) {
            add(t);
        }
    }
    @Override
    public void add(T value) {
        this.add(value, population);
    }

    @Override
    public void add(T value, int index) {
        if (index < 0 || index > population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        if(index == 0) {
            if (theFirstElement == null) {
                theFirstElement = new Nodes<>(value);
                population++;
                return;
            }
            Nodes<T> newElement = new Nodes<>(null, theFirstElement, value);
            theFirstElement.setPrevious(newElement);
            theFirstElement = newElement;
        } else if (population - index == 0) {
            if (theLastElement == null) {
                theLastElement = new Nodes<>(theFirstElement, value);
                theFirstElement.setNext(theLastElement);
                population++;
                return;
            }
            Nodes<T> newElement = new Nodes<>(theLastElement, value);
            theLastElement.setNext(newElement);
            theLastElement = newElement;
        } else if (index <= (population/2)){
            Nodes<T> current = theFirstElement;
            for (int i = 0; i <= index; i++) {
                current = current.getNext();
            }
           Nodes<T> newElement = new Nodes<>(current.getPrevious(), current, value);
            current.getPrevious().setNext(newElement);
            current.setPrevious(newElement);
        } else {
            Nodes<T> current = theLastElement;
            for (int i = population-1; i > index; i--) {
                current = current.getPrevious();
            }
            Nodes<T> newElement = new Nodes<>(current.getPrevious(), current, value);
            current.getPrevious().setNext(newElement);
            current.setPrevious(newElement);
        }
        population++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removedValue;
        if(index == 0) {
            removedValue = theFirstElement.getValue();
            theFirstElement = theFirstElement.getNext();
            theFirstElement.setPrevious(null);
        } else if ((population-1) - index == 0) {
            removedValue = theLastElement.getValue();
            theLastElement.getPrevious().setNext(null);
            theLastElement = theLastElement.getPrevious();
        } else if (index <= (population/2)){
            Nodes<T> current = theFirstElement;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            removedValue = current.getValue();
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        } else {
            Nodes<T> current = theLastElement;
            for (int i = population; i > index ; i--) {
                current = current.getPrevious();
            }
            removedValue = current.getValue();
            //current.decreaseIndexNext();
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
        }
        population--;
        return removedValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        Nodes<T> current;
        if (index <= (population/2)){
            current = theFirstElement;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = theLastElement;
            for (int i = population-1; i > index ; i--) {
                current = current.getPrevious();
            }
        }
        return current.getValue();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T replacedValue;
        Nodes<T> current;
        if (index <= (population/2)){
            current = theFirstElement;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
        } else {
            current = theLastElement;
            for (int i = population-1; i > index ; i--) {
                current = current.getPrevious();
            }
        }
        replacedValue = current.getValue();
        current.setValue(value);
        return replacedValue;
    }

    @Override
    public void clear() {
        population = 0;
        theFirstElement = null;
        theLastElement = null;
    }

    @Override
    public int size() {
        return population;
    }

    @Override
    public boolean isEmpty() {
        return population == 0;
    }

    @Override
    public boolean contains(T value) {
        for (T t : this) {
            if (Objects.equals(t, value)) return true;
        }
        return false;
    }

    @Override
    public int indexOf(T value) {
        int nonCoincidence = -1;
        int count = 0;
        for (T t : this) {
            if (Objects.equals(t, value)) return count;
            count++;
        }
        return nonCoincidence;

    }

    @Override
    public int lastIndexOf(T value) {
        int nonCoincidence = -1;
        int count = population-1;
            for (Nodes<T> nodes = theLastElement; nodes != null; nodes = nodes.getPrevious()) {
                if (Objects.equals(nodes.getValue(), value)) {
                    return count;
                }
                count--;
            }

        return nonCoincidence;
    }

    @Override
    public String toString() {
        if (population == 0) {
            return "[]";
        } else {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            for (T t : this) {
                stringJoiner.add(String.valueOf(t));
            }
            return stringJoiner.toString();
        }

    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {

            private int counter;
            private int removedLast;
            private Nodes<T> current = theFirstElement;
            @Override
            public boolean hasNext() {
                return counter < population;
            }

            @Override
            public T next() {
                T value;
                if(counter >= population) throw new NoSuchElementException("There is no more elements in the List");
                value = current.getValue();
                current = current.getNext();
                counter++;

                return value;
            }

            @Override
            public void remove() {
                if (counter == 0) throw new IllegalStateException("\"You have called remove() before next()\"");
                if(removedLast > 0) throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                MyLinkedList.this.remove(counter-1);
                counter--;
                if(counter >= population) removedLast++;
             }
        };
    }

    private static class Nodes<T> {
        private Nodes<T> previous;
        private Nodes<T> next;
        private T value;

        public Nodes(Nodes<T> previous, Nodes<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
        }
        public Nodes(T value) {
            this.value = value;
            this.previous = null;
            this.next = null;
        }
        public Nodes(Nodes<T> previous, T value) {
            this.previous = previous;
            this.next = null;
            this.value = value;
        }

        public Nodes<T> getNext() {
            return next;
        }

        public Nodes<T> getPrevious() {
            return previous;
        }

        public T getValue() {
            return value;
        }

        public void setPrevious(Nodes<T> previous) {
            this.previous = previous;
        }

        public void setNext(Nodes<T> next) {
            this.next = next;
        }

        public void setValue(T value) {
            this.value = value;
        }
    }
}

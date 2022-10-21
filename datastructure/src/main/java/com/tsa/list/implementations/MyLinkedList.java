package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
@SuppressWarnings("unchecked")
public class MyLinkedList <T> implements List<T> {
    private Nodes<T> theFirstElement;
    private MyLinkedList<T>.Nodes<T> theLastElement;
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
                theFirstElement = new MyLinkedList<T>.Nodes<>(value);
                population++;
                return;
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(null, theFirstElement, value);
            theFirstElement.setPrevious(newElement);
            theFirstElement = newElement;
            theFirstElement.setPosition(index);
            theFirstElement.increaseIndexNext();
        } else if ((population) - index == 0) {
            if (theLastElement ==null) {
                theLastElement = new MyLinkedList<T>.Nodes<>(theFirstElement, value);
                theFirstElement.setNext(theLastElement);
                population++;
                return;
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(theLastElement, value);
            theLastElement.setNext(newElement);
            theLastElement = newElement;
        } else if (index <= (population/2)){
            MyLinkedList<T>.Nodes<T> current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(current.getPrevious(), current, value);
            current.getPrevious().setNext(newElement);
            current.setPrevious(newElement);
            newElement.setPosition(index);
            newElement.increaseIndexNext();
        } else {
            MyLinkedList<T>.Nodes<T> current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(current.getPrevious(), current, value);
            current.getPrevious().setNext(newElement);
            current.setPrevious(newElement);
            newElement.setPosition(index);
            newElement.increaseIndexNext();
        }
        population++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= theFirstElement.getIndex())
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removedValue;
        if(index == 0) {
            theFirstElement.decreaseIndexNext();
            theFirstElement.setIndex(theFirstElement.getIndex()-1);
            removedValue = theFirstElement.getValue();
            theFirstElement = theFirstElement.getNext();
            theFirstElement.setPrevious(null);
        } else if ((theFirstElement.getIndex()-1) - index == 0) {
            removedValue = theLastElement.getValue();
            theLastElement.getPrevious().setNext(null);
            theLastElement.setIndex(theLastElement.getIndex()-1);
            theLastElement = theLastElement.getPrevious();
        } else if (index <= (theFirstElement.getIndex()/2)){
            MyLinkedList<T>.Nodes<T> current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
            removedValue = current.getValue();
            current.decreaseIndexNext();
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            current.setIndex(current.getIndex()-1);
        } else {
            MyLinkedList<T>.Nodes<T> current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
            removedValue = current.getValue();
            current.decreaseIndexNext();
            current.getPrevious().setNext(current.getNext());
            current.getNext().setPrevious(current.getPrevious());
            current.setIndex(current.getIndex()-1);
        }
        population--;
        return removedValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= theFirstElement.getIndex())
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        Nodes<T> current;
        if (index <= (theFirstElement.getIndex()/2)){
            current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
        } else {
            current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
        }
        return current.getValue();
    }

    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= theFirstElement.getIndex())
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T replacedValue;
        if (index <= (theFirstElement.getIndex()/2)){
            Nodes<T> current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
            replacedValue = current.getValue();
            current.setValue(value);
        } else {
            Nodes<T> current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
            replacedValue = current.getValue();
            current.setValue(value);
        }
        return replacedValue;
    }

    @Override
    public void clear() {
        theFirstElement.setIndex(0);
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
        if (value == null) {
            for (Nodes<T> nodes = theFirstElement; nodes != null; nodes = nodes.getNext()) {
                if (nodes.getValue() == null) {
                    return true;
                }
            }
        } else {
            for (Nodes<T> nodes = theFirstElement; nodes != null; nodes = nodes.getNext()) {
                if (nodes.getValue() != null && nodes.getValue().equals(value)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int indexOf(T value) {
        int nonCoincidence = -1;
        if (value == null) {
            for (Nodes<T> nodes = theFirstElement; nodes != null; nodes = nodes.getNext()) {
                if (nodes.getValue() == null) {
                    return nodes.getPosition();
                }
            }
        } else {
            for (Nodes<T> nodes = theFirstElement; nodes != null; nodes = nodes.getNext()) {
                if (nodes.getValue() != null && nodes.getValue().equals(value)) {
                    return nodes.getPosition();
                }
            }
        }
        return nonCoincidence;

    }

    @Override
    public int lastIndexOf(T value) {
        int nonCoincidence = -1;
        if (value == null) {
            for (Nodes<T> nodes = theLastElement; nodes != null; nodes = nodes.getPrevious()) {
                if (nodes.getValue() == null) {
                    return nodes.getPosition();
                }
            }
        } else {
            for (Nodes<T> nodes = theLastElement; nodes != null; nodes = nodes.getPrevious()) {
                if (nodes.getValue() != null && nodes.getValue().equals(value)) {
                    return nodes.getPosition();
                }
            }
        }
        return nonCoincidence;
    }

    @Override
    public String toString() {
        if (population == 0) {
            return "[]";
        } else {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            /*Nodes<T> current = theFirstElement;
            while (current.getNext() != null) {
                stringJoiner.add(String.valueOf(current.getValue()));
                current = current.getNext();
            }
            stringJoiner.add(String.valueOf(current.getValue()));*/
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
            private T value;
            @Override
            public boolean hasNext() {
                return counter < theFirstElement.getIndex();
            }

            @Override
            public T next() {
                if(counter >= theFirstElement.getIndex()) throw new NoSuchElementException("There is no more elements in the List");
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
                /*if ((counter-1) == 0) {
                    Nodes<T> previous = current.getPrevious();
                    previous.decreaseIndexNext();
                    previous.setIndex(current.getIndex()-1);
                    previous.getNext().setPrevious(null);
                    theFirstElement = theFirstElement.getNext();
                } else if ((counter) == theFirstElement.getIndex()) {
                    theLastElement.setIndex(theLastElement.getIndex()-1);
                    theLastElement.getPrevious().setNext(null);
                    theLastElement = theLastElement.previous;
                } else {
                    Nodes<T> previous = current.getPrevious();
                    previous.decreaseIndexNext();
                    previous.setIndex(previous.getIndex() - 1);
                    previous.getPrevious().setNext(previous.getNext());
                    previous.getNext().setPrevious(current.getPrevious());
                }
                population--;*/
                counter--;
                if(counter >= population) removedLast++;
             }
        };
    }

    private class Nodes<T> {
        private Nodes<T> previous;
        private Nodes<T> next;
        private T value;
        private static int index;
        private int position;

        public Nodes(Nodes<T> previous, Nodes<T> next, T value) {
            this.previous = previous;
            this.next = next;
            this.value = value;
            this.position = index++;
        }
        public Nodes(T value) {
            this.value = value;
            this.previous = null;
            this.next = null;
            this.position = index++;
        }
        public Nodes(Nodes<T> previous, T value) {
            this.previous = previous;
            this.next = null;
            this.value = value;
            this.position = index++;
        }

        public void increment() {
            position++;
        }

        public void increaseIndexNext() {

            if (next != null){
                next.increment();
                next.increaseIndexNext();
            }

        }
        public void decrement() {
            position--;
        }

        public void decreaseIndexNext() {
            if (next != null){
                next.decrement();
                next.decreaseIndexNext();
            }

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


        public int getIndex() {
            return index;
        }

        public void setIndex(int newIndex) {
            index = newIndex;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

    }
}

package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.StringJoiner;

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
    public void add(Object value) {
       if (theFirstElement == null) {
           theFirstElement = new MyLinkedList<T>.Nodes<>((T) value);
       } else {
           MyLinkedList<T>.Nodes<T> current = theFirstElement;
           while (current.getNext() != null) {
               current = current.getNext();
           }
           current.setNext(new MyLinkedList<T>.Nodes<>(current, (T) value));
           this.theLastElement = current.getNext();
       }
        population++;
    }

    @Override
    public void add(Object value, int index) {
        if (index < 0 || index > theFirstElement.getIndex())
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        if(index == 0) {
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(null, theFirstElement, (T) value);
            theFirstElement.setPrevious(newElement);
            theFirstElement = newElement;
            theFirstElement.setPosition(index);
            theFirstElement.increaseIndexNext();
        } else if ((theFirstElement.getIndex()) - index == 0) {
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(theLastElement, (T) value);
            theLastElement.setNext(newElement);
            theLastElement = newElement;
        } else if (index <= (theFirstElement.getIndex()/2)){
            MyLinkedList<T>.Nodes<T> current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(current.getPrevious(), current, (T) value);
            current.getPrevious().setNext(newElement);
            current.setPrevious(newElement);
            newElement.setPosition(index);
            newElement.increaseIndexNext();
        } else {
            MyLinkedList<T>.Nodes<T> current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
            MyLinkedList<T>.Nodes<T> newElement = new MyLinkedList<T>.Nodes<>(current.getPrevious(), current, (T) value);
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
        T removedValue = null;
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
    public T set(Object value, int index) {
        if (index < 0 || index >= theFirstElement.getIndex())
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T replacedValue = null;
        if (index <= (theFirstElement.getIndex()/2)){
            Nodes<T> current = theFirstElement;
            while (current.getPosition() != index) {
                current = current.getNext();
            }
            replacedValue = current.getValue();
            current.setValue((T) value);
        } else {
            Nodes<T> current = theLastElement;
            while (current.getPosition() != index) {
                current = current.getPrevious();
            }
            replacedValue = current.getValue();
            current.setValue((T) value);
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
    public boolean contains(Object value) {
        Nodes<T> current = theFirstElement;
        while (!current.getValue().equals(value) && current.getNext() != null) {
            current = current.getNext();
        }
        if (current.getValue().equals(value)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int indexOf(Object value) {
        int nonCoincidence = -1;
        Nodes<T> current = theFirstElement;
        while (!current.getValue().equals(value) && current.getNext() != null) {
            current = current.getNext();
        }
        if (current.getValue().equals(value)) {
            return current.getPosition();
        } else {
            return nonCoincidence;
        }
    }

    @Override
    public int lastIndexOf(Object value) {
        int nonCoincidence = -1;
        Nodes<T> current = theLastElement;
        while (!current.getValue().equals(value) && current.getPrevious() != null) {
            current = current.getPrevious();
        }
        if (current.getValue().equals(value)) {
            return current.getPosition();
        } else {
            return nonCoincidence;
        }
    }

    @Override
    public String toString() {
        if (population == 0) {
            return "[]";
        } else {
            StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
            Nodes current = theFirstElement;
            while (current.getNext() != null) {
                stringJoiner.add(String.valueOf(current.getValue()));
                current = current.getNext();
            }
            stringJoiner.add(String.valueOf(current.getValue()));
            return stringJoiner.toString();
        }

    }

    @Override
    public Iterator iterator() {
        return new Iterator() {

            int counter;
            Nodes<T> current = theFirstElement;
            T value;
            @Override
            public boolean hasNext() {
                return counter < theFirstElement.getIndex();
            }

            @Override
            public Object next() {
                value = current.getValue();
                current = current.getNext();
                counter++;

                return value;
            }

            @Override
            public void remove() {
                if ((counter-1) == 0) {
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
                population--;
                counter--;
             }
        };
    }

    public class Nodes<T> {
        private Nodes previous = null;
        private Nodes next= null;;
        private T value = null;;
        private static int index;
        private int position;
        private static int circle = 0;

        public static StringBuilder stringBuilder;

        public Nodes(Nodes previous, Nodes next, T value) {
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

        public void refreshCircle() {
            this.circle = 0;
        }

        public Nodes getNext() {
            return next;
        }

        public Nodes getPrevious() {
            return previous;
        }

        public T getValue() {
            return value;
        }

        public void setPrevious(Nodes previous) {
            this.previous = previous;
        }

        public void setNext(Nodes next) {
            this.next = next;
        }

        public void setValue(T value) {
            this.value = value;
        }


        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }


        public int searchValueForward(T value) {

            if (this.value.equals(value)) {
                return position;
            } else {
                if (circle > 1) return -1;
                else return next.searchValueForward(value);
            }
        }
        public int searchValueBackward(T value) {

            if (this.value.equals(value)) {
                return position;
            } else {
                if (circle > 1) return -1;
                else return previous.searchValueBackward(value);
            }
        }
    }
}

package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

public class MyLinkedList <T> implements List {

    private static final int INIT_SIZE = 12;

    private int population = 0;
    private Nodes<T>[] body;

    private int[] removedNumbers;
    private int removedPopulation;
    private Nodes<T> theLastInBody;
    private Nodes<T> theFirstInBody;


    public MyLinkedList() {
        body = new Nodes[INIT_SIZE];
        removedNumbers = new int[INIT_SIZE];
    }

    @Override
    public void add(Object value) {
        if ((population+1) >= body.length) inflation();
        if (population == 0) {
            body[population] = new Nodes<>((T) value);
            body[population].setFirst(true);
            this.theFirstInBody = body[population];
            this.theLastInBody = body[population];
            population++;
            return;
        }
        if (removedPopulation > 0) {
            body[removedNumbers[removedPopulation]] = new Nodes<>(this.theLastInBody, this.theFirstInBody, (T) value);

        } else body[population] = new Nodes<>(this.theLastInBody, this.theFirstInBody, (T) value);

        if ((population-1) == 0) {
            this.theLastInBody.setNext(body[population]);
            this.theLastInBody.setPrevious(body[population]);
            this.theLastInBody = body[population];
        } else {
            if (removedPopulation > 0) {
                this.theLastInBody.setNext(body[removedNumbers[removedPopulation]]);
                this.theLastInBody = body[removedNumbers[removedPopulation]];
                removedPopulation--;
            } else {
                this.theLastInBody.setNext(body[population]);
                this.theLastInBody = body[population];
            }
        }
        population++;
    }

    @Override
    public void add(Object value, int index) {
        if (index < 0 || index > population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        if ((population+1) >= body.length) inflation();
        if (index == 0) {
            Nodes nodeInBody = null;
            for (int i=0; i < population; i++) {
                if((body[i] != null) && (body[i].getPosition() == index)) nodeInBody = body[i];
            }
            nodeInBody.isFirst = false;
            if (removedPopulation > 0) {
                body[removedNumbers[removedPopulation]] = new Nodes<>(theLastInBody, nodeInBody, (T) value);
                body[removedNumbers[removedPopulation]].isFirst = true;
                nodeInBody.setPrevious(body[removedNumbers[removedPopulation]]);
                this.theLastInBody.setNext(body[removedNumbers[removedPopulation]]);
                this.theFirstInBody = body[removedNumbers[removedPopulation]];
                body[removedNumbers[removedPopulation]].setPosition(index);
                body[removedNumbers[removedPopulation]].increaseIndexNext();
                removedPopulation--;
            } else {
                body[population] = new Nodes<>(theLastInBody, nodeInBody, (T) value);
                body[population].isFirst = true;
                nodeInBody.setPrevious(body[population]);
                this.theLastInBody.setNext(body[population]);
                this.theFirstInBody = body[population];
                body[population].setPosition(index);
                body[population].increaseIndexNext();
            }
            population++;
            return;
        }
        if ((population - index) == 0) {
            Nodes nodeInBody = null;
            for (int i=0; i < population; i++) {
                if((body[i] != null) && (body[i].getPosition() == index-1)) nodeInBody = body[i];
            }
            if (removedPopulation > 0) {
                body[removedNumbers[removedPopulation]] = new Nodes<>(nodeInBody, nodeInBody.next, (T) value);
                nodeInBody.setNext(body[removedNumbers[removedPopulation]]);
                theFirstInBody.setPrevious(body[removedNumbers[removedPopulation]]);
                body[removedNumbers[removedPopulation]].setPosition(index);
                removedPopulation--;
                theLastInBody = body[removedNumbers[removedPopulation]];
            } else {
                body[population] = new Nodes<>(nodeInBody, nodeInBody.next, (T) value);
                nodeInBody.setNext(body[population]);
                theFirstInBody.setPrevious(body[population]);
                body[population].setPosition(index);
                theLastInBody = body[population];
            }
            population++;
            return;
        }
        Nodes nodeInBody = null;
        for (int i=0; i < population; i++) {
            if((body[i] != null) && (body[i].getPosition() == index)) nodeInBody = body[i];
        }
        if (removedPopulation > 0) {
            body[removedNumbers[removedPopulation]] = new Nodes<>(nodeInBody.getPrevious(), nodeInBody, (T) value);
            nodeInBody.getPrevious().setNext(body[removedNumbers[removedPopulation]]);
            nodeInBody.setPrevious(body[removedNumbers[removedPopulation]]);
            body[removedNumbers[removedPopulation]].setPosition(index);
            body[removedNumbers[removedPopulation]].increaseIndexNext();
            removedPopulation--;
        } else {
            body[population] = new Nodes<>(nodeInBody.getPrevious(), nodeInBody, (T) value);
            nodeInBody.getPrevious().setNext(body[population]);
            nodeInBody.setPrevious(body[population]);
            body[population].setPosition(index);
            body[population].increaseIndexNext();
        }
        population++;
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        Nodes removed = null;
        for (int i=0; i < population; i++) {
            if((body[i] != null) && (body[i].getPosition() == index)) {
                removed = body[i];
                removedNumbers[removedPopulation] = i;
                removedPopulation++;
                body[i] = null;
                population--;
            }
        }
        if (removed != null) {
            Nodes prevoius = removed.getPrevious();
        removed.getPrevious().setNext(removed.getNext());
        removed.getNext().setPrevious(removed.getPrevious());
        removed.setPrevious(null);
        removed.setNext(null);
        prevoius.decreaseIndexNext();
        prevoius.setIndex(prevoius.getIndex()-1);
            return (T) removed.getValue();
        } else return null;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        for (int i=0; i < population; i++) {
            if((body[i] != null) && (body[i].getPosition() == index)) {
                return body[i].getValue();
            }
        }
        System.out.println("return null");
        return null;
    }
    private void inflation() {
        Nodes<T> [] newBody = new Nodes[((body.length*3)/2)+1];
        for(int i = 0; i <= population; i++) {
            newBody[i] = body[i];
        }
        this.body = newBody;
    }
    @Override
    public T set(Object value, int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        Nodes set = null;
        T returnedValue = null;
        for (int i=0; i < population; i++) {
            if((body[i] != null) && (body[i].getPosition() == index)) {
                set = body[i];
            }
        }
        if (set != null) {
            returnedValue = (T)set.getValue();
            set.setValue(value);
            return returnedValue;
        } else return null;
    }

    @Override
    public void clear() {
        population = 0;
        removedPopulation = 0;
        theFirstInBody.setIndex(0);
        theFirstInBody = null;
        theLastInBody = null;
        this.body = new Nodes[body.length];
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
        boolean result = false;
        for (int i=0; i < population; i++) {
            if((body[i] != null) && (body[i].getValue().equals((T)value))) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public int indexOf(Object value) {
        theFirstInBody.refreshCircle();
        return theFirstInBody.searchValueForward((T)value);
    }

    @Override
    public int lastIndexOf(Object value) {
        theLastInBody.refreshCircle();
        return theLastInBody.searchValueBackward((T)value);
    }

    @Override
    public String toString() {
        if (theFirstInBody == null) {
            return "[]";
        } else return theFirstInBody.toString();
    }

    class Nodes<T> {
        private Nodes previous = null;
        private Nodes next= null;;
        private T value = null;;
        private boolean isFirst = false;
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
        public Nodes(Nodes previous, Nodes next) {
            this.previous = previous;
            this.next = next;
            this.position = index++;
        }
        public void increment() {
            position++;
        }

        public void increaseIndexNext() {
            if (next.isFirst) {

            } else {
                next.increment();
                next.increaseIndexNext();
            }

        }
        public void decrement() {
            position--;
        }

        public void decreaseIndexNext() {
            if (next.isFirst) {

            } else {
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

        public void setFirst(boolean first) {
            isFirst = first;
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

        @Override
        public String toString() {
            stringBuilder  = new StringBuilder();
            toStr();

            return stringBuilder.toString();
        }

        public int searchValueForward(T value) {
            if (isFirst) circle++;
            if (this.value.equals(value)) {
                return position;
            } else {
                if (circle > 1) return -1;
                else return next.searchValueForward(value);
            }
        }
        public int searchValueBackward(T value) {
            if (isFirst) circle++;
            if (this.value.equals(value)) {
                return position;
            } else {
                if (circle > 1) return -1;
                else return previous.searchValueBackward(value);
            }
        }

        public void toStr() {
            if (isFirst) {
                stringBuilder.append("[");
            }
            if (!next.isFirst) {
                stringBuilder.append(value + ", ");
                next.toStr();
            } else {
                stringBuilder.append(value);
                stringBuilder.append("]");
            }
        }
    }
}

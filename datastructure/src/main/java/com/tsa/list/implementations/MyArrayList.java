package com.tsa.list.implementations;

import com.tsa.list.interfaces.List;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.StringJoiner;
@SuppressWarnings("unchecked")
public class  MyArrayList <T> implements List<T> {

    private final static int INIT_CAPACITY = 10;
    private double INIT_CAPACITY_COEFFICIENT = 1.5;
    private int population = 0;
    private Object[] body = new Object[INIT_CAPACITY];
    public MyArrayList() {
        this(null, (T[])null);
    }

    public MyArrayList(Double customCoefficientCapacity) {
        this(customCoefficientCapacity, (T) null);
    }
    public MyArrayList(Double customCoefficientCapacity, T... arg)  {
        if(arg != null) {
            if (arg.length > INIT_CAPACITY) this.body = new Object[arg.length];

            for (T t : arg) {
                body[this.population++] = t;
            }
        }
        if (customCoefficientCapacity != null &&
                customCoefficientCapacity > 1) INIT_CAPACITY_COEFFICIENT = customCoefficientCapacity;
    }

    @Override
    public void add(T value) {
        if (this.population == this.body.length) {
            inflateBody();
        }
        this.body[population++] = value;
    }
    @Override
    public void add(T value, int index) {
        if (index < 0 || index > population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        if (population+1 >= this.body.length) {
            inflateBody();
        }
        int mediator = population - index;
        if (mediator == 0) {
            this.body[index] = value;
            population++;
            return;
        }
        for (int i = population; i > population - mediator; i--) {
            this.body[i] = this.body[i-1];
        }
        population++;
        this.body[index] = value;
    }


    @Override
    public T remove(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removedValue = (T) body[index];

        for (int i = index; i < population-1; i++) {
            this.body[i] = this.body[i+1];
        }
        this.body[population-1] = null;
        population--;
        return removedValue;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        return (T)body[index];
    }
    @Override
    public T set(T value, int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removed = (T)body[index];
        this.body[index] = value;
        return removed;
    }



    @Override
    public void clear() {
        this.population = 0;
        body = new Object[body.length]; //I was almost blind by the flash of this thought

    }

    @Override
    public int size() {
        return this.population;
    }

    @Override
    public boolean isEmpty() {
        return population == 0;
    }

    @Override
    public boolean contains(T value) {
        if (value == null) {
            for (int i = 0; i < population; i++) {
                if (this.body[i] == null) return true;
            }
        } else {
            for (int i = 0; i < population; i++) {
                if (this.body[i] != null && this.body[i].equals(value)) return true;
            }
        }

        return false;
    }

    @Override
    public int indexOf(T value) {
        if (value == null) {
            for (int i = 0; i < population; i++) {
                if (this.body[i] == null) return i;
            }
        } else {
            for (int i = 0; i < population; i++) {
                if (this.body[i] != null && this.body[i].equals(value)) return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        if (value == null) {
            for (int i = population-1; i > 0; i--) {
                if (this.body[i] == null) return i;
            }
        } else {
            for (int i = population-1; i > 0; i--) {
                if (this.body[i] != null && this.body[i].equals(value)) return i;
            }
        }

        return -1;
    }
    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            int counter;
            int removedLast;
            @Override
            public boolean hasNext() {
                return counter < population;
            }
            @Override
            public T next() {
                if(counter >= population) throw new NoSuchElementException("There are no more elements in the List");
                T value = (T)body[counter];
                counter++;

                return value;

            }
            @Override
            public void remove() {
                if(counter == 0) throw new IllegalStateException("\"You have called remove() before next()\"");
                if(removedLast > 0) throw new IllegalStateException("\"You have called remove() after \"the last\" next()\"");
                MyArrayList.this.remove(counter-1);
                counter--;
                if(counter >= population) removedLast++;
            }
        };
    }
    @Override
    public String toString() {
        StringJoiner stringJoiner = new StringJoiner(", ", "[", "]");
        for (T t : this) {
            stringJoiner.add(String.valueOf(t));
        }
        return stringJoiner.toString();
    }

    private void inflateBody() {
        Object[] newBody = new Object[(int)((this.body.length*INIT_CAPACITY_COEFFICIENT)+1)];
        for (int i =0; i < population; i++) {
            newBody[i] = body[i];
        }
        this.body = newBody;
    }
}

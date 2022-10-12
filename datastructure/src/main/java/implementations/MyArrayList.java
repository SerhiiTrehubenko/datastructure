package implementations;

import interfaces.List;

public class  MyArrayList <T> implements List {

    private final int INIT_SIZE = 5;
    private int population = 0;
    private int countInflation = 0;
    private T[] body = (T[]) new Object[INIT_SIZE];

    public MyArrayList() {
    }

    public MyArrayList(T... arg) {
        if (arg.length > INIT_SIZE) this.body = (T[]) new Object[arg.length];
        for (T t : arg) {
            body[this.population++] = t;
        }
    }

    @Override
    public void add(Object value) {
        if (this.population == this.body.length) {
            inflateBody((T) value);
        }
        this.body[population++] = (T) value;
    }

    @Override
    public void add(Object value, int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        if (population+1 >= this.body.length) {
            inflateBody((T) value, index);
            if(countInflation > 0) population--;
            countInflation++;
            return;
        }
        int mediator = population - (index + 1);
        if (mediator == 0) {
            System.out.println("if mediator");
            T oldValue = this.body[index];
            this.body[index] = (T)value;
            this.body[population] = oldValue;
            population++;
            return;
        }
        System.out.println("mediator= " + mediator);
        for (int i = population; i >= population - mediator; i--) {
            this.body[i] = this.body[i-1];
        }
        population++;
        this.body[index] = (T) value;
    }


    @Override
    public Object remove(int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removedValue = body[index];
        int mediator = population - index;
        for (int i = index; i < population-1; i++) {
            this.body[i] = this.body[i+1];
        }
        this.body[population-1] = null;
        population--;
        return removedValue;
    }

    @Override
    public Object get(int index) {
        return body[index];
    }

    @Override
    public Object set(Object value, int index) {
        if (index < 0 || index >= population)
            throw new IndexOutOfBoundsException(index + " is out of the List boundary");
        T removed = this.body[index];
        this.body[index] = (T)value;
        return removed;
    }

    @Override
    public void clear() {
        this.population = 0;
        for (T t : body) {
            t = null;
        }
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
    public boolean contains(Object value) {
        for (int i = 0; i < population; i++) {
            if (this.body[i].equals(value)) return true;
        }
        return false;
    }

    @Override
    public int indexOf(Object value) {

        for (int i = 0; i < population; i++) {
            if(this.body[i].equals((T)value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = population-1; i > 0; i--) {
            if(this.body[i].equals((T)value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i <= population-1; i++) {
            if(i == 0) {
                stringBuilder.append(this.body[i] + ", ");
                i++;
            }
            if(i == this.population-1) {
                stringBuilder.append(this.body[i]);
                continue;
            }
            stringBuilder.append(this.body[i] + ", ");

        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    private void inflateBody(T value) {
        population = 0;
        T[] newBody = (T[]) new Object[(this.body.length*3)/2+1];
        for (T t : body) {
            newBody[population++] = t;
        }
        this.body = newBody;
        this.body[population] = (T)value;
    }
    private void inflateBody(T value, int index) {
        population = 0;
        T[] newBody = (T[]) new Object[((this.body.length*3)/2)+1];
        for (T t : this.body) {
            if (population == index) {
                newBody[index] = value;
                population++;
            }
            newBody[population++] = t;
        }
        this.body = newBody;
    }
}

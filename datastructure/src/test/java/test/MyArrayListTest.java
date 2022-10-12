package test;

import implementations.MyArrayList;
import interfaces.List;
import org.junit.*;

public class MyArrayListTest {

    private List<Integer> array;

    @Before
    public void init() {
        array = new MyArrayList<>();
        for (int i=0; i < 5; i++) {
            array.add(i);
        }

    }
    @Test
    public void add() {
        System.out.println(array);
        for (int i = 0; i < 20; i++) {
            array.add(i);
        }
        System.out.println(array);
    }
    @Test
    public void addIndex() {
        System.out.println(array);
        array.add(99, 0);
        array.add(100, 2);
        array.add(101, array.size()-1);
        System.out.println(array);

    }
    @Test
    public void remove() {
        System.out.println(array);
        System.out.println(array.remove(0));
        System.out.println(array.remove(1));
        System.out.println(array.remove(array.size()-1));
        System.out.println(array);
    }
    @Test
    public void get() {
        System.out.println(array);
        System.out.println(array.get(3));
    }
    @Test
    public void set() {
        System.out.println(array);
        array.set(99, 1);
        array.set(100, 4);
        System.out.println(array);
    }
    @Test
    public void arrayList() {
        System.out.println(array.size());
        System.out.println(array.isEmpty());
        System.out.println(array.contains(4));
        System.out.println(array);
    }
    @Test
    public void cleanAndIsEmpty() {
        System.out.println(array);
        array.clear();
        System.out.println("isEmpty: "+array.isEmpty());
        System.out.println(array);
    }
    @Test
    public void contains() {
        System.out.println(array);
        System.out.println("contains \"3\"?: "+array.contains(3));
        System.out.println("contains \"20\"?: "+array.contains(20));
    }
    @Test
    public void indexes() {
        System.out.println(array);
        array.add(0, 2);
        System.out.println("firstIndex: " + array.indexOf(0));
        System.out.println("firstIndex: " + array.lastIndexOf(0));
    }
    @Test
    public void toStringCustom() {
        System.out.println(array);

    }

}

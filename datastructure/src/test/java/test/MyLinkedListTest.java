package test;

import implementations.MyLinkedList;
import interfaces.List;
import org.junit.*;
import static org.junit.Assert.*;

public class MyLinkedListTest {

    List<Integer> array;
    @Before
    public void init() {
        array = new MyLinkedList<Integer>();
        for (int i=0; i <= 5; i++) {
            array.add(i);
        }
    }

    @Test
    public void add() {
        System.out.println(array);
        array.add(99);
        System.out.println(array);
    }
    @Test
    public void addIndex() {
        System.out.println(array);
        array.add(99, 2);
        array.add(100, 0);
        array.add(101, array.size()-1);
        System.out.println(array);
    }
    @Test
    public void removeAndSizeAndGet() {
        System.out.println(array);
        assertTrue(array.size() == 6);
        array.add(99, 2);
        System.out.println(array);
        System.out.println("removed: " + array.remove(2));
        System.out.println(array);
        array.add(100);
        System.out.println(array);
        System.out.println("size: " + array.size());
        System.out.println("get index /6/: " + array.get(6));
    }
    @Test
    public void set() {
        System.out.println(array);
        array.set(99, 0);
        array.set(100, 3);
        System.out.println(array);
    }
    @Test
    public void clearAndIsEmpty() {
        System.out.println(array);
        System.out.println("Clearing");
        array.clear();
        System.out.println("isEmpty: " + array.isEmpty());
        System.out.println("Populating the empty LinkedList");
        for (int i=5; i <=10; i++) {
            array.add(i);
        }
        System.out.println(array);
        System.out.println("isEmpty: " + array.isEmpty());
        System.out.println("get index /3/: " + array.get(3));
    }
    @Test
    public void indexOfAndLastIndexOf() {
        System.out.println(array);
        //array.add(99, 0);
        array.add(99, 2);
        array.add(99, 5);
        System.out.println(array);
        System.out.println("firstIndex of /99/: " + array.indexOf(99));
        System.out.println("lastIndex of /99/: " + array.lastIndexOf(99));
        System.out.println("firstIndex of /3/: " + array.indexOf(3));
        System.out.println("lastIndex of /3/: " + array.lastIndexOf(3));

    }

}

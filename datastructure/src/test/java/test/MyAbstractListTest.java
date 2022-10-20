package test;

import com.tsa.list.interfaces.List;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public abstract class MyAbstractListTest {

    protected List<Integer> array;

    @Test
    public void addToTheTail() {
        assertEquals(0, array.size());
        array.add(10);
        array.add(11);
        array.add(12);
        assertEquals(3, array.size());
        System.out.println(array);
        assertEquals(10, array.get(0));
    }

    @Test
    public void addByTheIndexInsideTheRange() {
        populateList();
        assertEquals(5, array.size());
        array.add(10,0);
        array.add(11,2);
        array.add(12, 7);
        assertEquals(8, array.size());
        assertEquals(12, array.get(7));
    }

    @Test
    public void addByTheIndexIsBiggerThanMaximumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.add(10, array.size()+1));
    }

    @Test
    public void addByTheIndexIsLessThanMinimumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.add(10, -1));
    }
    @Test
    public void removeByTheIndexTheIndexInsideTheRange() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(0, array.remove(0));
        System.out.println(array);
        assertEquals(4, array.size());
        assertEquals(4, array.remove(3));
        System.out.println(array);
        assertEquals(3, array.size());

    }
    @Test
    public void removeByTheIndexIsBiggerThanMaximumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.remove(array.size()));
    }

    @Test
    public void removeByTheIndexIsLessThanMinimumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.remove(-1));
    }
    @Test
    public void setByTheIndexTheIndexInsideTheRange() {
        populateList();
        assertEquals(5, array.size());
        array.set(99, 2);
        assertEquals(99, array.get(2));

    }
    @Test
    public void setByTheIndexIsBiggerThanMaximumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.set(100, array.size()));
    }

    @Test
    public void setByTheIndexIsLessThanMinimumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.set(100, -1));
    }

    @Test
    public void getByTheIndexTheIndexInsideTheRange() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(2, array.get(2));

    }
    @Test
    public void getByTheIndexIsBiggerThanMaximumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.get(array.size()));
    }

    @Test
    public void getByTheIndexIsLessThanMinimumRange() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, ()->array.get(-1));
    }
    @Test
    public void testClear() {
        populateList();
        assertEquals(5, array.size());
        array.clear();
        assertEquals(0, array.size());
    }
    @Test
    public void testIsEmptyTrue() {
        populateList();
        assertEquals(5, array.size());
        array.clear();
        assertTrue(array.isEmpty());
    }
    @Test
    public void testIsEmptyFalse() {
        populateList();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());
    }
    @Test
    public void testContainsTrue() {
        populateList();
        assertEquals(5, array.size());
        assertTrue(array.contains(3));
    }
    @Test
    public void testContainsFalse() {
        populateList();
        assertEquals(5, array.size());
        assertFalse(array.contains(20));
    }
    @Test
    public void testIndexOfPositive() {
        populateList();
        assertEquals(5, array.size());
        array.add(1,3);
        assertEquals(1, array.indexOf(1));
    }
    @Test
    public void testIndexOfNegative() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(-1, array.indexOf(20));
    }
    @Test
    public void testLastIndexOfPositive() {
        populateList();
        assertEquals(5, array.size());
        array.add(1,3);
        System.out.println(array);
        assertEquals(3, array.lastIndexOf(1));
    }
    @Test
    public void testLastIndexOfNegative() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(-1, array.lastIndexOf(20));
    }
    @Test
    public void testToString() {
        array.add(10);
        array.add(20);
        array.add(30);
        array.add(99,0);
        assertEquals(4, array.size());
        assertEquals("[99, 10, 20, 30]", array.toString());
    }
    @Test
    public void testIterate() {
        populateList();
        array.remove(2);
        array.add(99, array.size());
        for (Integer integer : array) {
            System.out.println(integer);
        }
    }
    @Test
    public void testRemoveUnderIterating() {
        populateList();
        System.out.println(array.size());
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(0)) iterator.remove();
            if (value.equals(2)) iterator.remove();
            if (value.equals(4)) iterator.remove();
        }
        System.out.println(array);
        assertEquals(2, array.size());
    }

    private void populateList() {
        for (int i = 0; i < 5; i++) {
            array.add(i);
        }
    }

}

package test;

import com.tsa.list.interfaces.List;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class MyAbstractListTest {

    protected List<Integer> array;

    @Test
    void testAdd() {
        populateList();
        assertEquals(5, array.size());
    }

    @Test
    public void testAddByIndexToTheTail() {
        assertEquals(0, array.size());
        array.add(10, 0);
        array.add(11, 1);
        array.add(12, 2);
        assertEquals(3, array.size());
        assertEquals(10, array.get(0));
    }

    @Test
    public void testAddByTheIndexToTheMiddle() {
        populateList();
        assertEquals(5, array.size());
        array.add(10, 1);
        array.add(11, 2);
        array.add(12, 4);
        assertEquals(8, array.size());
        assertEquals(4, array.indexOf(12));
        assertEquals(2, array.indexOf(11));
        assertEquals(1, array.indexOf(10));
    }

    @Test
    public void testAddByTheIndexIsBiggerThanMaximumRange() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(10, 6), "index can be in range: [0, 5] , you have provided 6");
    }

    @Test
    public void testAddByTheIndexIsLessThanMinimumRange() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(10, -1), "index can be in range: [0, 5] , you have provided -1");
    }

    @Test
    public void testRemoveWhenListEmpty() {
        assertThrows(RuntimeException.class, () -> array.remove(0), "There is nothing to do, Current size is: 0");
    }

    @Test
    public void testRemoveWhenListContainOneElement() {
        array.add(20);
        assertEquals(1, array.size());
        assertEquals(20, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    public void testRemoveFromTailWhenListContainsTwoElements() {
        array.add(20);
        array.add(21);
        assertEquals(21, array.remove(1));
        assertEquals(1, array.size());
    }

    @Test
    public void testRemoveByIndexInMiddleOfList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(1, array.remove(1));
        assertEquals(4, array.size());
        assertEquals(3, array.remove(2));
        assertEquals(3, array.size());
    }

    @Test
    public void testRemoveByIndexIsEqualsListSize() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(5));
    }

    @Test
    public void testRemoveByIndexIsLessThanListSize() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(-1));
    }

    @Test
    public void testSetValueToStartWhenListEmpty() {
        assertThrows(RuntimeException.class, () -> array.set(99, 0), "There is nothing to do, Current size is: 0");
    }

    @Test
    public void testSetByIndexIsMiddleList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(2, array.set(99, 2));
        assertEquals(5, array.size());
        assertEquals(99, array.get(2));

    }

    @Test
    public void testSetByIndexIsEqualsListSize() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(100, array.size()));
    }

    @Test
    public void testSetByIndexIsLessThanListSize() {
        populateList();
        assertThrows(IndexOutOfBoundsException.class, () -> array.set(100, -1));
    }

    @Test
    public void testGetByIndexWhenListEmpty() {
        assertThrows(RuntimeException.class, () -> array.get(0), "There is nothing to do, Current size is: 0");
    }

    @Test
    public void testGetByIndexWhenListHasOneElement() {
        array.add(99);
        assertEquals(99, array.get(0));
    }

    @Test
    public void testGetByIndexIsMiddleOfList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(2, array.get(2));
        assertEquals(3, array.get(3));

    }

    @Test
    public void testGetByIndexIsAtEntOfList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(4, array.get(4));

    }

    @Test
    public void testGetByIndexIsEqualSize() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(5));
    }

    @Test
    public void testGetByIndexIsLessThanSize() {
        populateList();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1));
    }

    @Test
    public void testClearWhenListEmpty() {
        assertEquals(0, array.size());
        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    public void testClearWhenListWithValues() {
        populateList();
        assertEquals(5, array.size());
        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    public void testIsEmptyWhenListEmpty() {
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    public void testIsEmptyWhenToListWasAddedAndThenDeletedValue() {
        assertEquals(0, array.size());
        array.add(10);
        assertEquals(1, array.size());
        assertEquals(10, array.remove(0));
        assertTrue(array.isEmpty());
    }

    @Test
    public void testIsEmptyWhenListContainsValues() {
        populateList();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());
    }

    @Test
    public void testContainsWhenListEmpty() {
        assertEquals(0, array.size());
        assertFalse(array.contains(99));
    }

    @Test
    public void testContainsWhenValueWasAddedAndThenWasDeleted() {
        populateList();
        assertEquals(5, array.size());
        array.add(99, 3);
        assertTrue(array.contains(99));
        assertEquals(99, array.remove(3));
        assertFalse(array.contains(99));
    }

    @Test
    public void testContainsWhenListContainsValues() {
        populateList();
        array.add(null);
        assertEquals(6, array.size());
        assertTrue(array.contains(3));
        assertTrue(array.contains(null));
    }

    @Test
    public void testContainsWhenValueInNotInList() {
        populateList();
        assertEquals(5, array.size());
        assertFalse(array.contains(20));
    }

    @Test
    public void testIndexOfListIsEmpty() {
        assertEquals(0, array.size());
        assertEquals(-1, array.indexOf(1));
    }

    @Test
    public void testIndexOfTheValueIsAtTheStart() {
        populateList();
        assertEquals(0, array.indexOf(0));

    }

    @Test
    public void testIndexOfTheValueIsAtTheMiddle() {
        populateList();
        assertEquals(2, array.indexOf(2));
        array.add(99, 2);
        assertEquals(2, array.indexOf(99));
    }

    @Test
    public void testIndexOfTheValueIsAtTheEnd() {
        populateList();
        assertEquals(4, array.indexOf(4));
        array.add(99, 5);
        assertEquals(5, array.indexOf(99));
    }

    @Test
    public void testIndexOfTheValueIsNotInList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(-1, array.indexOf(20));
    }

    @Test
    public void testLastIndexOfListIsEmpty() {
        assertEquals(0, array.size());
        assertEquals(-1, array.lastIndexOf(1));
    }

    @Test
    public void testLastIndexOfTheValueAtTheStartOfList() {
        populateList();
        assertEquals(0, array.lastIndexOf(0));
    }

    @Test
    public void testLastIndexOfTheValueAtTheMiddleOfList() {
        populateList();
        assertEquals(2, array.lastIndexOf(2));
    }

    @Test
    public void testLastIndexOfTheValueAtTheEndOfList() {
        populateList();
        assertEquals(5, array.size());
        array.add(99);
        assertEquals(5, array.lastIndexOf(99));
    }

    @Test
    public void testLastIndexOfTheValueIsNotInList() {
        populateList();
        assertEquals(5, array.size());
        assertEquals(-1, array.lastIndexOf(20));
    }

    @Test
    public void testToString() {
        array.add(10);
        array.add(20);
        array.add(30);
        array.add(99, 0);
        assertEquals(4, array.size());
        assertEquals("[99, 10, 20, 30]", array.toString());
    }

    @Test
    public void testIterate() {
        int count = 0;
        populateList();
        assertEquals(5, array.size());
        array.remove(2);
        assertEquals(4, array.size());
        array.add(99, 4);
        assertEquals(5, array.size());
        for (Integer integer : array) {
            count++;
        }
        assertEquals(5, count);
    }

    @Test
    public void testRemoveUnderIterating() {
        populateList();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(0)) iterator.remove();
            if (value.equals(2)) iterator.remove();
            if (value.equals(4)) iterator.remove();
        }
        assertEquals(2, array.size());
    }

    @Test
    public void testThrowExceptionIteratorRemoveBeforeNext() {
        populateList();
        Iterator<Integer> iterator = array.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void testThrowExceptionIteratorRemoveAfterLastNext() {
        populateList();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void testThrowExceptionIteratorNextAfterLastNext() {
        populateList();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    private void populateList() {
        for (int i = 0; i < 5; i++) {
            array.add(i);
        }
    }

}

package test;

import com.tsa.list.interfaces.List;
import org.junit.jupiter.api.Test;


import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractListTddTest {
    protected List<Integer> array;

    @Test
    void testEmptyListSizeNil() {
        assertEquals(0, array.size());
    }

    @Test
    void testAddOneValueSizeOne() {
        array.add(99);
        assertEquals(1, array.size());
    }

    @Test
    void testAddSeveralValuesSizeEqualsCount() {
        int count = 5;
        for (int i = 0; i < count; i++) {
            array.add(i);
        }
        assertEquals(count, array.size());
    }

    @Test
    void testAddByIndexInEmptyListToTailSizeEqualsThree() {
        array.add(99, 0);
        assertEquals(99, array.get(0));

        array.add(100, 1);
        assertEquals(100, array.get(1));

        array.add(101, 2);
        assertEquals(101, array.get(2));

        assertEquals(3, array.size());
    }

    @Test
    void testCombineAddAndAndByIndex() {
        array.add(99);
        array.add(100, 1);
        assertEquals(2, array.size());
    }

    @Test
    void testAddByIndexToStartGetFromStart() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 0);
        assertEquals(99, array.get(0));

        assertEquals(6, array.size());
    }

    @Test
    void testAddByIndexToSecondPositionGetFromSecondPosition() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 1);
        assertEquals(99, array.get(1));

        assertEquals(6, array.size());
    }

    @Test
    void testAddByIndexToMiddle() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 2);
        assertEquals(99, array.get(2));

        assertEquals(6, array.size());
    }

    @Test
    void testAddByIndexToTailOneValue() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 5);
        assertEquals(99, array.get(5));

        assertEquals(6, array.size());
    }

    @Test
    void testAddByIndexSeveralValuesToStarWithSameIndex() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 0);
        assertEquals(99, array.get(0));
        assertEquals(6, array.size());

        array.add(100, 0);
        assertEquals(100, array.get(0));
        assertEquals(7, array.size());

        assertEquals(99, array.get(1));
        assertEquals(0, array.get(2));
    }

    @Test
    void testAddByIndexSeveralValuesToEnd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 5);
        assertEquals(99, array.get(5));
        assertEquals(6, array.size());

        array.add(100, 6);
        assertEquals(100, array.get(6));
        assertEquals(7, array.size());

        assertEquals(4, array.get(4));
    }

    @Test
    void testAddByIndexSeveralValuesToMiddleWithSameIndex() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 3);
        assertEquals(99, array.get(3));
        assertEquals(6, array.size());

        array.add(100, 3);
        assertEquals(100, array.get(3));
        assertEquals(7, array.size());

        assertEquals(99, array.get(4));
        assertEquals(3, array.get(5));
        assertEquals(2, array.get(2));
    }

    @Test
    void testAddByIndexSeveralValuesWithIndexSizeMinusOne() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.add(99, 4);
        assertEquals(99, array.get(4));
        assertEquals(6, array.size());

        array.add(100, 5);
        assertEquals(100, array.get(5));
        assertEquals(7, array.size());

        assertEquals(4, array.get(6));
        assertEquals(3, array.get(3));
    }

    @Test
    void testAddPopulateListSeveralTimesWithSimpleAdd() {
        for (int i = 0; i < 5; i++) {
            populateListSimpleAdd();
        }
        assertEquals(5 * 5, array.size());
    }

    @Test
    void testAddPopulateListSeveralTimesWithAddByIndex() {
        for (int i = 0; i < 5; i++) {
            populateListWithAddByIndexDifferentOrder();
        }
        assertEquals(5 * 7, array.size());
    }

    @Test
    void testAddByIndexIsBiggerThanListSizeThrowOutOfBoundaryException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(99, 6));
    }

    @Test
    void testAddByIndexIsLessThanNilThrowOutOfBoundaryException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.add(99, -1));
    }

    @Test
    void testRemoveFromEmptyListThrowIndexOutOfBoundsException() {
        assertEquals(0, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.remove(0),
                "There is nothing to do, Current size is: 0");
    }

    @Test
    void testRemoveOneValueAddedBySimpleAdd() {
        assertEquals(0, array.size());
        array.add(99);
        assertEquals(1, array.size());
        assertEquals(99, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testRemoveOneValueFromStarSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertEquals(0, array.remove(0));
        assertEquals(4, array.size());
    }

    @Test
    void testRemoveSeveralValueFromStarSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(4, array.size());

        assertEquals(1, array.remove(0));
        assertEquals(3, array.size());

        assertEquals(2, array.remove(0));
        assertEquals(2, array.size());
    }

    @Test
    void testRemoveOneValueFromEndSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(4, array.remove(4));
        assertEquals(4, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromEndSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(4, array.remove(4));
        assertEquals(4, array.size());

        assertEquals(3, array.remove(3));
        assertEquals(3, array.size());

        assertEquals(2, array.remove(2));
        assertEquals(2, array.size());
    }

    @Test
    void testRemoveOneValuesFromMiddleSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(2, array.remove(2));
        assertEquals(4, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromMiddleSeveralValuesAddedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(1, array.remove(1));
        assertEquals(4, array.size());

        assertEquals(2, array.remove(1));
        assertEquals(3, array.size());
    }

    @Test
    void testRemoveOneValueFromStartValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(6, array.size());

        assertEquals(102, array.get(0));
    }

    @Test
    void testRemoveSeveralValueFromStartValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(6, array.size());
        assertEquals(102, array.get(0));

        assertEquals(102, array.remove(0));
        assertEquals(5, array.size());
        assertEquals(101, array.get(0));

        assertEquals(101, array.remove(0));
        assertEquals(4, array.size());
        assertEquals(105, array.get(0));
    }

    @Test
    void testRemoveOneValueFromEndValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(103, array.remove(6));
        assertEquals(6, array.size());
        assertEquals(104, array.get(5));
    }

    @Test
    void testRemoveSeveralValueFromEndValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(103, array.remove(6));
        assertEquals(6, array.size());
        assertEquals(104, array.get(5));

        assertEquals(104, array.remove(5));
        assertEquals(5, array.size());
        assertEquals(99, array.get(4));
    }

    @Test
    void testRemoveOneValueFromMiddleValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(105, array.remove(3));
        assertEquals(6, array.size());
        assertEquals(99, array.get(3));
    }

    @Test
    void testRemoveSeveralValueFromMiddleValuesAddedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(105, array.remove(3));
        assertEquals(6, array.size());
        assertEquals(99, array.get(3));

        assertEquals(99, array.remove(3));
        assertEquals(5, array.size());
        assertEquals(104, array.get(3));

        assertEquals(104, array.remove(3));
        assertEquals(4, array.size());
        assertEquals(103, array.get(3));

        assertEquals(103, array.remove(3));
        assertEquals(3, array.size());
    }

    @Test
    void testRemoveAllValueFromListThroughStartValuesAddedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(4, array.size());
        assertEquals(1, array.get(0));

        assertEquals(1, array.remove(0));
        assertEquals(3, array.size());
        assertEquals(2, array.get(0));

        assertEquals(2, array.remove(0));
        assertEquals(2, array.size());
        assertEquals(3, array.get(0));

        assertEquals(3, array.remove(0));
        assertEquals(1, array.size());
        assertEquals(4, array.get(0));

        assertEquals(4, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testRemoveAllValueFromListThroughEndValuesAddedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(4, array.remove(4));
        assertEquals(4, array.size());
        assertEquals(3, array.get(3));

        assertEquals(3, array.remove(3));
        assertEquals(3, array.size());
        assertEquals(2, array.get(2));

        assertEquals(2, array.remove(2));
        assertEquals(2, array.size());
        assertEquals(1, array.get(1));

        assertEquals(1, array.remove(1));
        assertEquals(1, array.size());
        assertEquals(0, array.get(0));

        assertEquals(0, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testRemoveAllValueFromListThroughStartValuesAddedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(6, array.size());
        assertEquals(102, array.get(0));

        assertEquals(102, array.remove(0));
        assertEquals(5, array.size());
        assertEquals(101, array.get(0));

        assertEquals(101, array.remove(0));
        assertEquals(4, array.size());
        assertEquals(105, array.get(0));

        assertEquals(105, array.remove(0));
        assertEquals(3, array.size());
        assertEquals(99, array.get(0));

        assertEquals(99, array.remove(0));
        assertEquals(2, array.size());
        assertEquals(104, array.get(0));

        assertEquals(104, array.remove(0));
        assertEquals(1, array.size());
        assertEquals(103, array.get(0));

        assertEquals(103, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testRemoveAllValueFromListThroughEndValuesAddedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(103, array.remove(6));
        assertEquals(6, array.size());
        assertEquals(104, array.get(5));

        assertEquals(104, array.remove(5));
        assertEquals(5, array.size());
        assertEquals(99, array.get(4));

        assertEquals(99, array.remove(4));
        assertEquals(4, array.size());
        assertEquals(105, array.get(3));

        assertEquals(105, array.remove(3));
        assertEquals(3, array.size());
        assertEquals(101, array.get(2));

        assertEquals(101, array.remove(2));
        assertEquals(2, array.size());
        assertEquals(102, array.get(1));

        assertEquals(102, array.remove(1));
        assertEquals(1, array.size());
        assertEquals(100, array.get(0));

        assertEquals(100, array.remove(0));
        assertEquals(0, array.size());
    }

    @Test
    void testRemoveAllValuesThenAddValuesWithSimpleAdd() {
        int count;
        populateListSimpleAdd();
        assertEquals(5, array.size());
        count = array.size();
        for (int i = 0; i < count; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());

        populateListSimpleAdd();
        assertEquals(5, array.size());
    }

    @Test
    void testRemoveAllValuesThenAddValuesWithAddByIndex() {
        int count;
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        count = array.size();
        for (int i = 0; i < count; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());

        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromStartThenAddSeveralValuesToStartWithAddByIndex() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(1, array.remove(0));
        assertEquals(3, array.size());

        array.add(99, 0);
        array.add(100, 0);
        assertEquals(100, array.get(0));
        assertEquals(99, array.get(1));
        assertEquals(2, array.get(2));

        assertEquals(5, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromMiddleThenAddSeveralValuesToMiddleWithAddByIndex() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(2, array.remove(2));
        assertEquals(4, array.size());

        assertEquals(3, array.remove(2));
        assertEquals(3, array.size());

        array.add(99, 2);
        assertEquals(4, array.size());
        assertEquals(99, array.get(2));
        assertEquals(4, array.get(3));

        array.add(100, 2);
        assertEquals(5, array.size());
        assertEquals(99, array.get(3));
    }

    @Test
    void testRemoveSeveralValuesFromEndThenAddSeveralValuesToEndWithAddByIndex() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(4, array.remove(4));
        assertEquals(3, array.remove(3));
        assertEquals(3, array.size());

        array.add(99, 3);
        assertEquals(4, array.size());
        assertEquals(99, array.get(3));

        array.add(100, 4);
        assertEquals(5, array.size());
        assertEquals(100, array.get(4));
    }

    @Test
    void testRemoveSeveralValuesFromStartThenAddSeveralValuesToStartWithAddByIndexPopulateAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(102, array.remove(0));
        assertEquals(5, array.size());

        array.add(99, 0);
        array.add(100, 0);
        assertEquals(100, array.get(0));
        assertEquals(99, array.get(1));
        assertEquals(101, array.get(2));

        assertEquals(7, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromEndThenAddSeveralValuesToEndWithAddByIndexPopulateAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(103, array.remove(6));
        assertEquals(104, array.remove(5));
        assertEquals(5, array.size());

        array.add(99, 5);
        assertEquals(6, array.size());
        assertEquals(99, array.get(5));

        array.add(100, 6);
        assertEquals(7, array.size());
        assertEquals(100, array.get(6));
    }

    @Test
    void testRemoveSeveralValuesFromStartMiddleEndThenAddValuesToRemovedIndexesPopulateSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(2, array.remove(1));
        assertEquals(4, array.remove(2));

        array.add(99, 0);
        assertEquals(99, array.get(0));

        array.add(100, 1);
        assertEquals(100, array.get(1));

        array.add(101, 4);
        assertEquals(101, array.get(4));

        assertEquals(5, array.size());
    }

    @Test
    void testRemoveSeveralValuesFromStartMiddleEndThenAddValuesToRemovedIndexesPopulateAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(105, array.remove(2));
        assertEquals(103, array.remove(4));

        array.add(99, 0);
        assertEquals(99, array.get(0));

        array.add(100, 2);
        assertEquals(100, array.get(2));

        array.add(101, 4);
        assertEquals(101, array.get(4));

        assertEquals(7, array.size());
    }

    @Test
    void testGetEmptyListThrowIndexOutOfBoundsException() {
        assertEquals(0, array.size());
        assertThrows(IndexOutOfBoundsException.class, () -> array.get(0),
                "There is nothing to do, Current size is: 0");
    }

    @Test
    void testGetListContainOneValueAddSimple() {
        array.add(99);
        assertEquals(1, array.size());

        assertEquals(99, array.get(0));
    }

    @Test
    void testGetListContainOneValueAddByIndex() {
        array.add(99, 0);
        assertEquals(1, array.size());

        assertEquals(99, array.get(0));
    }

    @Test
    void testGetFromStartMiddleEndListPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.get(0));
        assertEquals(2, array.get(2));
        assertEquals(4, array.get(4));
    }

    @Test
    void testGetFromStartMiddleEndListPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.get(0));
        assertEquals(105, array.get(3));
        assertEquals(103, array.get(6));
    }

    @Test
    void testGetFromEndAfterRemoveOneValueListPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(2, array.remove(2));
        assertEquals(4, array.size());

        assertEquals(4, array.get(3));
    }

    @Test
    void testGetFromEndAfterRemoveOneValueListPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(105, array.remove(3));
        assertEquals(6, array.size());

        assertEquals(103, array.get(5));
    }

    @Test
    void testGetIndexEqualsListSizeThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(5),
                "index can be in range: [0, 5)" + ", you have provided 5");
    }

    @Test
    void testGetIndexBiggerThenListSizeThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(10),
                "index can be in range: [0, 5)" + ", you have provided 10");
    }

    @Test
    void testGetIndexLessThenNilThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.get(-1),
                "index can be in range: [0, 5)" + ", you have provided -1");
    }

    @Test
    void testSetEmptyListThrowIndexOutOfBoundsException() {
        assertEquals(0, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.set(99, 0),
                "There is nothing to do, Current size is: 0");
    }

    @Test
    void testSetListContainsOneValuePopulateWithSimpleAdd() {
        assertEquals(0, array.size());
        array.add(99);
        assertEquals(1, array.size());

        assertEquals(99, array.set(100, 0));
        assertEquals(1, array.size());
    }

    @Test
    void testSetListContainsOneValuePopulateWithAddByIndex() {
        assertEquals(0, array.size());
        array.add(99, 0);
        assertEquals(1, array.size());

        assertEquals(99, array.set(100, 0));
        assertEquals(1, array.size());
    }

    @Test
    void testSetToStartMiddleEndListPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.set(99, 0));
        assertEquals(99, array.get(0));

        assertEquals(3, array.set(101, 3));
        assertEquals(101, array.get(3));

        assertEquals(4, array.set(999, 4));
        assertEquals(999, array.get(4));

        assertEquals(5, array.size());
    }

    @Test
    void testSetToStartMiddleEndListPopulateWithSAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.set(999, 0));
        assertEquals(999, array.get(0));

        assertEquals(105, array.set(1010, 3));
        assertEquals(1010, array.get(3));

        assertEquals(103, array.set(1020, 6));
        assertEquals(1020, array.get(6));

        assertEquals(7, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromStartListPopulatedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(4, array.size());

        assertEquals(1, array.set(99, 0));
        assertEquals(99, array.get(0));
        assertEquals(4, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromMiddleListPopulatedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(2, array.remove(2));
        assertEquals(4, array.size());

        assertEquals(3, array.set(99, 2));
        assertEquals(99, array.get(2));
        assertEquals(4, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromEndListPopulatedWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(4, array.remove(4));
        assertEquals(4, array.size());

        assertEquals(3, array.set(99, 3));
        assertEquals(99, array.get(3));
        assertEquals(4, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromStartListPopulatedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(6, array.size());

        assertEquals(102, array.set(999, 0));
        assertEquals(999, array.get(0));
        assertEquals(6, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromMiddleListPopulatedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(105, array.remove(3));
        assertEquals(6, array.size());

        assertEquals(99, array.set(999, 3));
        assertEquals(999, array.get(3));
        assertEquals(6, array.size());
    }

    @Test
    void testSetToListAfterRemoveValueFromEndListPopulatedWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(103, array.remove(6));
        assertEquals(6, array.size());

        assertEquals(104, array.set(999, 5));
        assertEquals(999, array.get(5));
        assertEquals(6, array.size());
    }

    @Test
    void testSetToStartEndAfterRemoveFromStartMiddleEndListPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(2, array.remove(1));
        assertEquals(4, array.remove(2));
        assertEquals(2, array.size());

        assertEquals(1, array.set(99, 0));
        assertEquals(99, array.get(0));

        assertEquals(3, array.set(110, 1));
        assertEquals(110, array.get(1));

        assertEquals(2, array.size());
    }

    @Test
    void testSetToStartMiddleEndAfterRemoveFromStartMiddleEndAfterAddToMiddleListPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.remove(0));
        assertEquals(2, array.remove(1));
        assertEquals(4, array.remove(2));
        assertEquals(2, array.size());

        array.add(999, 1);
        assertEquals(3, array.size());

        assertEquals(1, array.set(99, 0));
        assertEquals(99, array.get(0));

        assertEquals(999, array.set(110, 1));
        assertEquals(110, array.get(1));

        assertEquals(3, array.set(99, 2));
        assertEquals(99, array.get(0));

        assertEquals(3, array.size());
    }

    @Test
    void testSetToStartMiddleEndAfterRemoveFromStartMiddleEndListPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.remove(0));
        assertEquals(105, array.remove(2));
        assertEquals(103, array.remove(4));
        assertEquals(4, array.size());

        assertEquals(102, array.set(1010, 0));
        assertEquals(1010, array.get(0));

        assertEquals(101, array.set(1020, 1));
        assertEquals(1020, array.get(1));

        assertEquals(104, array.set(1030, 3));
        assertEquals(1030, array.get(3));

        assertEquals(4, array.size());
    }

    @Test
    void testSetToStartMiddleEndAfterRemovingAllValuesThenRepopulatingPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        for (int i = 0; i < 5; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());

        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertEquals(0, array.set(99, 0));
        assertEquals(99, array.get(0));

        assertEquals(2, array.set(110, 2));
        assertEquals(110, array.get(2));

        assertEquals(4, array.set(130, 4));
        assertEquals(130, array.get(4));

        assertEquals(5, array.size());
    }

    @Test
    void testSetToStartMiddleEndAfterRemovingAllValuesThenRepopulatingPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());

        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        assertEquals(100, array.set(999, 0));
        assertEquals(999, array.get(0));

        assertEquals(105, array.set(110, 3));
        assertEquals(110, array.get(3));

        assertEquals(103, array.set(130, 6));
        assertEquals(130, array.get(6));

        assertEquals(7, array.size());
    }

    @Test
    void testSetPopulateListWithSimpleAddThenResetAllValuesNaturalDirection() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        for (int i = 0; i < 5; i++) {
            array.set(99, i);
        }

        assertEquals(5, array.size());

        for (int i = 0; i < 5; i++) {
            assertEquals(99, array.get(i));
        }
    }

    @Test
    void testSetPopulateListWithSimpleAddThenResetAllValuesReversedDirection() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        for (int i = 4; i >= 0; i--) {
            array.set(99, i);
        }

        assertEquals(5, array.size());

        for (int i = 4; i >= 0; i--) {
            assertEquals(99, array.get(i));
        }
    }

    @Test
    void testSetPopulateListWithAddByIndexThenResetAllValuesNaturalDirection() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            array.set(99, i);
        }

        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            assertEquals(99, array.get(i));
        }
    }

    @Test
    void testSetPopulateListWithAddByIndexThenResetAllValuesReversedDirection() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        for (int i = 6; i >= 0; i--) {
            array.set(99, i);
        }

        assertEquals(7, array.size());

        for (int i = 6; i >= 0; i--) {
            assertEquals(99, array.get(i));
        }
    }

    @Test
    void testSetIndexEqualsListSizeThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.set(99, 5),
                "index can be in range: [0, 5)" + ", you have provided 5");
    }

    @Test
    void testSetIndexBiggerThenNilThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.set(99, 10),
                "index can be in range: [0, 5)" + ", you have provided 10");
    }

    @Test
    void testSetIndexLessThenNilThrowIndexOutOfBoundsException() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        assertThrows(IndexOutOfBoundsException.class, () -> array.set(99, -1),
                "index can be in range: [0, 5)" + ", you have provided -1");
    }

    @Test
    void testClearEmptyList() {
        assertEquals(0, array.size());
        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    void testClearAfterPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    void testClearAfterPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        array.clear();
        assertEquals(0, array.size());
    }

    @Test
    void testSizeEmptyList() {
        assertEquals(0, array.size());
    }

    @Test
    void testSizeAddSeveralValues() {
        array.add(99);
        assertEquals(1, array.size());
        array.add(101, 1);
        assertEquals(2, array.size());
    }

    @Test
    void testSizeAfterRemoveAllValuesPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        for (int i = 0; i < 5; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());
    }

    @Test
    void testSizeAfterRemoveAllValuesPopulateWithAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            array.remove(0);
        }
        assertEquals(0, array.size());
    }

    @Test
    void testIsEmptyOfEmptyListResultTrue() {
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());
    }

    @Test
    void testIsEmptyListContainsOneValueSimpleAddResultFalse() {
        assertEquals(0, array.size());
        array.add(99);
        assertFalse(array.isEmpty());
    }

    @Test
    void testIsEmptyListContainsOneValueAddByIndexResultFalse() {
        assertEquals(0, array.size());
        array.add(99, 0);
        assertFalse(array.isEmpty());
    }

    @Test
    void testIsEmptyAfterRemoveAllValuesPopulateSimpleAddResultTrue() {
        populateListSimpleAdd();
        assertEquals(5, array.size());

        for (int i = 0; i < 5; i++) {
            array.remove(0);
        }
        assertTrue(array.isEmpty());
    }

    @Test
    void testIsEmptyAfterRemoveAllValuesPopulateAddByIndexResultTrue() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        for (int i = 0; i < 7; i++) {
            array.remove(0);
        }
        assertTrue(array.isEmpty());
    }

    @Test
    void testIsEmptyAfterClearResultTrue() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());

        array.clear();
        assertTrue(array.isEmpty());
    }

    @Test
    void testContainsEmptyListResultFalse() {
        assertTrue(array.isEmpty());
        assertFalse(array.contains(500));
    }

    @Test
    void testContainsAfterSimpleAddAndRemoveOneValue() {
        assertTrue(array.isEmpty());
        array.add(99);
        assertTrue(array.contains(99));

        assertEquals(99, array.remove(0));
        assertFalse(array.contains(99));
    }

    @Test
    void testContainsValueInStartMiddleEndListPopulateSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        assertTrue(array.contains(0));
        assertTrue(array.contains(2));
        assertTrue(array.contains(4));
    }

    @Test
    void testContainsValueInStartMiddleEndListPopulateAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        assertTrue(array.contains(100));
        assertTrue(array.contains(105));
        assertTrue(array.contains(103));
    }

    @Test
    void testContainsValueAfterRemoveFromStartMiddleEndPopulateSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        assertEquals(4, array.remove(4));
        assertFalse(array.contains(4));
        assertEquals(4, array.size());

        assertEquals(2, array.remove(2));
        assertFalse(array.contains(2));
        assertEquals(3, array.size());

        assertEquals(0, array.remove(0));
        assertFalse(array.contains(0));
        assertEquals(2, array.size());
    }

    @Test
    void testContainsValueAfterRemoveFromStartMiddleEndPopulateAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        assertEquals(103, array.remove(6));
        assertFalse(array.contains(103));
        assertEquals(6, array.size());

        assertEquals(105, array.remove(3));
        assertFalse(array.contains(105));
        assertEquals(5, array.size());

        assertEquals(100, array.remove(0));
        assertFalse(array.contains(100));
        assertEquals(4, array.size());
    }

    @Test
    void testContainsAfterRemoveAllValuesPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        for (int i = 0; i < 5; i++) {
            array.remove(0);
        }

        for (int i = 0; i < 5; i++) {
            assertFalse(array.contains(i));
        }
    }

    @Test
    void testContainsAfterRemoveAllValuesPopulateWithAddByIndex() {
        String[] values = "100, 102, 101, 105, 99, 104, 103".split(", ");

        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        for (int i = 0; i < 7; i++) {
            array.remove(0);
        }

        for (String value : values) {
            assertFalse(array.contains(Integer.parseInt(value)));
        }
    }

    @Test
    void testContainsAfterClearPopulateWithSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        array.clear();

        for (int i = 0; i < 5; i++) {
            assertFalse(array.contains(i));
        }
    }

    @Test
    void testContainsAfterClearValuesPopulateWithAddByIndex() {
        String[] values = "100, 102, 101, 105, 99, 104, 103".split(", ");

        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        array.clear();

        for (String value : values) {
            assertFalse(array.contains(Integer.parseInt(value)));
        }
    }

    @Test
    void TestIndexOfEmptyList() {
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());

        assertEquals(-1, array.indexOf(100));
    }

    @Test
    void TestIndexOfValuesAtStartMiddleEndPopulatedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        assertEquals(0, array.indexOf(0));
        assertEquals(2, array.indexOf(2));
        assertEquals(4, array.indexOf(4));
    }

    @Test
    void TestIndexOfValuesAtStartMiddleEndPopulatedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        assertEquals(0, array.indexOf(100));
        assertEquals(3, array.indexOf(105));
        assertEquals(6, array.indexOf(103));
    }

    @Test
    void TestIndexOfValuesAtStartMiddleEndWithDuplicatesPopulatedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        array.add(0, 0);
        array.add(2, 3);
        array.add(4, 6);
        assertEquals(8, array.size());

        assertEquals(0, array.indexOf(0));
        assertEquals(3, array.indexOf(2));
        assertEquals(6, array.indexOf(4));
    }

    @Test
    void TestIndexOfValuesAtStartMiddleEndWithDuplicatesPopulatedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        array.add(100, 0);
        array.add(105, 4);
        array.add(103, 9);
        assertEquals(10, array.size());

        assertEquals(0, array.indexOf(100));
        assertEquals(4, array.indexOf(105));
        assertEquals(8, array.indexOf(103));
    }

    @Test
    void TestLastIndexOfEmptyList() {
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());

        assertEquals(-1, array.lastIndexOf(100));
    }

    @Test
    void TestLastIndexOfValuesAtStartMiddleEndPopulatedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        assertEquals(0, array.lastIndexOf(0));
        assertEquals(2, array.lastIndexOf(2));
        assertEquals(4, array.lastIndexOf(4));
    }

    @Test
    void TestLastIndexOfValuesAtStartMiddleEndPopulatedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        assertEquals(0, array.lastIndexOf(100));
        assertEquals(3, array.lastIndexOf(105));
        assertEquals(6, array.lastIndexOf(103));
    }

    @Test
    void TestLastIndexOfValuesAtStartMiddleEndWithDuplicatesPopulatedSimpleAdd() {
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        array.add(0, 0);
        array.add(2, 3);
        array.add(4, 6);
        assertEquals(8, array.size());

        assertEquals(1, array.lastIndexOf(0));
        assertEquals(4, array.lastIndexOf(2));
        assertEquals(7, array.lastIndexOf(4));
    }

    @Test
    void TestLastIndexOfValuesAtStartMiddleEndWithDuplicatesPopulatedAddByIndex() {
        populateListWithAddByIndexDifferentOrder();
        assertEquals(7, array.size());
        assertFalse(array.isEmpty());

        array.add(100, 0);
        array.add(105, 4);
        array.add(103, 9);
        assertEquals(10, array.size());

        assertEquals(1, array.lastIndexOf(100));
        assertEquals(5, array.lastIndexOf(105));
        assertEquals(9, array.lastIndexOf(103));
    }

    @Test
    void testToStringEmptyList() {
        String result = "[]";
        assertEquals(0, array.size());
        assertTrue(array.isEmpty());

        assertEquals(result, array.toString());
    }

    @Test
    void testToStringListWithValues() {
        String result = "[0, 1, 2, 3, 4]";
        populateListSimpleAdd();
        assertEquals(5, array.size());
        assertFalse(array.isEmpty());

        assertEquals(result, array.toString());
    }

    @Test
    public void testIterate() {
        int count = 0;
        populateListSimpleAdd();
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
        populateListWithAddByIndexDifferentOrder();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) {
            Integer value = iterator.next();
            if (value.equals(100)) iterator.remove();
            if (value.equals(105)) iterator.remove();
            if (value.equals(103)) iterator.remove();
        }
        assertEquals(4, array.size());
    }

    @Test
    public void testThrowExceptionIteratorRemoveBeforeNext() {
        populateListWithAddByIndexDifferentOrder();
        Iterator<Integer> iterator = array.iterator();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void testThrowExceptionIteratorRemoveAfterLastNext() {
        populateListWithAddByIndexDifferentOrder();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) iterator.next();
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove);
    }

    @Test
    public void testThrowExceptionIteratorNextAfterLastNext() {
        populateListWithAddByIndexDifferentOrder();
        Iterator<Integer> iterator = array.iterator();
        while (iterator.hasNext()) iterator.next();
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    private void populateListSimpleAdd() {
        int count = 5;
        for (int i = 0; i < count; i++) {
            array.add(i);
        }
    }

    private void populateListWithAddByIndexDifferentOrder() {
        array.add(99, 0);
        array.add(100, 0);
        array.add(101, 1);
        array.add(102, 1);
        array.add(103, 4);
        array.add(104, 4);
        array.add(105, 3);
    }
}
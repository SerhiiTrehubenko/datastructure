package test;

import com.tsa.list.implementations.HashMap;
import com.tsa.list.interfaces.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    private Map<Object, Object> map;

    @BeforeEach
    public  void init() {
        map = new HashMap<>();
    }

    @Test
    void put() {
        mapFiller();
        assertEquals(6, map.size());
    }
    @Test
    void putToExistKey() {
        mapFiller();
        assertEquals("world!", map.put("Hello", "world2"));
        assertEquals("world2", map.get("Hello"));
    }

    @Test
    void get() {
        mapFiller();
        assertAll(
                () -> assertEquals("emptyKey", map.get("")),
                () -> assertEquals("world!", map.get("Hello")),
                () -> assertNull(map.get(null)),
                () -> assertEquals("double", map.get(25.25)),
                () -> assertEquals("integer", map.get(100)),
                () -> assertEquals("MyClass{ 1 }", map.get("myClass").toString()),
                () -> assertEquals(6, map.size())
        );
    }

    @Test
    void containsKeyPositive() {
        mapFiller();
        assertAll(
                () -> assertTrue(map.containsKey("")),
                () -> assertTrue(map.containsKey("Hello")),
                () -> assertTrue(map.containsKey(null)),
                () -> assertTrue(map.containsKey(25.25)),
                () -> assertTrue(map.containsKey(100)),
                () -> assertTrue(map.containsKey("myClass"))
        );
    }
    @Test
    void containsKeyNegative() {
        mapFiller();
        assertFalse(map.containsKey("dfsret"));
    }

    @Test
    void remove() {
        mapFiller();
        assertAll(
                () -> assertEquals("world!", map.remove("Hello")),
                () -> assertNull(map.remove("Hello")),
                () -> assertEquals("double", map.remove(25.25)),
                () -> assertNull(map.remove(25.25))
        );
    }

    @Test
    void size() {
        mapFiller();
        assertEquals(6, map.size());
    }

    @Test
    void iterator() {
        mapFiller();
        int count = 0;
        for (Object o : map) {
            count++;
        }
        assertEquals(count, map.size());
    }

    @DisplayName("iterator Throws Exception When Call Remove Before Next")
    @Test
    void iteratorThrowsExceptionWhenCallRemoveBeforeNext() {
        mapFiller();
        Iterator<Object> iterator = map.iterator();
        assertThrows(IllegalStateException.class, iterator::remove, "\"You have called remove() before next()\"");
    }

    @DisplayName("iterator Throws Exception When Call Remove After Remove Of The LastNext")
    @Test
    void iteratorThrowsExceptionWhenCallRemoveAfterRemoveOfLastNext() {
        mapFiller();
        Iterator<Object> iterator = map.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        iterator.remove();
        assertThrows(IllegalStateException.class, iterator::remove, "\"You have called remove() after \"the last\" next()\"");
    }

    @DisplayName("iterator Throws Exception When Call Next After The LastNext")
    @Test
    void iteratorThrowsExceptionWhenCallNextAfterLastNext() {
        mapFiller();
        Iterator<Object> iterator = map.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        assertThrows(NoSuchElementException.class, iterator::next, "There is no more elements in the List");
    }
    @Test
    void toStr() {
        mapFiller();
        assertEquals("[emptyKey, world!, double, integer, null, MyClass{ 1 }]", map.toString());

    }
    private void mapFiller() {
        map.put("", "emptyKey");
        map.put("Hello", "world!");
        map.put(null, null);
        map.put(25.25, "double");
        map.put(100, "integer");
        map.put("myClass", new MyClass());
    }

}
class MyClass {
    private int index;

    public MyClass() {
        index++;
    }

    @Override
    public String toString() {
        return "MyClass{ " + index +
                " }";
    }
}
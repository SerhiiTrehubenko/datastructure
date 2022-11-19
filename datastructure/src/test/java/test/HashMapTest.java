package test;

import com.tsa.list.implementations.HashMap;
import com.tsa.list.interfaces.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    private Map<Object, Object> map;

    @BeforeEach
    public  void init() {
        map = new HashMap<>();
    }

    @DisplayName("test put(), returns NULL if there was no mapping for key.")
    @Test
    void testPutReturnsNullWhenThereWereNoPreviousMapping() {
        assertNull(map.put("Hello", "world2"));
    }

    @DisplayName("test put(), returns the previous value associated with key")
    @Test
    void testPutReturnsPreviousValueAssociatedWithKey() {
        String key = "Hello";
        assertNull(map.put(key, "world2"));
        assertEquals("world2", map.put(key, "world100"));
        assertEquals("world100", map.put(key, 20));
        assertEquals(20, map.put(key, 25.25));
        assertEquals(25.25, map.put(key, null));
        assertNull(map.put(key, "MAp"));
    }
    @DisplayName("test put(), NULL can be used as a key")
    @Test
    void testPutCanUseNullAsKey() {
        map.put(null, "Hello");
        assertEquals("Hello", map.get(null));
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
                () -> assertNull(map.get("Hello")),
                () -> assertEquals("double", map.remove(25.25)),
                () -> assertNull(map.get(25.25))
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
            System.out.println(o);
            count++;
        }
        assertEquals(count, map.size());
    }

    @DisplayName("iterator Throws Exception When Call Remove Before Next")
    @Test
    void iteratorThrowsExceptionWhenCallRemoveBeforeNext() {
        mapFiller();
        Iterator<?> iterator = map.iterator();
        assertThrows(IllegalStateException.class, iterator::remove, "\"You have called remove() before next()\"");
    }

    @DisplayName("iterator Throws Exception When Call Remove After Remove Of The LastNext")
    @Test
    void iteratorThrowsExceptionWhenCallRemoveAfterRemoveOfLastNext() {
        mapFiller();
        Iterator<?> iterator = map.iterator();
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
        Iterator<?> iterator = map.iterator();
        while (iterator.hasNext()) {
            iterator.next();
        }
        assertThrows(NoSuchElementException.class, iterator::next, "There is no more elements in the List");
    }
    @Test
    void testToString() {
        mapFiller();
        assertEquals("[100=integer, =emptyKey, Hello=world!, null=null, 25.25=double, myClass=MyClass{ 1 }]",
                map.toString());
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
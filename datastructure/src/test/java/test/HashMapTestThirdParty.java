package test;

import com.tsa.list.implementations.HashMap;
import com.tsa.list.interfaces.Map;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTestThirdParty {
    @Test
    public void givenEmptyMapWhenGetByNullKeyThenNullShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.get(null));
    }

    @Test
    public void givenEmptyMapWhenGetByNotNullKeyThenNullShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.get("key"));
    }

    @Test
    public void givenNullKeyWhenPutOnceThenSizeShouldBeEqualToOneAndValueShouldBeEqualToInserted() {
        Map<String, String> map = new HashMap<>();

        String key = null;
        String value = "test";

        map.put(key, value);

        assertEquals(1, map.size());
        assertEquals(value, map.get(key));
    }

    @Test
    public void givenNullKeyWhenPutMultipleTimesThenSizeShouldBeEqualToOneAndValueShouldBeOverwrittenWithLast() {
        Map<String, String> map = new HashMap<>();

        String key = null;
        String firstValue = "test1";
        String secondValue = "test2";
        String thirdValue = "test3";

        map.put(key, firstValue);
        map.put(key, secondValue);
        map.put(key, thirdValue);

        assertEquals(1, map.size());
        assertEquals(thirdValue, map.get(key));
    }

    @Test
    public void givenNotNullKeyWhenPutThenSizeShouldBeEqualToOneAndValueShouldBeEqualToInserted() {
        Map<String, String> map = new HashMap<>();

        String key = "key";
        String value = "value";

        map.put(key, value);

        assertEquals(1, map.size());
        assertEquals(value, map.get(key));
    }

    @Test
    public void givenMultipleNotNullKeysWhenPutThenSizeShouldBeEqualToSizeOfKeysAndGetByKeyReturnsCorrespondingValue() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1";
        String secondKey = "key2";
        String firstValue = "value1";
        String secondValue = "value2";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);

        assertEquals(2, map.size());
        assertEquals(firstValue, map.get(firstKey));
        assertEquals(secondValue, map.get(secondKey));
    }

    @Test
    public void givenMultipleNodesInSameBucketWhenGetByExistingKeyThenGetByKeyReturnsCorrespondingValue() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key2"; //same bucket
        String secondKey = "key-1"; //same bucket
        String thirdKey = "key-10"; //same bucket

        String firstValue = "value2";
        String secondValue = "value3";
        String thirdValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);

        assertEquals(3, map.size());

        assertEquals(firstValue, map.get(firstKey));
        assertEquals(secondValue, map.get(secondKey));
        assertEquals(thirdValue, map.get(thirdKey));
    }

    @Test
    public void givenNotExistingKeyWhenGetByKeyThenNullShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        map.put("existing key", "value");

        assertNull(map.get("not existing key"));
    }

    @Test
    public void givenNotNullKeyWhenPutMultipleTimesWithSameKeyThenSizeShouldBeEqualToOneAndValueShouldBeOverwrittenWithLast() {
        Map<String, String> map = new HashMap<>();

        String key = "key";
        String firstValue = "test1";
        String secondValue = "test2";
        String thirdValue = "test3";

        map.put(key, firstValue);
        map.put(key, secondValue);
        map.put(key, thirdValue);

        assertEquals(1, map.size());
        assertEquals(thirdValue, map.get(key));
    }

    @Test
    public void givenEmptyMapWhenRemoveByNullKeyThenSizeShouldBeEqualToZero() {
        Map<String, String> map = new HashMap<>();
        map.remove(null);
        assertEquals(0, map.size());
    }

    @Test
    public void givenEmptyMapWhenRemoveByNotNullKeyThenSizeShouldBeEqualToZero() {
        Map<String, String> map = new HashMap<>();
        map.remove("key");
        assertEquals(0, map.size());
    }

    @Test
    public void givenNotEmptyMapWhenRemoveByNullKeyThenSizeShouldBeEqualToZero() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "value");
        assertEquals("value", map.remove(null));

        assertEquals(0, map.size());
    }

    @Test
    public void givenNotEmptyMapWithNotNullKeyWhenPutWithNullKeyAndRemoveByNullKeyThenSizeShouldDecreaseByOne() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "value");
        map.put("not null key", "value");

        assertEquals(2, map.size());

        assertEquals("value", map.remove(null));
        assertEquals(1, map.size());
    }

    @Test
    public void givenEmptyMapWhenRemoveThenSizeShouldBeEqualToZero() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.remove("key"));
        assertEquals(0, map.size());
    }

    @Test
    public void givenNotEmptyMapWhenRemoveThenSizeShouldBeEqualToZero() {
        Map<String, String> map = new HashMap<>();

        map.put("key", "value");
        assertEquals(1, map.size());

        map.remove("key");
        assertEquals(0, map.size());
    }

    @Test
    public void givenNotEmptyMapWhenRemoveOneByOneThenSizeShouldDecreaseAfterEachRemovalByOne() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket
        String secondKey = "key2";
        String thirdKey = "key-1"; //same bucket

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";

        map.put(firstKey, firstValue); //case: remove last node
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue); //case: remove first node

        assertEquals(3, map.size());

        assertEquals(thirdValue, map.remove(thirdKey));
        assertEquals(2, map.size());

        assertEquals(firstValue, map.remove(firstKey));
        assertEquals(1, map.size());

        assertEquals(secondValue, map.remove(secondKey));
        assertEquals(0, map.size());
    }

    @Test
    public void givenNotEmptyMapAndObjectsInSameBucketWhenRemoveFirstNodeThenSizeShouldDecreaseByOne() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket
        String secondKey = "key2";
        String thirdKey = "key-1"; //same bucket
        String fourthKey = "key-10";

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";
        String fourthValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);
        map.put(fourthKey, fourthValue);

        assertEquals(4, map.size());

        assertEquals(thirdValue, map.remove(thirdKey)); //case: remove first node
        assertEquals(3, map.size());
    }

    @Test
    public void givenNotEmptyMapAndObjectsInSameBucketWhenRemoveLastNodeThenSizeShouldDecreaseByOne() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket
        String secondKey = "key2";
        String thirdKey = "key-1"; //same bucket
        String fourthKey = "key-10";

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";
        String fourthValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);
        map.put(fourthKey, fourthValue);

        assertEquals(4, map.size());

        assertEquals(fourthValue, map.remove(fourthKey)); //case: remove first node
        assertEquals(3, map.size());
    }
    @Test
    public void givenNotEmptyMapAndObjectsInSameBucketWhenRemoveNodeInTheMiddleThenSizeShouldDecreaseByOne() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket
        String secondKey = "key12";//same bucket
        String thirdKey = "key-1"; //same bucket
        String fourthKey = "key10";

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";
        String fourthValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);
        map.put(fourthKey, fourthValue);

        assertEquals(4, map.size());

        assertEquals(secondValue, map.remove(secondKey)); //case: remove node in the middle
        assertEquals(3, map.size());
    }
    @Test
    public void givenEmptyMapNotAndNotExistingKeyWhenRemoveThenSizeShouldNotChange() {
        Map<String, String> map = new HashMap<>();
        assertNull(map.remove("not existing key"));
        assertEquals(0, map.size());
    }
    @Test
    public void givenNotEmptyMapNotAndNotExistingKeyWhenRemoveThenSizeShouldNotChange() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        assertEquals(1, map.size());

        assertNull(map.remove("not existing key"));
        assertEquals(1, map.size());
    }
    @Test
    public void givenEmptyMapWhenContainsNullKeyThenFalseShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        assertFalse(map.containsKey(null));
    }
    @Test
    public void givenEmptyMapWhenContainsNotNullKeyThenFalseShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        assertFalse(map.containsKey("key"));
    }
    @Test
    public void givenMapWithExistingNullKeyWhenContainsNullKeyThenTrueShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        map.put(null, "value");

        assertTrue(map.containsKey(null));
    }
    @Test
    public void givenMapWithNotExistingNullKeyWhenContainsNullKeyThenFalseShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        assertFalse(map.containsKey(null));
    }
    @Test
    public void givenNotExistingKeyWhenContainsKeyThenFalseShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        assertTrue(map.containsKey("key"));
    }
    @Test
    public void givenExistingKeyWhenContainsKeyThenTrueShouldBeReturned() {
        Map<String, String> map = new HashMap<>();
        map.put("key", "value");

        assertFalse(map.containsKey("not existing key"));
    }
    @Test
    public void givenMultipleNodesInSameBucketAndExistingKeyWhenContainsByKeyThenTrueShouldBeReturned() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket end
        String secondKey = "key12"; //same bucket middle
        String thirdKey = "key-1"; //same bucket beginning
        String fourthKey = "key-10";

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";
        String fourthValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);
        map.put(fourthKey, fourthValue);

        assertTrue(map.containsKey(firstKey));
        assertTrue(map.containsKey(secondKey));
        assertTrue(map.containsKey(thirdKey));
        assertTrue(map.containsKey(fourthKey));
    }
    @Test
    public void givenMultipleNodesInSameBucketAndNotExistingKeyWithHashLeadingToSameBucketWhenContainsByKeyThenFalseShouldBeReturned() {
        Map<String, String> map = new HashMap<>();

        String firstKey = "key1"; //same bucket end
        String secondKey = "key12"; //same bucket middle
        String thirdKey = "key-1"; //same bucket beginning
        String fourthKey = "key-10";
        String notExistingKeyWithHashLeadingToSameBucket = "key+12";

        String firstValue = "value1";
        String secondValue = "value2";
        String thirdValue = "value3";
        String fourthValue = "value4";

        map.put(firstKey, firstValue);
        map.put(secondKey, secondValue);
        map.put(thirdKey, thirdValue);
        map.put(fourthKey, fourthValue);

        assertTrue(map.containsKey(firstKey));
        assertTrue(map.containsKey(secondKey));
        assertTrue(map.containsKey(thirdKey));
        assertTrue(map.containsKey(fourthKey));
        assertFalse(map.containsKey(notExistingKeyWithHashLeadingToSameBucket));
    }
    @Test
    public void givenEmptyMapWhenIteratorNextThenNoSuchElementExceptionShouldBeRaised() {
        assertThrows(NoSuchElementException.class, () -> new HashMap<>().iterator().next());
    }
    @Test
    public void givenIteratorWhenNextAfterLastElementThenNoSuchElementExceptionShouldBeRaised() {
        HashMap<String, String> map = new HashMap<>();

        String key = "key";
        String value = "value";
        map.put(key, value);

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();

        Map.MyEntry<String, String> entry = iterator.next();
        assertEquals(key, entry.getKey());
        assertEquals(value ,entry.getValue());

        assertThrows(NoSuchElementException.class, iterator::next);
    }
    @Test
    public void givenIteratorWhenNextThenShouldReturnNextValue() {
        HashMap<String, String> map = new HashMap<>();

        String keyQ = "0";
        String keyW = "key1";
        String keyE = "key2";
        String keyR = "key-1";
        String keyT = "key-10";
        String keyY = "key-100";
        String keyZ = "1000";

        String keyQValue = "keyQValue";
        String keyWValue = "keyWValue";
        String keyEValue = "keyEValue";
        String keyRValue = "keyRValue";
        String keyTValue = "keyTValue";
        String keyYValue = "keyYValue";
        String keyZValue = "keyZValue";

        map.put(keyQ, keyQValue);
        map.put(keyW, keyWValue);
        map.put(keyE, keyEValue);
        map.put(keyR, keyRValue);
        map.put(keyT, keyTValue);
        map.put(keyY, keyYValue);
        map.put(keyZ, keyZValue);

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();

        Map.MyEntry<String, String> resultZ = iterator.next();
        Map.MyEntry<String, String> resultR = iterator.next();
        Map.MyEntry<String, String> resultT = iterator.next();
        Map.MyEntry<String, String> resultQ = iterator.next();
        Map.MyEntry<String, String> resultW = iterator.next();
        Map.MyEntry<String, String> resultY = iterator.next();
        Map.MyEntry<String, String> resultE = iterator.next();

        assertEquals(keyZ, resultZ.getKey());
        assertEquals(keyZValue, resultZ.getValue());

        assertEquals(keyR, resultR.getKey());
        assertEquals(keyRValue, resultR.getValue());

        assertEquals(keyT, resultT.getKey());
        assertEquals(keyTValue, resultT.getValue());

        assertEquals(keyQ, resultQ.getKey());
        assertEquals(keyQValue, resultQ.getValue());

        assertEquals(keyW, resultW.getKey());
        assertEquals(keyWValue, resultW.getValue());

        assertEquals(keyY, resultY.getKey());
        assertEquals(keyYValue, resultY.getValue());

        assertEquals(keyE, resultE.getKey());
        assertEquals(keyEValue, resultE.getValue());
    }
    @Test
    public void givenEmptyMapWhenIteratorHasNextThenShouldReturnFalse() {
        HashMap<String, String> map = new HashMap<>();
        assertFalse(map.iterator().hasNext());
    }
    @Test
    public void givenNotEmptyMapWhenIteratorHasNextThenShouldReturnTrue() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();
        assertTrue(iterator.hasNext());


        map.remove("key");
        assertFalse(iterator.hasNext());

        map.put("key", "value");
        assertTrue(iterator.hasNext());
    }
    @Test
    public void givenMapWithTwoElementsWhenIteratorNextThenIteratorHasNextShouldReturnFalse() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        map.put("key2", "value");

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertTrue(iterator.hasNext());
    }
    @Test
    public void givenMapWithOneElementWhenIteratorNextThenIteratorHasNextShouldReturnFalse() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();

        assertTrue(iterator.hasNext());
        iterator.next();
        assertFalse(iterator.hasNext());
    }
    @Test
    public void givenEmptyMapWhenIteratorRemoveThenNoSuchElementExceptionShouldBeRaised() {
        assertThrows(IllegalStateException.class, () -> new HashMap<>().iterator().remove());
    }
    @Test
    public void givenIteratorWhenRemoveCalledWithoutNextThenIllegalStateExceptionShouldBeRaised() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");

        assertEquals(1, map.size());

        assertThrows(IllegalStateException.class, map.iterator()::remove);
    }
    @Test
    public void givenIteratorWhenRemoveCalledAfterNextThenSizeShouldBeDecreasedByOneAndMapShouldNotContainKey() {
        HashMap<String, String> map = new HashMap<>();
        String key = "key";
        map.put(key, "value");
        assertEquals(1, map.size());

        Iterator<Map.MyEntry<String, String>> iterator = map.iterator();
        iterator.next();
        iterator.remove();

        assertEquals(0, map.size());
        assertFalse(map.containsKey(key));
    }
}
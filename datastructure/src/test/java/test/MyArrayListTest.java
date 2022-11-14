package test;

import com.tsa.list.implementations.MyArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class MyArrayListTest extends MyAbstractListTest {

    @BeforeEach
    public void init() {
        array = new MyArrayList<>();
    }
    @AfterEach
    public void clean() {
        if (array.size() != 0) array.clear();
    }
}

package test;

import com.tsa.list.implementations.MyLinkedList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;


public class MyLinkedListTest extends MyAbstractListTest{

    @BeforeEach
    public void init() {
        array = new MyLinkedList<>();
    }
    @AfterEach
    public void clean() {
        if (array.size() != 0) array.clear();
    }

}

package test;

import com.tsa.list.implementations.LinkedList;
import org.junit.jupiter.api.BeforeEach;


public class LinkedListTest extends MyAbstractListTest {

    @BeforeEach
    public void init() {
        array = new LinkedList<>();
    }

}

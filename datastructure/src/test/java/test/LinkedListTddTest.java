package test;

import com.tsa.list.implementations.LinkedList;
import org.junit.jupiter.api.BeforeEach;

public class LinkedListTddTest extends AbstractListTddTest {

    @BeforeEach
    public void init() {
        array = new LinkedList<>();
    }
}

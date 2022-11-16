package test;

import com.tsa.list.implementations.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class ArrayListTest extends MyAbstractListTest {

    @BeforeEach
    public void init() {
        array = new ArrayList<>();
    }

}

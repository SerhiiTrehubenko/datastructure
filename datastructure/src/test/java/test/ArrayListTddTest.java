package test;

import com.tsa.list.implementations.ArrayList;
import org.junit.jupiter.api.BeforeEach;

public class ArrayListTddTest extends AbstractListTddTest {

    @BeforeEach
    public void init() {
        array = new ArrayList<>();
    }

}

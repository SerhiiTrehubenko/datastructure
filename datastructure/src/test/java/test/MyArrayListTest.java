package test;

import com.tsa.list.implementations.MyArrayList;
import com.tsa.list.interfaces.List;
import org.junit.*;

public class MyArrayListTest extends MyAbstractListTest {

    @Override
    List<Integer> getList() {
        return new MyArrayList<>();
    }
}

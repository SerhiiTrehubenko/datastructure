package test;

import com.tsa.list.implementations.MyLinkedList;
import com.tsa.list.interfaces.List;


public class MyLinkedListTest extends MyAbstractListTest{

    @Override
    List<Integer> getList() {
        return new MyLinkedList<>();
    }


}

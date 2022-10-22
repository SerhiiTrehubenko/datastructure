package com.tsa.orm.entity;

import com.tsa.orm.annotation.MyColumn;
import com.tsa.orm.annotation.MyEntity;
import com.tsa.orm.annotation.MyTable;

@MyEntity
@MyTable
public class User {
    @MyColumn
    private Long id;
    @MyColumn
    private String name;
    @MyColumn
    private String password;

    public User(Long id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}

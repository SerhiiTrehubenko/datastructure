package com.tsa.orm.entity;

import com.tsa.orm.annotation.MyColumn;
import com.tsa.orm.annotation.MyEntity;
import com.tsa.orm.annotation.MyTable;

@MyEntity
@MyTable("Guest_Table")
public class Guest {
    @MyColumn("guest_id")
    private Long id;
    @MyColumn("guest_name")
    private String name;
    @MyColumn("guest_password")
    private String password;

    public Guest(Long id, String name, String password) {
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

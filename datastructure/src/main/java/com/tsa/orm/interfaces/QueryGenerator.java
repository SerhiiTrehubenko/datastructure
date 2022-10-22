package com.tsa.orm.interfaces;

import java.io.Serializable;

public interface QueryGenerator {
    String findAll (Class<?> type, String... columns);
    String findById (Class<?> type, Serializable id);
    String deleteById (Class<?> type, Serializable id);

    String insert(Object value) throws IllegalAccessException;

    String update(Object value) throws IllegalAccessException;
}

package com.tsa.serialize.interfaces;

public interface MessageDao<T> {

    void save(T message, String fileDestination);

    T load(String fileSource);
}

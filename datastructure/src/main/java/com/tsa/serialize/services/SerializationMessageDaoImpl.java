package com.tsa.serialize.services;


import com.tsa.serialize.interfaces.MessageDao;

import java.io.*;

@SuppressWarnings("unchecked")
public class SerializationMessageDaoImpl<T> implements MessageDao<T> {

    public SerializationMessageDaoImpl() {
    }

    @Override
    public void save(T message, String fileDestination) {
        try (var outputStream = new ObjectOutputStream(new FileOutputStream(fileDestination))) {
            outputStream.writeObject(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T load(String fileSource) {
        try (var inputStream = new ObjectInputStream(new FileInputStream(fileSource))) {
            return (T) inputStream.readObject();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

package com.tsa.serialize.services;

import com.tsa.serialize.entity.Message;
import com.tsa.serialize.interfaces.MessageDataDao;

import java.io.*;
import java.util.Date;


public class DataMessageDaoImpl implements MessageDataDao {
    @Override
    public void save(Message message, String fileDestination) {
        try (var out = new DataOutputStream(new FileOutputStream(fileDestination))){
            out.writeLong(message.getData().getTime());
            out.writeShort(message.getMessage().getBytes().length);
            out.write(message.getMessage().getBytes());
            out.writeDouble(message.getAmount());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Message load(String fileSource) {
        try (var input = new DataInputStream(new FileInputStream(fileSource))){
            return new Message(new Date(input.readLong()),
                    String.valueOf(new Object() {
                        @Override
                        public String toString() {
                            try {
                                int size = input.readShort();
                                byte[] inputText = new byte[size];
                                input.read(inputText, 0, size);
                                return new String(inputText);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }),
                    input.readDouble());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

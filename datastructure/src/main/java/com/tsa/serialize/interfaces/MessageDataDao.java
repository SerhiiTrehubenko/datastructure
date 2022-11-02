package com.tsa.serialize.interfaces;

import com.tsa.serialize.entity.Message;

public interface MessageDataDao {

    void save(Message message, String fileDestination);

    Message load(String fileSource);
}

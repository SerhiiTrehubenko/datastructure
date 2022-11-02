package test;

import com.tsa.serialize.entity.Message;
import com.tsa.serialize.interfaces.MessageDao;
import com.tsa.serialize.services.SerializationMessageDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SerializationMessageDaoImplTest {

    private MessageDao<List<Message>> messageDao;
    private List<Message> messages;

    @BeforeEach
    void init() {
        messageDao = new SerializationMessageDaoImpl();
        messages = new ArrayList<>(Arrays.asList(new Message(new Date(), "Hello", 25.25),
                new Message(new Date(), "World", 144.25),
                new Message(new Date(), "Sergey", 255.25),
                new Message(new Date(), "Java", 75.25)));
    }

    @Test
    void save() {
        messageDao.save(messages, "message.dat");
        assertEquals(348, Path.of("message.dat").toFile().length());
    }

    @Test
    void load() {
        List<Message> messagesRetrieved = messageDao.load("message.dat");
        assertEquals(4, messagesRetrieved.size());
    }
}
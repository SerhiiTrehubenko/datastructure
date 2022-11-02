package test;

import com.tsa.serialize.entity.Message;
import com.tsa.serialize.interfaces.MessageDataDao;
import com.tsa.serialize.services.DataMessageDaoImpl;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DataMessageDaoImplTest {

    private final MessageDataDao messageDataDao = new DataMessageDaoImpl();

    @Test
    void save() {
        Message message = new Message(new Date(), "Serhii", 200.25);
        messageDataDao.save(message, "data-message.dat");
        assertEquals(24, Path.of("data-message.dat").toFile().length());
    }

    @Test
    void load() {
        Message message = messageDataDao.load("data-message.dat");
        assertTrue(message.toString().contains("message='Serhii', amount=200.25}"));
    }
}
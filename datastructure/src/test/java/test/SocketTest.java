package test;

import com.tsa.socket.ClientIO;
import com.tsa.socket.ClientRW;
import com.tsa.socket.EchoServerIO;
import com.tsa.socket.EchoServerRW;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SocketTest {

    @Test
    void runServerIO() {
        EchoServerIO echoServerIO = new EchoServerIO();
        echoServerIO.run();
    }
    @Test
    void runClientIO() {
        ClientIO clientIO = new ClientIO();
        clientIO.setText(new ArrayList<>(List.of("Hello\t\n\r", "World!!!\t\n\r", "Sergey\t\n\r")));
        clientIO.run();
        assertEquals(4, clientIO.getResponses().size());
        for (String respons : clientIO.getResponses()) {
            System.out.println(respons);
        }
    }
    @Test
    void runServerRW() {
        EchoServerRW echoServerRW = new EchoServerRW();
        echoServerRW.run();
    }
    @Test
    void runClientRW() {
        ClientRW clientRW = new ClientRW();
        clientRW.setText(new ArrayList<>(List.of("Hello\n\t", "World!!!\n\t", "Sergey\n\t")));
        clientRW.run();
        assertEquals(4, clientRW.getResponses().size());
        for (String respons : clientRW.getResponses()) {
            System.out.println(respons);
        }
    }
}
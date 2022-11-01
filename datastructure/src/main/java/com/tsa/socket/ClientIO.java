package com.tsa.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ClientIO implements Runnable {

    private List<String> requests;
    private final List<String> responses = new ArrayList<>();

    @Override
    public void run() {
        byte[] inputBytes = new byte[100];
        StringJoiner stringJoiner;
        for (String request : requests) {
            int count;

            stringJoiner = new StringJoiner("");

            try (var socket = new Socket("localhost", 300);
                 var inputServer = new DataInputStream(socket.getInputStream());
                 var outClient = new DataOutputStream(socket.getOutputStream())) {

                request.chars().forEach(x -> {
                    try {
                        outClient.write(x);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
                outClient.flush();
                socket.shutdownOutput();

                stringJoiner.add("Client send: ");
                stringJoiner.add(request);

                while ((count = inputServer.read(inputBytes)) != -1) {
                    stringJoiner.add(new String(inputBytes, 0, count));
                }

                stringJoiner.add("************************");
                responses.add(stringJoiner.toString());

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setText(List<String> requests) {
        this.requests = requests;
        this.requests.add("STOP\t\n\r");
    }

    public List<String> getResponses() {
        return responses;
    }
}

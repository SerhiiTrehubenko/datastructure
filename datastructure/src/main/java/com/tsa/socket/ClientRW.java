package com.tsa.socket;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringJoiner;

public class ClientRW implements Runnable {

    private List<String> requests;
    private final List<String> responses = new ArrayList<>();

    @Override
    public void run() {

        for (String request : requests) {
            StringJoiner stringJoiner = new StringJoiner("");
            try (var socket = new Socket("localhost", 300);
                 var inputServer = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
                 var outClient = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {

                outClient.println(request);
                outClient.flush();
                socket.shutdownOutput();

                stringJoiner.add("Client send: ");
                stringJoiner.add(request);
                stringJoiner.add("\n");

                while (inputServer.hasNext()) {
                  String line = inputServer.nextLine();

                    if (line.startsWith("echo")) {
                        stringJoiner.add("\n");
                    }
                    stringJoiner.add(line);

                }
                stringJoiner.add("\n");
                stringJoiner.add("************************");
                responses.add(stringJoiner.toString());

            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setText(List<String> requests) {
        this.requests = requests;
        this.requests.add("STOP");
    }

    public List<String> getResponses() {
        return responses;
    }
}

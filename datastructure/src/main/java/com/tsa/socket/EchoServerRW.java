package com.tsa.socket;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringJoiner;

public class EchoServerRW implements Runnable{
    private static final String STOP = "STOP";
    private static final String PREFIX = "echo ";
    private static String INPUT_LINE = "";
    private StringJoiner stringJoiner;
    public EchoServerRW() {
    }

    public static void main(String[] args) {
        EchoServerRW echoServerRW = new EchoServerRW();
        echoServerRW.run();
    }

    @Override
    public void run() {
        int loops = 0;
        try (ServerSocket serverSocket = new ServerSocket(300)) {
            while (!stop()) {
                System.out.println("enter first WHILE " + loops++);
                try (var socket = serverSocket.accept();
                     var input = new Scanner(socket.getInputStream(), StandardCharsets.UTF_8);
                     var out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                    //Send Greetings
                    out.println("Welcome to the Server, print \"STOP\" to exit\n\r");
                    out.flush();
                    while (input.hasNext()) {
                        INPUT_LINE = input.nextLine();
                        stringJoiner = new StringJoiner("");
                        stringJoiner.add(PREFIX);
                        stringJoiner.add(INPUT_LINE);
                        out.println(stringJoiner);
                        out.flush();
                        if (stop()) {
                            break;
                        }
                        if (INPUT_LINE.endsWith("\t")) {
                            break;
                        }
                    }

                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    private boolean stop() {
        return STOP.equals(INPUT_LINE.trim());
    }

}

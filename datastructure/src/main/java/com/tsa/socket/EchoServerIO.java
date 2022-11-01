package com.tsa.socket;

import java.io.*;
import java.net.ServerSocket;
import java.util.StringJoiner;

public class EchoServerIO implements Runnable {

    public EchoServerIO() {}

    public static void main(String[] args) {
        EchoServerIO echoServerIO = new EchoServerIO();
        echoServerIO.run();
    }

    private static final String PREFIX = "echo ";
    private final static String STOP = "STOP";
    private final int[] inputBytes = new int[10];

    @Override
    public void run() {

        int loops = 0;
        int count = 0;
        int position = 0;
        boolean flag = false;
        StringJoiner stringJoiner;
        try (ServerSocket serverSocket = new ServerSocket(300)) {
            while (!stop()) {
                System.out.println("enter first WHILE " + loops++);

                try (var socket = serverSocket.accept();
                     var input = new DataInputStream(socket.getInputStream());
                     var out = new DataOutputStream(socket.getOutputStream())) {
                    out.write("Welcome to the Server, print \"STOP\" to exit\n\r".getBytes());
                    //out.flush();
                    while (count != -1) {
                        stringJoiner = new StringJoiner("");
                        stringJoiner.add(PREFIX);
                        while (count != -1) {
                            count = input.read();

                            if (count == 9) {
                                flag = true;
                            }
                            if (count == 13) {
                                stringJoiner.add(new String(inputBytes, 0, position));
                                stringJoiner.add("\n\r");
                                break;
                            }

                            if (count == 10 || count == 9) continue;

                            inputBytes[position] = count;

                            if (position >= inputBytes.length - 1) {
                                stringJoiner.add(new String(inputBytes, 0, inputBytes.length - 1));
                                position = 0;
                                continue;
                            }
                            position++;
                        }
                        out.write(stringJoiner.toString().getBytes());
                        //out.flush();
                        position = 0;
                        if (flag || stop()) break;
                    }
                } catch (Throwable e) {
                    throw new RuntimeException(e);
                }
            }
            //count = 0;
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
    private boolean stop() {
        return STOP.equals(new String(inputBytes, 0, 4).trim());
    }

}

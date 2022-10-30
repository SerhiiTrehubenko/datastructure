package com.tsa.file.streams;

import java.io.IOException;
import java.io.InputStream;

public class MyBufferedInputStream extends InputStream {
    private final InputStream inputStream;

    private static final int INITIAL_CAPACITY = 8*1024;

    private int position;
    private int counter;

    private byte[] body;

    public MyBufferedInputStream(InputStream inputStream) {
        this(INITIAL_CAPACITY, inputStream);
    }
    public MyBufferedInputStream(int initialCapacity,InputStream inputStream) {
        this.body = new byte[initialCapacity];
        this.inputStream = inputStream;
    }
    private void populate() throws IOException {
        position = 0;
        counter = position;
        int readCount = inputStream.read(body, 0, body.length);
        counter += readCount;
    }

    @Override
    public int read(byte[] b) throws IOException {
        return inputStream.read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        if (off > b.length || len > b.length || Math.abs(len + off) > b.length || off < 0 || len < 0) {
            throw  new IndexOutOfBoundsException();
        }
        if(off == b.length || len == 0) {
            return 0;
        }
        if (((counter - position) <= 0 && len >= body.length) && inputStream.available() > 0) {
            return inputStream.read(b, off, len);
        }
        return readBody(b, off, len);
    }

    private int readBody(byte[] b, int off, int len) throws IOException {
        int readCounter = counter-position;
        if (readCounter <= 0 && inputStream.available() <= 0) return -1;
        if (readCounter < len /* readCounter > 0*/) {
            int counter1 = 0;
            for (;;) {
                System.arraycopy(body, position, b, counter1, readCounter);
                position += readCounter;
                counter1 += readCounter;
                if (counter1 == len)return counter1;
                readCounter = len - readCounter;
                if (inputStream.available() > 0) populate();
                if (readCounter > 0 && (counter - position) <= 0) {
                    return counter1;
                }
            }

        }
        if (inputStream.available() > 0) populate();
        System.arraycopy(body, position, b, off, len);
        position += len;
        return len;
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        body = null;
        System.out.println("\nFile closed");
    }

    @Override
    public int read() throws IOException {
        if (position >= counter) {
            populate();
            if (position >= counter) {
                return  -1;
            }
        }
        return body[position++];
    }
}

package com.tsa.file.streams;

import java.io.IOException;
import java.io.OutputStream;

public class MyBufferedOutputStream extends OutputStream {

    private static final int INITIAL_CAPACITY = 8*1024;

    private byte[] body;
    private int position;
    private final OutputStream outputStream;

    public MyBufferedOutputStream(OutputStream outputStream) {
        this(INITIAL_CAPACITY, outputStream);
    }
    public MyBufferedOutputStream(int initialCapacity, OutputStream outputStream) {
        this.body = new byte[initialCapacity];
        this.outputStream = outputStream;
    }

    @Override
    public void write(byte[] b) throws IOException {
        outputStream.write(b, 0, b.length);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        if (off > b.length || len > b.length || Math.abs(len + off) > b.length || off < 0 || len < 0) {
            throw  new IndexOutOfBoundsException();
        }
        if (position >= body.length) {
            flush();
        }
        if (len > (body.length - position)) {
            flush();
            outputStream.write(b, off, len);
            return;
        }
        System.arraycopy(b, off, body, position, len);
        position += len;
    }

    @Override
    public void flush() throws IOException {
        outputStream.write(body, 0, position);
        position = 0;
    }

    @Override
    public void close() throws IOException {
        flush();
        outputStream.close();
        body = null;
    }

    @Override
    public void write(int b) throws IOException {
        if (position >= body.length) {
            flush();
        }
        body[position++] = (byte) b;
    }
}

package com.tsa.file.streams;

import java.io.IOException;
import java.io.OutputStream;

public class MyByteArrayOutputStream extends OutputStream {
    private final static int INITIAL_CAPACITY = 8*1024;
    private final int customCapacity;
    private byte[] body;
    private int position;

    public MyByteArrayOutputStream() {
        this(INITIAL_CAPACITY);
    }

    public MyByteArrayOutputStream(int initialCapacity) {
        if (initialCapacity < 0) throw  new RuntimeException("Capacity can not be less than 0");
        customCapacity = initialCapacity == INITIAL_CAPACITY ? 0 : initialCapacity;
        this.body = new byte[customCapacity > 0 ? customCapacity : initialCapacity];
    }

    @Override
    public void write(byte[] b) {
        if (b.length < (body.length - position)) {
            System.arraycopy(b, 0, body, position, b.length);
            position += b.length;
        } else if (b.length >= (body.length - position) || b.length >= body.length) {
            byte[] newBody = new byte[(body.length + b.length) + 1];
            System.arraycopy(body, 0, newBody, 0, position);
            System.arraycopy(b, 0, newBody, position, b.length);
            position += b.length;
            body = newBody;
        }
    }

    @Override
    public void write(byte[] b, int off, int len) {
        if (off > b.length || len > b.length || Math.abs(len + off) > b.length || off < 0 || len < 0) {
            throw  new IndexOutOfBoundsException();
        }
        if (len < (body.length - position)) {
            System.arraycopy(b, off, body, position, len);
            position += b.length;
        } else if (len >= (body.length - position) || len >= body.length) {
            byte[] newBody = new byte[(body.length + len) + 1];
            System.arraycopy(body, 0, newBody, 0, position);
            System.arraycopy(b, off, newBody, position, len);
            position += len;
            body = newBody;
        }
    }


    @Override
    public void close() {
        this.body = null;
    }

    @Override
    public void write(int b) {
        if (position >= body.length) {
            byte[] newBody = new byte[(int)(body.length * 1.2) + 1];
            System.arraycopy(body, 0, newBody, 0, position);
            body = newBody;
        }
        body[position++] = (byte) b;
    }

    @Override
    public String toString() {
        System.out.println(body.length);
        return new String(body, 0, position);
    }

    public void writeTo (OutputStream outputStream) throws IOException {
        outputStream.write(body, 0, position);
        body = new byte[customCapacity > 0 ? customCapacity : INITIAL_CAPACITY];
        position = 0;
        outputStream.flush();
        outputStream.close();
    }
}

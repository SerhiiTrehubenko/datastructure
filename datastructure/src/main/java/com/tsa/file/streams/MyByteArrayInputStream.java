package com.tsa.file.streams;

import java.io.IOException;
import java.io.InputStream;

public class MyByteArrayInputStream extends InputStream {

    private byte[] body;

    private int position;

    public MyByteArrayInputStream(byte[] b) {
        this.body = b;
    }
    public MyByteArrayInputStream(byte[] b, int off, int len) {
        this.body = new byte[len];
        System.arraycopy(b, off, this.body, 0, len);
    }

    @Override
    public int read() throws IOException {
        if (position >= body.length) return -1;
        return body[position++];
    }

    @Override
    public int read(byte[] b) throws IOException {
        if (b.length > body.length) {
            int readBytes = body.length - position;
            System.arraycopy(body, position, b, 0, body.length - position);
            position += readBytes;
            return readBytes;
        } else if (b.length <= body.length) {
            int readBytes = body.length - position;
            if (b.length < readBytes) {
                System.arraycopy(body, position, b, 0, b.length);
                position += b.length;
                return b.length;
            } else {
                System.arraycopy(body, position, b, 0, readBytes);
                position += readBytes;
                return readBytes;
            }
        }
        return -1;
    }

    @Override
    public int read(byte[] b, int off, int len) {
        if (off > b.length || len > b.length || Math.abs(len + off) > b.length || off < 0 || len < 0) {
            throw  new IndexOutOfBoundsException();
        }
        int readBytes = body.length - position;
        if(len > readBytes) {
            System.arraycopy(body, position, b, off, readBytes);
            position += readBytes;
            return  readBytes;
        } else if (len <= readBytes) {
            System.arraycopy(body, position, b, off, len);
            position += len;
            return len;
        }
        return -1;
    }

    @Override
    public void close(){
        body = null;
    }
}

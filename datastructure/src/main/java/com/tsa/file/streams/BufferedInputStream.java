package com.tsa.file.streams;

import java.io.IOException;
import java.io.InputStream;

public class BufferedInputStream extends InputStream {

    private final InputStream inputStream;

    private static final int INITIAL_CAPACITY = 8 * 1024;

    private int position;

    private int count;
    private byte[] buffer;

    public BufferedInputStream(InputStream inputStream) {
        this(INITIAL_CAPACITY, inputStream);
    }

    public BufferedInputStream(int initialCapacity, InputStream inputStream) {
        this.buffer = new byte[initialCapacity];
        this.inputStream = inputStream;
    }

    @Override
    public int read(byte[] b) throws IOException {
        isClosed();
        return read(b, 0, b.length);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        isClosed();
        if (off > b.length || len > b.length || Math.abs(len + off) > b.length || off < 0 || len < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (off == b.length || len == 0) {
            return 0;
        }
        if ((count - position) <= 0 && inputStream.available() <= 0) {
            return -1;
        }
        if (len >= buffer.length && inputStream.available() > 0) {
            return combineReadToDestArrayFromBufferAndInputStream(b, off, len);
        }
        return readBuffer(b, off, len);
    }

    @Override
    public void close() throws IOException {
        inputStream.close();
        buffer = null;
    }

    @Override
    public int read() throws IOException {
        isClosed();
        if (position >= count) {
            fillBuffer();
            if (position >= count) {
                return -1;
            }
        }
        return buffer[position++];
    }

    @Override
    public int available() throws IOException {
        return inputStream.available();
    }

    private void isClosed() throws IOException {
        if (buffer == null) {
            throw new IOException("InputStream is closed, use new one");
        }
    }

    private void fillBuffer() throws IOException {
        position = count = 0;
        int readCount = inputStream.read(buffer, 0, buffer.length);
        count += readCount;
    }

    private int readBuffer(byte[] b, int off, int len) throws IOException {
        int remainderBytesToReadInBuffer = count - position;
        int readBytes = 0;
        if (remainderBytesToReadInBuffer > 0 && remainderBytesToReadInBuffer < len) {
            readBytes = combineReadToDestArrayFromBufferAndInputStream(b, off, len);
            position += readBytes;
        } else if (remainderBytesToReadInBuffer <= 0 && inputStream.available() > 0) {
            fillBuffer();
            readBytes += readBuffer(b, off, len);
        } else {
            System.arraycopy(buffer, position, b, off, len);
            position += len;
            readBytes = len;
        }
        return readBytes;
    }

    private int combineReadToDestArrayFromBufferAndInputStream(byte[] b, int off, int len) throws IOException {
        int writtenBytesToDestArray = count - position;
        System.arraycopy(buffer, position, b, off, writtenBytesToDestArray);

        int resultInputRead = inputStream.read(b, off + writtenBytesToDestArray, len - writtenBytesToDestArray);
        writtenBytesToDestArray += resultInputRead < 0 ? 0 : resultInputRead;

        return writtenBytesToDestArray;
    }
}

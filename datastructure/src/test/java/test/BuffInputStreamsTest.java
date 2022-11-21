package test;

import com.tsa.file.streams.BufferedInputStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringJoiner;

public class BuffInputStreamsTest {
    @Test
    void testReadWithOneParameterWithSizeArrayThatChanges() {
        for (int i = 1; i <= 80; i++) {
            StringJoiner resultBuffer = new StringJoiner("\n");
            StringJoiner resultMyBuffer = new StringJoiner("\n");
            int numberOfBytesInFile;
            int readBytesByMyBuff = 0;
            try (var buff = new java.io.BufferedInputStream(new FileInputStream("text.txt"));
                 var myBuff = new BufferedInputStream(new FileInputStream("text.txt"))) {
                byte[] bytes = new byte[i];
                byte[] myBytes = new byte[i];
                int count;
                numberOfBytesInFile = myBuff.available();
                while ((count = buff.read(bytes)) != -1) {
                    resultBuffer.add(new String(bytes, "windows-1251").substring(0, count));
                }
                while ((count = myBuff.read(myBytes)) != -1) {
                    resultMyBuffer.add(new String(myBytes, "windows-1251").substring(0, count));
                    readBytesByMyBuff += count;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            assertEquals(resultMyBuffer.toString(), resultBuffer.toString());
            assertEquals(numberOfBytesInFile, readBytesByMyBuff);
        }
    }

    /**
     * Simulates cases When size buffer of BufferedStream less than size of an Array to which bytes are read
     * and vice versa.
     * Cases:
     * 1) "sizeArrays" bigger than buffer of BufferedStream -> direct reading from InputStream
     * (clause while  sizeBufferStreams <= sizeArrays -> com.tsa.file.streams.BufferedInputStream line 44)
     * 2) Combined reading from buffer of BufferedStream and direct reading from InputStream when
     * ("len" > "count - position")
     * (clause while  sizeBufferStreams > sizeArrays com.tsa.file.streams.BufferedInputStream line 88)
     */
    @Test
    void testReadWithThreeParametersWithSizeArrayAndSizeBufferStreamsThatChange() {
        int sizeBufferStreams = 1;
        int sizeArrays = 80;
        while (sizeBufferStreams != 80 && sizeArrays != 1) {
            StringJoiner resultBuffer = new StringJoiner("\n");
            StringJoiner resultMyBuffer = new StringJoiner("\n");
            int numberOfBytesInFile;
            int readBytesByMyBuff = 0;
            try (var buff = new java.io.BufferedInputStream(new FileInputStream("text2.txt"), sizeBufferStreams);
                 var myBuff = new BufferedInputStream(sizeBufferStreams, new FileInputStream("text2.txt"))) {
                byte[] bytes = new byte[sizeArrays];
                byte[] myBytes = new byte[sizeArrays];
                numberOfBytesInFile = myBuff.available();
                int count;
                while ((count = buff.read(bytes, 0, bytes.length)) != -1) {
                    resultBuffer.add(new String(bytes, "windows-1251").substring(0, count));

                }
                while ((count = myBuff.read(myBytes, 0, myBytes.length)) != -1) {
                    resultMyBuffer.add(new String(myBytes, "windows-1251").substring(0, count));
                    readBytesByMyBuff += count;
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            assertEquals(resultMyBuffer.toString().trim(), resultBuffer.toString().trim());
            assertEquals(readBytesByMyBuff, numberOfBytesInFile);
            sizeBufferStreams += 1;
            sizeArrays -= 1;
        }
    }

    @Test
    void testReadWhenOffEqualsArraySizeAndLenEqualsNil() {
        int readBytesOffEqualsArrayLength;
        int readBytesLenEqualsNil;
        try (var myBuff = new BufferedInputStream(new FileInputStream("text2.txt"))) {
            byte[] array = new byte[50];
            readBytesOffEqualsArrayLength = myBuff.read(array, 20, 0);
            readBytesLenEqualsNil = myBuff.read(array, array.length, 0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(0, readBytesOffEqualsArrayLength);
        assertEquals(0, readBytesLenEqualsNil);
    }

    @Test
    void testThrowExceptionWhenOffAndLenNotInBoundaries() {
        try (var myBuff = new BufferedInputStream(new FileInputStream("text.txt"))) {
            byte[] myBytes = new byte[50];
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, -1, 50));
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, 20, 80));
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, 20, -20));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadWithoutParameters() {
        StringJoiner resultBuffer = new StringJoiner(" ");
        StringJoiner resultMyBuffer = new StringJoiner(" ");
        try (var buff = new java.io.BufferedInputStream(new FileInputStream("text.txt"));
             var myBuff = new BufferedInputStream(new FileInputStream("text.txt"))) {
            int count = 0;
            while (count++ <= 5) {
                resultBuffer.add(String.valueOf((char) buff.read()));
            }
            count = 0;
            while (count++ <= 5) {
                resultMyBuffer.add(String.valueOf((char) myBuff.read()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(resultMyBuffer.toString(), resultBuffer.toString());
        assertEquals(11, resultMyBuffer.length());
        assertEquals("T h i s   i", resultMyBuffer.toString());
    }

    @Test
    void testCloseThrowsIOExceptionWhetCallReadAfterClose() throws IOException {
        var myBuff = new BufferedInputStream(0, new FileInputStream("text.txt"));
        myBuff.close();
        assertThrows(IOException.class, myBuff::read);
        assertThrows(IOException.class, () -> myBuff.read(new byte[10]));
        assertThrows(IOException.class, () -> myBuff.read(new byte[10], 0, 5));
    }
}

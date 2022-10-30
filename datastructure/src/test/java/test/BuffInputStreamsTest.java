package test;

import com.tsa.file.streams.MyBufferedInputStream;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.StringJoiner;

public class BuffInputStreamsTest {
    @Test
    void testReadWithOneParameter() {
        StringJoiner resultBuffer = new StringJoiner("\n");
        StringJoiner resultMyBuffer = new StringJoiner("\n");
        try (var buff = new BufferedInputStream(new FileInputStream("text.txt"));
             var myBuff = new MyBufferedInputStream(new FileInputStream("text.txt"))) {
            byte[] bytes = new byte[50];
            byte[] myBytes = new byte[50];
            int count;

            while ((count = buff.read(bytes)) != -1) {
                resultBuffer.add(new String(bytes, "windows-1251").substring(0, count));
            }
            while ((count = myBuff.read(myBytes)) != -1) {
                resultMyBuffer.add(new String(myBytes, "windows-1251").substring(0, count));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(resultMyBuffer.toString(), resultBuffer.toString());
        assertEquals(214, resultMyBuffer.length());
        assertTrue(resultMyBuffer.toString().contains("This is true of both spoken"));
    }
    @Test
    void testReadWithThreeParameters() {
        StringJoiner resultBuffer = new StringJoiner("\n");
        StringJoiner resultMyBuffer = new StringJoiner("\n");
        try (var buff = new BufferedInputStream(new FileInputStream("text.txt"));
             var myBuff = new MyBufferedInputStream(new FileInputStream("text.txt"))) {
            byte[] bytes = new byte[50];
            byte[] myBytes = new byte[50];
            int count;
            while ((count = buff.read(bytes, 0, bytes.length)) != -1) {
                resultBuffer.add(new String(bytes, "windows-1251").substring(0, count));
            }
            while ((count = myBuff.read(myBytes, 0, myBytes.length)) != -1) {
                resultMyBuffer.add(new String(myBytes, "windows-1251").substring(0, count));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertEquals(resultMyBuffer.toString(), resultBuffer.toString());
        assertEquals(214, resultMyBuffer.length());
        assertTrue(resultMyBuffer.toString().contains("This is true of both spoken"));
    }

    @Test
    void testThrowExceptionWhenOffAndLenNotInBoundaries() {
        try (var myBuff = new MyBufferedInputStream(new FileInputStream("text.txt"))) {
            byte[] myBytes = new byte[50];
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, -1, 50));
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, 20, 80));
            assertThrows(IndexOutOfBoundsException.class, () -> myBuff.read(myBytes, 20, -20));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testReadNoParameters() {
        StringJoiner resultBuffer = new StringJoiner(" ");
        StringJoiner resultMyBuffer = new StringJoiner(" ");
        try (var buff = new BufferedInputStream(new FileInputStream("text.txt"));
             var myBuff = new MyBufferedInputStream(new FileInputStream("text.txt"))) {
            int count = 0;
            while (count++ <= 5) {
                resultBuffer.add(String.valueOf((char)buff.read()));
            }
            count = 0;
            while (count++ <= 5) {
                resultMyBuffer.add(String.valueOf((char)myBuff.read()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(resultMyBuffer.toString(), resultBuffer.toString());
        assertEquals(11, resultMyBuffer.length());
        assertEquals("T h i s   i", resultMyBuffer.toString());
    }

    @Test
    void testClose() throws IOException{
        var buff = new MyBufferedInputStream(new FileInputStream("text.txt"));
        try {
            for (int i = 0; i < 10; i++) {
                System.out.print((char) buff.read());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buff.close();
        }
    }
}

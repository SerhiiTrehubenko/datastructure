package test;

import com.tsa.file.streams.MyByteArrayInputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.StringJoiner;

import static org.junit.jupiter.api.Assertions.*;

class MyByteArrayInputStreamTest {

    @Test
    void testReadNoParameters() {
        StringJoiner stringJoinerJava = new StringJoiner("");
        StringJoiner stringJoinerMy = new StringJoiner("");
        try (var input = new ByteArrayInputStream(BYTES);
        var myInput = new MyByteArrayInputStream(BYTES)){
            for (int i = 0; i < 73; i++) {
                stringJoinerJava.add(String.valueOf(input.read()));
            }
            for (int i = 0; i < 73; i++) {
                stringJoinerMy.add(String.valueOf(myInput.read()));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(stringJoinerJava.toString(), stringJoinerMy.toString());
    }

    @Test
    void testReadWithOneParameter() {
        int number = 50;
        byte[] bytesJava = new byte[number];
        byte[] bytesMy = new byte[number];
        try (var input = new ByteArrayInputStream(BYTES, 10, BYTES.length - 10);
             var myInput = new MyByteArrayInputStream(BYTES,10, BYTES.length - 10)){

            input.read(bytesJava);
            myInput.read(bytesMy);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(new String(bytesJava), new String(bytesMy));
    }

    @Test
    void testReadWithThree() {
        int number = 50;
        byte[] bytesJava = new byte[number];
        byte[] bytesMy = new byte[number];
        try (var input = new ByteArrayInputStream(BYTES);
             var myInput = new MyByteArrayInputStream(BYTES)){

            input.read(bytesJava, 5, bytesJava.length -5);
            myInput.read(bytesMy, 5, bytesMy.length -5);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(new String(bytesJava), new String(bytesMy));
    }

    private final static byte[] BYTES = ("This is true of both spoken/written" +
            " languages and programming languages.").getBytes();
}
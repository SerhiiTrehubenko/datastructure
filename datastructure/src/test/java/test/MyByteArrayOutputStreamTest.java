package test;

import com.tsa.file.streams.MyByteArrayOutputStream;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class MyByteArrayOutputStreamTest {

    @Test
    void write() {
        try (var out = new ByteArrayOutputStream(80);
        var outMy = new MyByteArrayOutputStream(80)){
            out.write(BYTES);
            outMy.write(BYTES);

            assertEquals(out.toString(), outMy.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void testWriteNoParameters() {
        try (var out = new ByteArrayOutputStream(80);
             var outMy = new MyByteArrayOutputStream(80)){
            out.write(BYTES[0]);
            outMy.write(BYTES[0]);

            assertEquals(out.toString(), outMy.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testWriteTo() {
        Path pathJava = Path.of("text-java.txt");
        Path pathMy = Path.of("text-my.txt");
        try (var out = new ByteArrayOutputStream(50);
             var outMy = new MyByteArrayOutputStream(50);
             var outStream = new FileOutputStream(pathJava.toFile());
             var outStreamMy = new FileOutputStream(pathMy.toFile())){

            out.write(BYTES);
            outMy.write(BYTES);
            out.writeTo(outStream);
            outMy.writeTo(outStreamMy);
            System.out.println(outMy);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(pathJava.toFile().length(), pathMy.toFile().length());
    }

    private final static byte[] BYTES = ("This is true of both spoken/written" +
            " languages and programming languages.").getBytes();
}
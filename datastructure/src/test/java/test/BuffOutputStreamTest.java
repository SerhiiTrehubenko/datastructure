package test;

import com.tsa.file.streams.MyBufferedOutputStream;
import org.junit.jupiter.api.Test;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class BuffOutputStreamTest {

    @Test
    void testWriteWithOneParameter() {
        Path pathJava = Path.of("text-java.txt");
        Path pathMy = Path.of("text-my.txt");
        try (var out = new BufferedOutputStream(new FileOutputStream(pathJava.toFile()));
             var myOut = new MyBufferedOutputStream(new FileOutputStream(pathMy.toFile()))) {
            out.write(BYTES);
            myOut.write(BYTES);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(pathJava.toFile().length(), pathMy.toFile().length());
        pathJava.toFile().delete();
        pathMy.toFile().delete();
    }

    @Test
    void testWriteWithThreeParameters() {
        Path pathJava = Path.of("text-java.txt");
        Path pathMy = Path.of("text-my.txt");
        try (var out = new BufferedOutputStream(new FileOutputStream(pathJava.toFile()));
             var myOut = new MyBufferedOutputStream(new FileOutputStream(pathMy.toFile()))) {
            out.write(BYTES, 0, BYTES.length);
            myOut.write(BYTES, 0, BYTES.length);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(pathJava.toFile().length(), pathMy.toFile().length());
        pathJava.toFile().delete();
        pathMy.toFile().delete();
    }

    @Test
    void testWriteNoParameters() {
        Path pathMy = Path.of("text-my.txt");
        try (var myOut = new MyBufferedOutputStream(50 ,new FileOutputStream(pathMy.toFile()))) {
            int i = 0;
            for (; i < 10; i++) {
                myOut.write(BYTES[i]);
            }
            myOut.write(BYTES, i, BYTES.length - i);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        assertEquals(72, pathMy.toFile().length());
        pathMy.toFile().delete();
    }

    @Test
    void flush() throws IOException {
        Path pathMy = Path.of("text-my.txt");
        var myOut = new MyBufferedOutputStream(new FileOutputStream(pathMy.toFile()));

            myOut.write(BYTES, 0, 50);
            assertEquals(0, pathMy.toFile().length());
            myOut.flush();
            assertEquals(50, pathMy.toFile().length());
        myOut.close();
    }

    @Test
    void close() throws IOException {
        Path pathMy = Path.of("text-my.txt");
        var myOut = new MyBufferedOutputStream(new FileOutputStream(pathMy.toFile()));

        myOut.write(BYTES, 0, 70);
        assertEquals(0, pathMy.toFile().length());
        myOut.close();
        assertEquals(70, pathMy.toFile().length());
    }



    private final static byte[] BYTES = ("This is true of both spoken/written" +
            " languages and programming languages.").getBytes();
}
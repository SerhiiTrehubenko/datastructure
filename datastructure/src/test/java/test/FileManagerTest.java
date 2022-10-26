package test;

import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static com.tsa.file.services.FileManager.*;

import static org.junit.jupiter.api.Assertions.*;

class FileManagerTest {

    private static Path mainPath;

    @BeforeEach
    public void init() throws IOException {
        mainPath = Paths.get(Path.of("").toAbsolutePath().toString(),"test-file-manager");
        List<String> folders = new ArrayList<>(Arrays.asList("one", "two", "three"));
        for (String folder : folders) {
            Path variant = Paths.get(mainPath.toString(), String.join("/", folders));
            variant.toFile().mkdirs();
            for (int i = 0; i < folder.length(); i++) {

                Path temporal = Path.of(variant.subpath((variant.getNameCount()-folder.length())-1, variant.getNameCount() - i).toString());

                File.createTempFile("text", ".txt", temporal.toAbsolutePath().toFile());

            }
            Collections.rotate(folders, 1);
        }
    }
    @AfterEach
    public void clearDirectory() {
        delete(mainPath.toString());
    }

    @Test
    void testCountFiles() {
        assertEquals(9, countFiles(mainPath.toAbsolutePath().toString()));
    }

    @Test
    void testCountDirs() {
        assertEquals(9, countDirs(mainPath.toAbsolutePath().toString()));
    }

    @Test
    void testCopy() {
        copy(mainPath.toAbsolutePath().toString().concat("\\three"),
                mainPath.toAbsolutePath().toString().concat("\\one"));
        assertEquals(6, countFiles(mainPath.toAbsolutePath().toString().concat("\\one")));
        assertEquals(5, countDirs(mainPath.toAbsolutePath().toString().concat("\\one")));
        assertEquals(12, countFiles(mainPath.toString()));
    }

    @Test
    void testMove() {
        move(mainPath.toAbsolutePath().toString().concat("\\three"),
                mainPath.toAbsolutePath().toString().concat("\\one"));
        assertEquals(6, countFiles(mainPath.toAbsolutePath().toString().concat("\\one")));
        assertEquals(5, countDirs(mainPath.toAbsolutePath().toString().concat("\\one")));
        assertEquals(9, countFiles(mainPath.toString()));
    }
    @Test
    void testDelete() {
        assertEquals(3, Objects.requireNonNull(mainPath.toFile().listFiles()).length);
        delete(mainPath.toAbsolutePath().toString().concat("\\three"));
        assertEquals(2, Objects.requireNonNull(mainPath.toFile().listFiles()).length);
    }

}
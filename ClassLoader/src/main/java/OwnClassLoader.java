import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by CodeCouple.pl
 */
public class OwnClassLoader extends ClassLoader {

    static final String CLASSES = "classes";

    @Override
    protected Class<?> findClass(String name) {
        byte[] bytesFromFile = getBytesFrom(name);
        return defineClass(name, bytesFromFile, 0, bytesFromFile.length);
    }

    byte[] getBytesFrom(String fileName) {
        try {
            return Files.readAllBytes(Paths.get(CLASSES, fileName + ".class"));
        } catch (IOException e) {
            return new byte[0];
        }
    }
}

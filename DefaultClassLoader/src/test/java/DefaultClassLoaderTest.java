import jdk.nashorn.tools.Shell;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import sun.misc.Launcher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by CodeCouple.pl
 */
class DefaultClassLoaderTest {

    @Test
    void shouldReturnNullClassLoaderWhenLoadStringClass() {
        // Given
        Class<String> stringClass = String.class;
        // When
        ClassLoader classLoader = stringClass.getClassLoader();
        // Then
        assertThat(classLoader).isNull();
    }

    @Test
    void shouldReturnExtensionClassLoaderWhenLoadExtensionClass() {
        // Given
        Class<Shell> extensionClass = Shell.class;
        // When
        ClassLoader classLoader = extensionClass.getClassLoader();
        // Then
        assertThat(classLoader).isNotNull();
        assertThat(classLoader).isInstanceOf(getExtensionClassLoaderClass());
    }

    private Class<? extends ClassLoader> getExtensionClassLoaderClass() {
        return Launcher.getLauncher().getClassLoader().getParent().getClass();
    }

    @Test
    void shouldReturnApplicationClassLoaderWhenLoadApplicationClass() {
        // Given
        Class<MyClass> applicationClass = MyClass.class;
        // When
        ClassLoader classLoader = applicationClass.getClassLoader();
        // Then
        assertThat(classLoader).isNotNull();
        assertThat(classLoader).isInstanceOf(getApplicationClassLoaderClass());
    }

    private Class<? extends ClassLoader> getApplicationClassLoaderClass() {
        return Launcher.getLauncher().getClassLoader().getClass();
    }

    @Test
    void shouldThrowClassNotFoundExceptionWhenCannotFindClassOnClassPath() {
        // When
        Executable executable = () -> Class.forName("random.name");
        // Then
        assertThrows(ClassNotFoundException.class, executable);
    }

    @Test
    void shouldThrowNoClassDefFoundErrorWhenCannotLoadClass() {
        // Given
        MyBrokenWrapper wrapper = new MyBrokenWrapper();
        // When
        Executable executable = wrapper::create;
        // Then
        assertThrows(NoClassDefFoundError.class, executable);
    }

}

class MyClass {

}

class MyBrokenClass {

    static int value;

    static {
        value = 1/0;
    }

}

class MyBrokenWrapper {

    MyBrokenClass create() {
        try {
            new MyBrokenClass();
        } catch (ExceptionInInitializerError e) {

        }
        return new MyBrokenClass();
    }

}
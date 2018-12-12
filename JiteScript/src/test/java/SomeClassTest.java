import me.qmx.jitescript.JDKVersion;
import me.qmx.jitescript.JiteClass;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class SomeClassTest {

    private CustomClassLoader customClassLoader = new CustomClassLoader();

    @Test
    void shouldReturnSomeClass() throws IllegalAccessException, InstantiationException {
        // Given
        JiteClass someClass = new JiteClass("SomeClass");
        someClass.defineDefaultConstructor();

        byte[] bytes = someClass.toBytes(JDKVersion.V1_8);
        Class<?> clazz = customClassLoader.defineClass("SomeClass", bytes);

        // When
        Object newInstance = clazz.newInstance();

        // Then
        assertThat(newInstance).isNotNull();
    }

}

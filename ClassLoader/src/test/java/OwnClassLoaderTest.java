import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class OwnClassLoaderTest {

    @Test
    void shouldLoadOwnClassFromBytes() {
        // Given
        OwnClassLoader ownClassLoader = new OwnClassLoader();
        // When
        Class<?> ownClass = ownClassLoader.findClass("OwnClass");
        // Then
        assertThat(ownClass).isNotNull();
        assertThat(ownClass.getClassLoader().getClass().getName()).isEqualTo("OwnClassLoader");
    }


}

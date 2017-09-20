import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by CodeCouple.pl
 */
class ExceptionExample {

    @Test
    void someTest(){
        //When
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                this::someMethod);

        //Then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class);
        assertThat(exception).hasMessage("Assert Throws");
    }

    void someMethod(){
        throw new IllegalArgumentException("Assert Throws");
    }

}

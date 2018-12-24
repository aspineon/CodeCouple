import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * Created by CodeCouple.pl
 */
class OptionalTest {

    @Test
    void shouldThrowNullPointerExceptionWhenValueIsNullOnOf() {
        // When
        Executable optionalWithNull =
                () -> Optional.of(null);

        // Then
        assertThrows(NullPointerException.class, optionalWithNull);
    }

    @Test
    void shouldNotThrowNullPointerExceptionWhenValueIsNullOnOfNullable() {
        // When
        Executable optionalWithNull =
                () -> Optional.ofNullable(null);

        // Then
        assertAll(optionalWithNull);
    }

    @Test
    void shouldReturnTrueWhenValueIsPresent() {
        // Given
        Optional<String> optional = Optional.of("");

        // When
        boolean isPresent = optional.isPresent();

        // Then
        assertThat(isPresent).isTrue();
    }

    @Test
    void shouldReturnFalseWhenValueIsNotPresent() {
        // Given
        Optional<String> optional = Optional.empty();

        // When
        boolean isPresent = optional.isPresent();

        // Then
        assertThat(isPresent).isFalse();
    }

    @Test
    void shouldCallMethodWhenValueIsPresentOnIfPresent() {
        // Given
        Optional<String> optional = Optional.of("call");
        Slepper slepper = Mockito.mock(Slepper.class);
        doCallRealMethod().when(slepper).consumer("call");

        // When
        optional.ifPresent(slepper::consumer);

        // Then
        verify(slepper).consumer("call");
    }

    @Test
    void shouldReturnDefaultWhenValueIsNotPresentOnOrElse() {
        // Given
        Optional<String> optional = Optional.empty();

        // When
        String value = optional.orElse("orElse");

        // Then
        assertThat(value).isEqualTo("orElse");
    }

    @Test
    void shouldCallMethodWhenValueIsPresentOnOrElse() {
        // Given
        Optional<String> optional = Optional.of("orElse");
        Slepper slepper = Mockito.mock(Slepper.class);
        when(slepper.getDefaultValue()).thenCallRealMethod();

        // When
        String value = optional.orElse(slepper.getDefaultValue());

        // Then
        verify(slepper).sleep();
        assertThat(value).isEqualTo("orElse");
    }

    @Test
    void shouldReturnDefaultWhenValueIsNotPresentOnOrElseGet() {
        // Given
        Optional<String> optional = Optional.empty();

        // When
        String value = optional.orElseGet(() -> "orElse");

        // Then
        assertThat(value).isEqualTo("orElse");
    }

    @Test
    void shouldNotCallMethodWhenValueIsPresentOnOrElseGet()  {
        // Given
        Optional<String> optional = Optional.of("orElse");
        Slepper slepper = Mockito.mock(Slepper.class);
        when(slepper.getDefaultValue()).thenCallRealMethod();

        // When
        String value = optional.orElseGet(slepper::getDefaultValue);

        // Then
        verifyZeroInteractions(slepper);
        assertThat(value).isEqualTo("orElse");
    }


    @Test
    void shouldThrowIllegalStateExceptionWhenValueIsEmpty() {
        // Given
        Optional<String> optional = Optional.empty();

        // When
        Executable optionalWhichThrowException =
                () -> optional.orElseThrow(() -> new IllegalStateException());

        // Then
        assertThrows(IllegalStateException.class, optionalWhichThrowException);
    }


}

class Slepper {

    String getDefaultValue()  {
        sleep();
        return "default";
    }

    void consumer(final String toConsume) {

    }

    void sleep() {

    }

}

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Clock;
import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Created by CodeCouple.pl
 */
class GoodClassWhichDependsOnTimeTest {

    @Test
    void shouldReturnFalseWhenDateTimeIsAfterOneHourAsTodayDateTime() {
        // Given
        Date date = getExpirationDate(1,23);

        ClassWithFixedTime fixedTime = new ClassWithFixedTime(date);
        Clock clock = Mockito.mock(Clock.class);

        when(clock.millis()).thenReturn(getCurrentDateInMillis());

        GoodClassWhichDependsOnTime good = new GoodClassWhichDependsOnTime(clock);
        // When
        boolean goingToExpireToday = good.isGoingToExpireToday(fixedTime);
        // Then
        assertThat(goingToExpireToday).isFalse();
    }

    @Test
    void shouldReturnFalseWhenDateTimeIsAfterOneDayAsTodayDateTime() {
        // Given
        Date date = getExpirationDate(2,1);

        ClassWithFixedTime fixedTime = new ClassWithFixedTime(date);
        Clock clock = Mockito.mock(Clock.class);

        when(clock.millis()).thenReturn(getCurrentDateInMillis());

        GoodClassWhichDependsOnTime good = new GoodClassWhichDependsOnTime(clock);
        // When
        boolean goingToExpireToday = good.isGoingToExpireToday(fixedTime);
        // Then
        assertThat(goingToExpireToday).isFalse();
    }

    @Test
    void shouldReturnTrueWhenDateTimeIsSameAsTodayDateTime() {
        // Given
        Date date = getExpirationDate(1,22);

        ClassWithFixedTime fixedTime = new ClassWithFixedTime(date);
        Clock clock = Mockito.mock(Clock.class);

        when(clock.millis()).thenReturn(getCurrentDateInMillis());

        GoodClassWhichDependsOnTime good = new GoodClassWhichDependsOnTime(clock);
        // When
        boolean goingToExpireToday = good.isGoingToExpireToday(fixedTime);
        // Then
        assertThat(goingToExpireToday).isTrue();
    }

    @Test
    void shouldReturnTrueWhenDateTimeIsBeforeTodayDateTime() {
        // Given
        Date date = getExpirationDate(1,21);

        ClassWithFixedTime fixedTime = new ClassWithFixedTime(date);
        Clock clock = Mockito.mock(Clock.class);

        when(clock.millis()).thenReturn(getCurrentDateInMillis());

        GoodClassWhichDependsOnTime good = new GoodClassWhichDependsOnTime(clock);
        // When
        boolean goingToExpireToday = good.isGoingToExpireToday(fixedTime);
        // Then
        assertThat(goingToExpireToday).isTrue();
    }

    private Date getExpirationDate(int day, int hour) {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.set(2018, Calendar.NOVEMBER, day, hour, 30, 30);
        return expirationDate.getTime();
    }

    private long getCurrentDateInMillis() {
        Calendar expirationDate = Calendar.getInstance();
        expirationDate.set(2018, Calendar.NOVEMBER, 1, 22, 30, 30);
        return expirationDate.getTimeInMillis();
    }

}
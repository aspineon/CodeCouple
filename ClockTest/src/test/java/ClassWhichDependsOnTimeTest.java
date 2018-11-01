import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class ClassWhichDependsOnTimeTest {


    @Test
    void shouldReturnTrueWhenDateIsSameAsToday() {
        // Given
        Date date = DateUtils.addHours(new Date(), 2);
        ClassWithFixedTime fixedTime = new ClassWithFixedTime(date);
        ClassWhichDependsOnTime bad = new ClassWhichDependsOnTime();
        // When
        boolean goingToExpireToday = bad.isGoingToExpireToday(fixedTime);
        // Then
        assertThat(goingToExpireToday).isTrue();
    }

}
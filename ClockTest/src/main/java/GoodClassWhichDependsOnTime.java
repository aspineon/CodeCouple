import java.time.Clock;
import java.util.concurrent.TimeUnit;

/**
 * Created by CodeCouple.pl
 */
class GoodClassWhichDependsOnTime {

    private final Clock clock;

    GoodClassWhichDependsOnTime(Clock clock) {
        this.clock = clock;
    }

    boolean isGoingToExpireToday(ClassWithFixedTime classWithFixedTime) {
        long result = clock.millis() - classWithFixedTime.getDate().getTime();
        return result >= 0 && TimeUnit.MILLISECONDS.toDays(result) == 0;
    }

}

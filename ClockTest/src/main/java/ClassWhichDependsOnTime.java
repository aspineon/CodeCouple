import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by CodeCouple.pl
 */
class ClassWhichDependsOnTime {

    boolean isGoingToExpireToday(ClassWithFixedTime classWithFixedTime) {
        long result = classWithFixedTime.getDate().getTime() - new Date().getTime();
        return result >= 0 && TimeUnit.MILLISECONDS.toDays(result) == 0;
    }

}

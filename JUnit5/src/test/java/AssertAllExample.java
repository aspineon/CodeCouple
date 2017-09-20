import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

/**
 * Created by CodeCouple.pl
 */
class AssertAllExample {

    @Test
    void someTest(){
        assertAll("CodeCouple",
                () -> assertThat("Code").contains("e"),
                () -> assertThat("Couple").contains("e"));
    }

}

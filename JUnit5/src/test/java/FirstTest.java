import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class FirstTest {

    @Test
    void someTest(){
        assertThat("CodeCouple is the best!").contains("best");
    }

}

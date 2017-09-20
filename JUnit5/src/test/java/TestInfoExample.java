import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class TestInfoExample {

    @Test
    void someTest(TestInfo testInfo){
        assertThat(testInfo.getTestMethod().get().getName()).isEqualTo("someTest");
    }

}

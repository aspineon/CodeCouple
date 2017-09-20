import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
@DisplayName("FirstTest")
class DisplayNameExample {

    @Test
    @DisplayName("First test method")
    void someTest(){
        assertThat("CodeCouple").contains("Code");
    }

}
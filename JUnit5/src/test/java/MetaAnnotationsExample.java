import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class MetaAnnotationsExample {

    @Test
    @CodeCouple
    void someTest(){
        assertThat("CodeCouple").contains("Code");
    }

    @Test
    @Tag("integration")
    @Tag("CodeCouple")
    void someSecondTest(){
        assertThat("CodeCouple").contains("Code");
    }

}

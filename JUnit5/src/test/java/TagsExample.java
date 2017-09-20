import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

/**
 * Created by CodeCouple.pl
 */
class TagsExample {

    @Test
    void someUnitTest(){
        //Some logic
    }

    @Test
    @Tag("integration")
    void someIntegrationTest(){
        //Some logic
    }

}

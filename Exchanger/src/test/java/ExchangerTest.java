import org.junit.jupiter.api.Test;

import java.util.concurrent.Exchanger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class ExchangerTest {

    @Test
    void shouldExchangeValuesBetweenThreads() throws InterruptedException {
        // Given
        Exchanger<String> exchanger = new Exchanger<>();

        Job firstJob = new Job(exchanger, "first");
        Job secondJob = new Job(exchanger, "second");

        // When
        firstJob.start();
        secondJob.start();

        // Then
        firstJob.join();
        secondJob.join();

        assertThat(firstJob.getExchangedValue()).isEqualTo("second");
        assertThat(secondJob.getExchangedValue()).isEqualTo("first");
    }


}

class Job extends Thread {

    private final Exchanger<String> exchanger;

    private final String value;

    private String exchangedValue;

    Job(Exchanger<String> exchanger, String value) {
        this.exchanger = exchanger;
        this.value = value;
    }

    @Override
    public void run() {
        try {
            exchangedValue = exchanger.exchange(value);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    String getExchangedValue() {
        return exchangedValue;
    }
}
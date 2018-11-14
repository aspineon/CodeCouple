import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class CountDownLatchTest {


    private static final int THREAD_COUNT = 3;

    @Test
    void shouldCountDownToThree() throws InterruptedException {
        // Given
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_COUNT);
        // When
        runJobs(countDownLatch);
        countDownLatch.await(400, TimeUnit.MILLISECONDS);
        // Then
        assertThat(countDownLatch.getCount()).isEqualTo(0);
    }

    private void runJobs(CountDownLatch countDownLatch) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(THREAD_COUNT);
        threadPool.schedule(new Job(countDownLatch),100, TimeUnit.MILLISECONDS);
        threadPool.schedule(new Job(countDownLatch),300, TimeUnit.MILLISECONDS);
        threadPool.schedule(new Job(countDownLatch),200, TimeUnit.MILLISECONDS);
        threadPool.shutdown();
    }


}

class Job implements Runnable {

    private final CountDownLatch countDownLatch;

    Job(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void run() {
        System.out.println(countDownLatch.getCount());
        countDownLatch.countDown();
    }
}

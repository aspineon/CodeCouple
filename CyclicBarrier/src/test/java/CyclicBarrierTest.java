import org.junit.jupiter.api.Test;

import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class CyclicBarrierTest {


    private static final int NUMBER_OF_JOBS = 4; //3 + 1 main job

    @Test
    void shouldRunJobAfterOtherJobs() throws BrokenBarrierException, InterruptedException {
        // Given
        Final finalJob = new Final();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER_OF_JOBS, finalJob);
        // When
        runJobs(cyclicBarrier);
        cyclicBarrier.await();
        // Then
        assertThat(finalJob.value).isEqualTo(1);
    }

    private void runJobs(CyclicBarrier cyclicBarrier) {
        ScheduledExecutorService threadPool = Executors.newScheduledThreadPool(NUMBER_OF_JOBS);
        threadPool.schedule(new Job(cyclicBarrier),100, TimeUnit.MILLISECONDS);
        threadPool.schedule(new Job(cyclicBarrier),300, TimeUnit.MILLISECONDS);
        threadPool.schedule(new Job(cyclicBarrier),200, TimeUnit.MILLISECONDS);
        threadPool.shutdown();
    }

}

class Job implements Runnable {

    private final CyclicBarrier cyclicBarrier;

    Job(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

    public void run() {
        System.out.println(cyclicBarrier.getNumberWaiting());
        try {
            cyclicBarrier.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}

class Final implements Runnable {

    int value = 0;

    public void run() {
        value = 1;
    }
}
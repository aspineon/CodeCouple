import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Created by CodeCouple.pl
 */
class SemaphoreTest {

    @Test
    void voidA() throws InterruptedException {
        // Given
        Semaphore semaphore = new Semaphore(2);

        Sleeper sleeper1 = new Sleeper(semaphore);
        Sleeper sleeper2 = new Sleeper(semaphore);
        Sleeper sleeper3 = new Sleeper(semaphore);
        Sleeper sleeper4 = new Sleeper(semaphore);

        // When
        sleeper1.start();
        sleeper2.start();
        sleeper3.start();
        sleeper4.start();

        sleeper1.join();
        sleeper2.join();
        sleeper3.join();
        sleeper4.join();

        //Then
    }


}

class Sleeper extends Thread {

    private final Semaphore semaphore;

    Sleeper(Semaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        try {
            Thread.sleep((new Random().nextInt(20) * 10));
            System.out.println(Thread.currentThread().getName() + " Before Permits: " + semaphore.availablePermits());
            System.out.println(Thread.currentThread().getName() + " Has Queued Threads: " + semaphore.hasQueuedThreads
                    ());
            System.out.println(Thread.currentThread().getName() + " Queued Length: " + semaphore.getQueueLength());
            if (semaphore.tryAcquire()) {
                System.out.println(Thread.currentThread().getName() + " Acquire");
                System.out.println(Thread.currentThread().getName() + " Job");
                Thread.sleep(new Random().nextInt(3) * 3000);
                System.out.println(Thread.currentThread().getName() + " Release");
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " After Permits: " + semaphore.availablePermits());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}



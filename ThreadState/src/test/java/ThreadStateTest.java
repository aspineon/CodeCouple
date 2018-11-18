import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by CodeCouple.pl
 */
class ThreadStateTest {

    @Test
    void shouldReturnNewState() {
        // Given
        final Thread newThread = new Thread();
        // Then
        assertThat(newThread.getState()).isEqualTo(Thread.State.NEW);
    }

    @Test
    void shouldReturnRunnableState() throws InterruptedException {
        // Given
        final Thread runnableThread = new Thread(() -> {
            assertThat(Thread.currentThread().getState()).isEqualTo(Thread.State.RUNNABLE);
        });
        // When
        runnableThread.start();
        runnableThread.join();
    }

    @Test
    void shouldReturnBlockedState() throws InterruptedException {
        // Given
        final Thread synchronizedThread = new Thread(this::synchronizedMethod);
        final Thread blockedThread = new Thread(this::synchronizedMethod);

        // When
        synchronizedThread.start();
        blockedThread.start();

        // Then
        Thread.sleep(10);
        assertThat(blockedThread.getState()).isEqualTo(Thread.State.BLOCKED);

    }

    @Test
    void shouldReturnWaitingState() throws InterruptedException {
        // Given
        final Thread waitingThread = new Thread(this::synchronizedMethod);
        final Thread wrapperThread = new Thread(() -> {
            waitingThread.start();
            try {
                Thread.currentThread().join();
            } catch(InterruptedException e) {
                e.printStackTrace();
            }
        });

        // When
        wrapperThread.start();

        // Then
        Thread.sleep(10);
        assertThat(wrapperThread.getState()).isEqualTo(Thread.State.WAITING);
    }

    @Test
    void shouldReturnTimedWaitingState() throws InterruptedException {
        // Given
        final Thread timedWaitingThread = new Thread(() -> {
            try {
                Thread.sleep(1_000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // When
        timedWaitingThread.start();

        // Then
        Thread.sleep(10);
        assertThat(timedWaitingThread.getState()).isEqualTo(Thread.State.TIMED_WAITING);

    }

    @Test
    void shouldReturnTerminatedState() throws InterruptedException {
        // Given
        final Thread terminatedThread = new Thread(()->{});

        // When
        terminatedThread.start();
        terminatedThread.join();

        // Then
        assertThat(terminatedThread.getState()).isEqualTo(Thread.State.TERMINATED);
    }

    private synchronized void synchronizedMethod() {
        try {
            Thread.sleep(30);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by CodeCouple.pl
 */
class DaemonTest {

    @Test
    void shouldCreateClientThread() {
        // Given
        Thread thread = new Thread(() -> {});
        thread.start();
        // When
        boolean daemon = thread.isDaemon();
        // Then
        assertThat(daemon).isFalse();
    }

    @Test
    void shouldCreateDaemonThreadFromDaemonThread() throws InterruptedException {
        // Given
        DaemonThread thread = new DaemonThread();
        thread.setDaemon(true);
        thread.start();
        thread.join();
        // When
        boolean daemon = thread.getDaemon().isDaemon();
        // Then
        assertThat(daemon).isTrue();
    }

    @Test
    void shouldCreateDaemonThread() {
        // Given
        Thread thread = new Thread(() -> {});
        thread.setDaemon(true);
        thread.start();
        // When
        boolean daemon = thread.isDaemon();
        // Then
        assertThat(daemon).isTrue();
    }

    @Test
    void shouldThrowIllegalThreadStateException() {
        // Given
        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        // When
        thread.start();
        // Then
        assertThrows(IllegalThreadStateException.class, () -> thread.setDaemon(true));
    }




}

class DaemonThread extends Thread {

    private Thread daemon;

    @Override
    public void run() {
        daemon = new Thread(() -> {});
    }

    Thread getDaemon() {
        return daemon;
    }
}
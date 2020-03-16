package pl.codecouple;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class RejectedExecutionHandlerTest {

	@Test
	void shouldThrowRejectedExecutionExceptionWithAbortPolicy() {
		// given
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				1,
				1,
				0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1),
				new ThreadPoolExecutor.AbortPolicy()
		);
		// when
		threadPool.submit(() -> sleep(10_000));
		threadPool.submit(() -> sleep(10_000));
		// then
		assertThatExceptionOfType(RejectedExecutionException.class)
				.isThrownBy(() -> threadPool.submit(() -> sleep(10_000)));
	}

	@Test
	void shouldNotThrowRejectedExecutionExceptionWithDiscardPolicy() {
		// given
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				1,
				1,
				0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1),
				new ThreadPoolExecutor.DiscardPolicy()
		);
		// when
		threadPool.submit(() -> sleep(10_000));
		threadPool.submit(() -> sleep(10_000));
		// then
		assertThatCode(() -> threadPool.submit(() -> sleep(10_000)))
			.doesNotThrowAnyException();
	}

	@Test
	void shouldReturnNewestElementsWithDiscardOldestPolicy() throws InterruptedException {
		// given
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				1,
				1,
				0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(2),
				new ThreadPoolExecutor.DiscardOldestPolicy()
		);
		// when
		threadPool.execute(() -> sleep(100));

		BlockingQueue<String> queue = new LinkedBlockingDeque<>();
		threadPool.execute(() -> queue.offer("Oldest"));
		threadPool.execute(() -> queue.offer("Job"));
		threadPool.execute(() -> queue.offer("Newest"));

		threadPool.awaitTermination(100, TimeUnit.MILLISECONDS);

		List<String> results = new ArrayList<>();
		queue.drainTo(results);

		// then
		assertThat(results).containsExactlyInAnyOrder("Job", "Newest")
				.doesNotContain("Oldest");
	}

	@Test
	void shouldSaveRejectedTasksWithCustomPolicy() {
		// given
		RejectedTasksRepository repository = Mockito.mock(RejectedTasksRepository.class);
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				1,
				1,
				0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1),
				new CustomRejectedExecutionHandler(repository)
		);
		// when
		threadPool.submit(() -> sleep(10_000));
		threadPool.submit(() -> sleep(10_000));
		threadPool.submit(() -> sleep(10_000));

		// then
		verify(repository).save(anyString());
	}

	@Test
	void shouldBlockCallerThread() {
		final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
				1,
				1,
				0, TimeUnit.MILLISECONDS,
				new LinkedBlockingQueue<>(1),
				new ThreadPoolExecutor.CallerRunsPolicy());

		threadPool.submit(() -> sleep(1_000));
		threadPool.submit(() -> sleep(1_000));

		long startTime = System.currentTimeMillis();
		threadPool.submit(() -> sleep(1_000));

		long blockedDuration = System.currentTimeMillis() - startTime;

		assertThat(blockedDuration).isGreaterThanOrEqualTo(1_000);
	}

	void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch(InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class CustomRejectedExecutionHandler implements RejectedExecutionHandler {

	private final RejectedTasksRepository rejectedTasksRepository;

	CustomRejectedExecutionHandler(final RejectedTasksRepository rejectedTasksRepository) {
		this.rejectedTasksRepository = rejectedTasksRepository;
	}

	@Override
	public void rejectedExecution(final Runnable r, final ThreadPoolExecutor executor) {
		rejectedTasksRepository.save(r.toString());
		Executors.newFixedThreadPool(10);
	}
}

interface RejectedTasksRepository {

	void save(final String taskName);

}

package Thread.thread_5;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample_3 {

	public static void main(String[] args) {
		// 1. 커스텀한 ThreadPoolExecutor 생성
		ThreadPoolExecutor customExecutor = new ThreadPoolExecutor(
			2,	//corePoolSize = 2 
			4, 	// maximumPoolSize = 4
			30, // 추가 스레드[최대 스레드 - 코어 스레드]가 사용되지 않을 때 제거되기까지의 시간(30초)
			TimeUnit.SECONDS, // 시간 단위(초)
			new LinkedBlockingQueue<Runnable>(10), // 작업 큐 크기 = 10 
			new ThreadPoolExecutor.CallerRunsPolicy() // 큐가 꽉 차면 호출한 스레드에서 작업을 실행
		);
		
		for(int i = 1; i <= 15; i++) {
			int taskId = i;
			customExecutor.execute(()-> {
				// ThreadPool에서 순차적으로 작업 큐에 있는 Thread 10개 실행될 내용
				System.out.println("Executing Task" + taskId + " on " + Thread.currentThread().getName());
				
				try {
					Thread.sleep(2000);	// 작업 시뮬레이션
				} catch(InterruptedException e) {
					Thread.currentThread().interrupt();
				}
				
				System.out.println("Completed Task " + taskId + " on " + Thread.currentThread().getName());
			});
		}
		
		try {
			// awaitTermination : 스레드 풀이 모드 종료될때까지 대기한다.
			if(!customExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
				customExecutor.shutdownNow(); // 만약 스레드 풀이 종료되지 않았다면 강제 종료
			}
		} catch (InterruptedException e) {
			customExecutor.shutdownNow();
		}
		
		
		// 2. CompletableFuture 예제
		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
			System.out.println("Async task started on " + Thread.currentThread().getName());
			
			try {
				Thread.sleep(3000); // 비동기 작업
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			System.out.println("Async task completed on " + Thread.currentThread().getName());
		});
		
		future.thenRun(() -> System.out.println("Follow-up task on" + Thread.currentThread().getName()));
		
		try {
			future.get();	// 결과 대기
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		
		// 3. Parallel Stream을 이용한 병렬 작업
		// 
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.parallelStream().forEach(number -> {
			System.out.println("Processing number " + number + " on " + Thread.currentThread().getName());
			
			try {
				Thread.sleep(1000);		// 작업 시뮬레이션
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
		
		// 4. ForkJoinPool 예제
		// ForkJoinPool을 사용하여 병렬 계산 작업을 효율적으로 처리.
		// RecursiveTask를 구현하여 재귀적으로 작업을 분할(fork)하고 병합(join).
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);	// 병렬 레벨 4로 설정
		ForkJoinTask<Integer> sumTask = forkJoinPool.submit(new RecursiveSumTask(1, 100));
		
		try {
			System.out.println("ForkJoinPool Result : " + sumTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			forkJoinPool.shutdown();
		}
	}

}

// Fork/Join Framework를 활용한 RecursiveTask 구현
// RecursiveSumTask :
// ㅇ 작은 범위의 합계는 직접 계산.
// ㅇ 큰 범위는 작업을 분할하여 병렬로 처리.
class RecursiveSumTask extends RecursiveTask<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int start;
	private final int end;
	
	public RecursiveSumTask(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		if(end - start <= 10) {
			return calculateSum();
		}else { // 큰 작업은 분할
			int mid = (start + end) / 2;
			RecursiveSumTask leftTask = new RecursiveSumTask(start, mid);
			RecursiveSumTask rightTask = new RecursiveSumTask(mid + 1, end);
			
			leftTask.fork();	// 왼쪽 작업 비동기 실행
			
			int rightResult = rightTask.compute(); // 오른쪽 작업 동기 실행
			int leftResult = leftTask.join();	// 왼쪽 작업 결과 대기
			
			return leftResult + rightResult;
		}
	}

	private Integer calculateSum() {
		
		int sum = 0;
		for(int i = start; i <= end; i++) {
			sum += 1;
		}
		return sum;
	}
	
}
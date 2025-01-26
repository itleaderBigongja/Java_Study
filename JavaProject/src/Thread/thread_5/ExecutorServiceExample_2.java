package Thread.thread_5;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample_2 {

	public static void main(String[] args) {
		
		// 1. Custom ThreadPoolExcutor 생성
		ThreadPoolExecutor customExecutor = new ThreadPoolExecutor(
				2, // corePoolSize = 2 ( 기본적으로 유지되는 스레드 수(2)
				4, // maximumPoolSize = 4 ( 최대 스레드 생성 수(4) 
				30,// 추가 스레드[최대 스레드 - 코어 스레드]가 사용되지 않을 때 제거되기까지의 시간(30초)
				TimeUnit.SECONDS // 시간 단위(초)
				, new LinkedBlockingQueue<Runnable>(10) // 작업 큐 크기 = 10
				, new ThreadPoolExecutor.CallerRunsPolicy() // CallerRunsPolicy 적용
				// CallerRunsPolicy : 큐가 꽉 차면 호출한 스레드에서 작업을 실행
		);
		
		for(int i = 1; i <= 15; i++) {
			int taskId = i;
			customExecutor.execute(() -> {
				System.out.println("Executing Task " + taskId 
								+ " on " + Thread.currentThread().getName());
				try {
					Thread.sleep(2000); // 작업 시뮬레이션
				} catch (Exception e) {
					Thread.currentThread().interrupt();
				}
				System.out.println("Completed Task " + taskId + " on " + Thread.currentThread().getName());
			});
		}
		
		customExecutor.shutdown();
		
		try {
			// awaitTermination : 주어진 시간(60초) 내에 스레드 풀이 종료되지 않으면 shutdownNow()로 강제 종료.
			if(!customExecutor.awaitTermination(60, TimeUnit.SECONDS)) {
				customExecutor.shutdownNow(); // 강제종료
			}
		} catch (InterruptedException e) {
			customExecutor.shutdownNow();
		}
		
		// 3. Parallel Stream을 이용한 병렬 작업
		// ㅇ 리스트를 병렬로 처리하여 각 요소에 대해 동시 작업을 수행합니다.
		// ㅇ 실행 원리 : 내부적으로 Fork/Join Framework를 사용해 작업을 병렬로 나눕니다.
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.parallelStream().forEach(number -> {
			System.out.println("Processing number" + number + "on " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);	// 작업 시뮬레이션
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}
}

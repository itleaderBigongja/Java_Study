package Thread.thread_6.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample_Exceptionally {

	public static void main(String[] args) {
		
		System.out.println("스레드 예외 처리 및 풀백 시작...");
		
		/** 개념:* 성공적인 결과를 반환하는 비동기 작업을 정의 */
		CompletableFuture<String> successFuture = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(1); // 1초 동안 스레드를 잠재웁니다.( 작업 시뮬레이션 )
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
			
			return "Data from Successful Source";
		});
		
		/** 개념:* 성공적인 비동기 작업 결과를 소비하고 작업을 완료 합니다. */
		try {
			// thenAccept()는 결과를 소비하고 결과를 반환하지 않습니다.
			successFuture.thenAccept(System.out::println).get();
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		
		/** 개념:* 예외를 발생시키는 비동기 작업을 정의 */
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			// 예외 발생
			throw new RuntimeException("Failed to fetch data");
		}).exceptionally(throwable -> {
				System.out.println("예외 발생: " + throwable.getMessage());
				// 풀백 값을 제공
				return "Fallback Data";
		}).thenApply(String::valueOf);
		
		/** 개념:* 예외 처리된 결과를 처리하고 작업을 완료합니다.*/
		try {
			future.thenAccept(System.out::println).get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		// 프로그램 종료
		System.out.println("프로그램을 종료합니다.");
	}
}

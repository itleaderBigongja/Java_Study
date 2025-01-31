package Thread.thread_6_Intermediate.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** 작업 완료 상태 확인: isCompleted() == isDone()
 *  메서드는 CompletableFuture 객체가 나타내는 비동기 작업이 완료되었는지 여부를 확인
 *  여기서 "완료"라는 것은 작업이 성공적인 종료되었거나, 예외가 발생하여 실패했거나, 명시적 취소되었음을 모두 포함
 *  
 *  isCompletedExceptionally()
 *  이 메서드는 비동기 작업이 예외로 인해 실패했는지 여부를 확인
 *  
 *  isCancelled()
 *  이 메서드는 비동기 작업이 취소되었는지 여부를 확인
 *  */ 
public class CompletableFutureExample_isCompleted {

	public static void main(String[] args) {
		
		System.out.println("isCompleted() 예제 시작!...");
		
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			
			try {
				// 2초 동안 작업 시뮬레이션
				TimeUnit.SECONDS.sleep(2);
				return "작업 완료";
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
		
		// 작업 완료 후, 여부
		System.out.println("[ 작업 시작 후, 완료 여부 ]:" + future.isDone());
		
		CompletableFuture<String> failedFuture = CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("Error Occured");
		}).exceptionally(throwable -> "Fallback").thenApply(String::valueOf);
		System.out.println("[ 예외 발생 후 완료 여부 ]:" + failedFuture.isDone());
		// 작업 완료 대기
		failedFuture.join();
		
		CompletableFuture<String> canceledFuture = new CompletableFuture<>();
		canceledFuture.cancel(true);
		System.out.println("[ 취소 완료 여부 ]:" + canceledFuture.isDone());
		
		System.out.println("isDone() 예제 종료!");
	}
}

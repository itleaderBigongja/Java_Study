package Thread.thread_6_Intermediate.CompletableFuture;

import java.util.concurrent.CompletableFuture;

/** 콜백 함수를 이용하여 결과 처리(WhenComplete)*/ 
public class CompletableFutureExample_WhenComplete {

	public static void main(String[] args) {
		System.out.println("WhenComplete 예제 시작...");
		
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			
			// 성공적인 작업 시뮬레이션
			return "Data From Source 1";
		});
		
		/** whenComplete( BiConsume<T, Throwable> action )
		 *  이 메서드는 작업 완료 시, 성공 또는 실패 여부와 관계없이 콜백 함수를 실행
		 *  BiConsumer는 작업 결과(result)와 예외(throwable)를 인자로 받으며, 예외가 없을 경우 throwable은 null
		 * */
		future1.whenComplete((result, throwable) -> {
			if(throwable == null) {
				System.out.println("작업 성공, 결과 : " + result);
			}else {
				System.out.println("작업 실패, 결과 : " + throwable.getMessage());
			}
		});
		
		future1.is
		
		
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("Error occurred!");
		});
		
		future2.whenComplete((result, throwable) -> {
			if(throwable == null) {
				System.out.println("작업 성공, 결과 : " + result);
			}else {
				System.out.println("작업 실패, 결과 : " + throwable.getMessage());
			}
		});
		
		future2.exceptionally(throwable -> "Fallback Result");
		
		System.out.println("whenCompltable 예제 종료!");
	}
}

package Thread.thread_6_Intermediate.CompletableFuture;

import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample_AnyOf {

	public static void main(String[] args) {
		
		System.out.println("여러 작업 하나라도 완료되면 처리(AnyOf) 시작...");
		
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				System.out.println("future1 작업 완료");
				return "Result from future1";
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
		
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				System.out.println("future2 작업 완료");
				return "Result from future2";
			} catch (InterruptedException e2) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e2);
			}
		});
		
		CompletableFuture<String> future3 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				return "Result from future3";
			} catch (InterruptedException e3) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e3);
			}
		});
		
		CompletableFuture<Object> anyOfFuture = CompletableFuture.anyOf(future1, future2, future3);
		try {
			String result = (String) anyOfFuture.get();
			System.out.println("제일 먼저 완료된 작업 결과 : " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("anyOf 예제 종료");
		
	}
}

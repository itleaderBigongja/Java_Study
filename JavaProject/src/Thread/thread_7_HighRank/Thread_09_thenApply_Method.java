package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Thread_09_thenApply_Method {

	public static void main(String[] args) {
		// CompletableFuture: 3초 "Hellp" 반환
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "Hello";
		});
		
		// thenApply: 결과 대문자로 변환
		CompletableFuture<String> upperCaseFuture = future.thenApply(s -> s.toUpperCase());
		
		// 결과 출력
		System.out.println("Original Future isDone: " + future.isDone());
		System.out.println("Transformed Future isDone: " + upperCaseFuture.isDone());
		
		String result = "";
		
		try {
			result = upperCaseFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace(); // "HELLO" (3초 후, 출력)
		}
		
		System.out.println("Result: " + result);
	}
}
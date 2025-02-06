package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Thread_10_thenCombine_Method {
	
	public static void main(String[] args) {
		
		// 첫 번째: CompletableFuture: 3초 후, "Hello" 반환
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			
			return "Hello";
		});
		
		// 두 번째: CompletableFuture: 2초 후 "World" 반환
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			
			try{
				Thread.sleep(2000);
			}catch(InterruptedException e){
				Thread.currentThread().interrupt();
			}
			return "World";
		});
		
		// thenCombine: 두 결과를 결합하여 "Hello World" 생성
		CompletableFuture<String> combinedFuture = future1.thenCombine(future2, (s1, s2) -> s1 + " " + s2);
		
		// 결과 출력
		try {
			String result = combinedFuture.get();
			System.out.println("결과 값 : " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

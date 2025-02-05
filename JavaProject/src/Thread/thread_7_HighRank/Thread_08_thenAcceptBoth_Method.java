package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** thenAcceptBoth 메서드
 *  thenAcceptBoth 메서드는 CompletableFuture 클래스에서 제공하는 비동기 처리 메시드 중 하나이다.
 *  이 메서드는 두 개의 CompletableFuture가 모두 완료되면, 각각의 결과를 받아서 특정 작업을 실행하는 데 사용된다.
 *  두 작업의 결과를 함께 사용해야 하지만, 새로운 결과를 반환하지 않을 경우에 적합하다.
 *  
 *  기능
 *  < 두 작업 완료 대기 >
 *  ㅇ thenAcceptBoth 메서드는 두 개의 CompletableFuture 작업이 모두 성공적으로 완료될 때까지 기다린다.
 *  
 *  < 결과 소비 >
 *  ㅇ 두 작업이 모두 완료되면, 각각의 결과를 받아서 BiConsumer를 통해 특정 작업을 실행한다.
 *  
 *  < 결과 반환 없음 >
 *  ㅇ thenAcceptBoth 메서드는 새로운 CompletableFuture<Void>를 반환한다.
 *    결과 소비하지만, 새로운 결과를 생성하지 않고 단순히 작업을 수행하는데 목적이 있다.
 *    
 *  < 비동기 처리 >
 *  ㅇ thenAcceptBoth에 제공된 BiConsumer는 비동기적으로 실행된다. 즉, thenAcceptBoth 메서드를 호출한 즉시 콜백이 실행되는 것이 아니라
 *    두 CompletableFuture가 완료된 후 별도의 스레드에서 실행된다.
 *    
 *  특징
 *  < 두 결과 소비 >
 *  ㅇ 두 개의 CompletableFuture 결과를 모두 받아서 소비한다.
 *  
 *  < BiConsumer 사용 >
 *  ㅇ 결과를 처리하기 위해 java.util.function.BiConsumer 인터페이스를 사용한다.
 *     BiConsumer는 두 개의 입력 값을 받아 들이고 작업을 수행하지만, 값을 반환하지 않는 함수형 인터페이스이다.
 *     
 *  < 비동기 동작 >
 *  ㅇ 이전 작업 완료 후 콜백이 실행되므로 비동기적으로 동작한다.
 *  
 *  < 예외 처리 >
 *  ㅇ 두 CompletableFuture 중 하나라도 예외로 완료되면, thenAcceptBoth 메서드도 예외로 완료되며, 콜백은 실행되지 않는다.  
 **/
public class Thread_08_thenAcceptBoth_Method {
	
	public static void main(String[] args) {
		
		// 첫 번째 : CompletableFuture: 5초 후, "Hello" 반환
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "Hello";
		});
		
		// 두 번째 : CompletableFuture: 3초 후, "World" 반환
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			return "World";
		});
		
		// 두 CompletableFuture가 모두 완료되면 결과를 받아 출력
		CompletableFuture<Void> combinedFuture = future1.thenAcceptBoth(future2, (s1, s2) -> {
			
			System.out.println("Future1 result: " + s1);
			System.out.println("Future2 result: " + s2);
			System.out.println("Combined result: " + s1 + " " + s2);
		});
		
		// combinedFuture가 완료될 때까지 대기( 선택 사항 )
		try {
			combinedFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("Done");
	}
}
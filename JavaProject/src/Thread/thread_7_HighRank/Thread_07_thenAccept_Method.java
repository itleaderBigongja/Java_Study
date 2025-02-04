package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/** thenAccept 메서드란? 
 *  thenAccept 메서드는 CompletableFuture 클래스에서 제공하는 비동기 처리 메서드 중 하나이다.
 *  이 메서드는 이전 CompletableFuture가 완료되면, 그 결과를 받아서 특정 작업을 실행하는 데 사용된다.
 *  이전 작업의 결과를 소비(Cancle)하고 다른 결과를 반환하지 않는 경우에 적합하다.
 *  
 *  기능 
 *  < 이전 작업 결과 소비 >
 *  -> thenAccept 메서드는 이전 CompletableFuture 작업이 성공적으로 완료되면, 해당 작업의 결과를 받아서( Consumer ) 특정 작업을 실행한다.
 *  
 *  < 결과 반환 없음 >
 *  -> thenAccept 메서드는 새로운 CompletableFuture<Void>를 반환한다. 이전 작업의 결과를 소비하지만, 새로운 결과를 생성하지 않고,
 *     단순히 작업을 수행하는 데 목적이 있다.
 *     
 *  < 비동기 처리 >
 *  -> thenAccept에 제공된 Consumer는 비동기적으로 실행된다. 즉, thenAccept 메서드를 호출한 즉시, 콜백이 실행되는것이 아니라.
 *     이전 CompletableFuture가 완료된 별도의 스레드에서 실행된다.
 *  
 *  thenAccept 메서드의 활용 상황
 *  < 결과 출력 >
 *  -> 비동기 작업 결과를 받아서 콘솔에 출력하거나, 로그 기록하는 등의 작업에 사용
 *  
 *  < 결과 저장 > 
 *  -> 비동기 작업의 결과를 받아서 변수에 저장하거나 데이터베이스에 저장하는 작업에 사용할 수 있다.
 *  
 *  < 사이드 이펙트 작업 >
 *  -> 결과를 변환하지 않고 특정 작업을 수행해야 할 때, 사용한다. ( 예: 알림 발송, 리소스 해제 등 )
 *  */
public class Thread_07_thenAccept_Method {
	
	public static void main(String[] args) {
		
		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// 비동기 작업 생성
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(1); // 1초 대기
				System.out.println("비동기 작업 완료!");
				return "비동기 작업 결과";
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, executor);
		
		// 결과를 소비하는 Consumer 생성
		Consumer<String> resultConsumer = result -> {
			System.out.println("thenAccept에서 결과 처리 : " + result);
		};
		
		// thenAccept를 사용하여 결과 처리
		CompletableFuture<Void> acceptFuture = future.thenAccept(resultConsumer);
		
		
		try {
			acceptFuture.join(); // thenAccept 작업 완료 대기
			System.out.println("모든 작업 완료!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			executor.shutdown();
		}
	}
}

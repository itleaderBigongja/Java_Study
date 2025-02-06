package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/** thenRun() 메서드
 *  CompletableFuture의 thenRun() 메서드는 이전 CompletableFuture 작업이 완료된 후,
 *  특정 코드를 실행하는 데 사용된다. thenRun()은 이전 작업의 결과에는 관심이 없고,
 *  단순히 작업 완료 후에 어떤 동작을 수행하고 싶을 때 유용하다. 
 *  이전 작업의 결과를 받지 않고, 반환 값도 없는 Runnable 인터페이스를 사용한다. 
 *  
 *  기본적인 동작 원리
 *  ㅇ thenRun(Runnable action)은 Runnable action을 인자로 받는다.
 *  ㅇ 현재 CompletableFuture가 정상적으로 완료되면, action이 실행된다.
 *  ㅇ action은 이전 작업의 결과를 받지 않는다.
 *  ㅇ action은 값을 반환하지 않는다.(Void형)
 *  ㅇ thenRun()은 CompletableFuture<Void>를 반환한다.
 *  
 *  파라미터 설명
 *  ㅇ action: Runnable 함수형 인터페이스를 구현한 람다 표현식 또는 메서드 참조이다.
 *             이 함수는 인자를 받지 않고, void를 반환한다.
 **/
public class Thread_12_thenRun_Method {

	public static void main(String[] args) {
		// CompletableFuture: 2초 후, "Task Completed" 반환
		CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			return "Task Completed";
		});
		
		try {
			System.out.println("future의 결과 : " + future.get());
		} catch (InterruptedException | ExecutionException e1) {
			e1.printStackTrace();
		}
		
		// thenRun: 작업 완료 메시지 출력
		CompletableFuture<Void> result = future.thenRun(() -> {
			System.out.println("Privious task finished!");
		});
		
		// result.get()은 result가 완료될때까지 현재 스레드를 블로킹한다.
		// void형이 아닌 경우, 반환값이 있지만, void형이면 반환값을 반환하지 않는다.
		try {
			result.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println("Done");
	}
	
}

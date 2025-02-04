package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/** runAfterBoth 메서드란? 
 *  CompletableFuture 클래스에서 제공하는 비동기 처리 메서드 중 하나이다.
 *  이 메서드는 두 개의 CompletableFuture가 모두 완료된 후에 특정 작업을 실해 하는데 사용된다.
 *  결과 값을 필요로 하지 않고, 단순히 두 작업이 완료된 후 특정 코드를 실행해야 할 때 유용하다.
 *  
 *  기능
 *  ㅇ 두 작업 완료 대기 : runAfterBoth()는 두 개의 CompletableFuture가 모두 정상적으로 완료될 때까지 기다린다.
 *  
 *  ㅇ 작업 실행 : 두 작업이 모두 완료되면, runAfterBoth에 제공된 Runnable 객체의 run() 메서드를 실행합니다. 이 Runnable은
 *              결과를 소비하지 않으며 단순히 특정 작업을 실행한다.
 *              
 *  ㅇ 결과 반환 : runAfterBoth는 새로운 CompletableFuture<Void>를 반환한다.
 *              이 CompletableFuture에서 제공된 Runnable이 실행된 시점을 나타내며, 결과 값은 없다.(Void 타입)
 **/
public class Thread_06_runAfterBoth_Method {
	
	public static void main(String[] args) {
		
		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// 두 개의 비동기 작업 생성
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("작업 1 완료");
				return "작업_1 결과";
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}, executor); // executor를 통해서 CompletableFuture 비동기 스레드를 호출한다.
		
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("작업 2 완료!");
				return "작업_2 결과";
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}, executor);
		
		// 두 작업이 모두 완료된 후, 실행할 Runnable 생성
		Runnable runnable = () -> {
			System.out.println("두 작업 모두 완료됨! 후처리 작업 실행!");
		};
		
		// runAfterBoth를 사용하여 두 작업 완료 후, Runnable 실행(future1 실행 후, future2 실행, 그 다음 후처리 작업)
		CompletableFuture<Void> combinedFuture = future1.runAfterBoth(future2, runnable); 
		
		// 결과 출력
		try {
			// combinedFuture의 모든 작업이 완료될 때까지 기다린다.
			combinedFuture.join();
			System.out.println("모든 작업이 완료되었습니다,");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}

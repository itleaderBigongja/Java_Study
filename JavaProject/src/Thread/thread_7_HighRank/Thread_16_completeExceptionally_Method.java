package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** completeExceptionally 메서드
 *  CompletableFuture의 completeExceptionally(Throwable ex) 메서드는 CompletableFuture를 지정된
 *  예외로 완료하는 데 사용됩니다. 이 메서드를 호출하면, CompletableFuture는 더 이상 정상적인 값을 생성하지 않고,
 *  주어진 예외를 발생시킨 상태로 전환됩니다. 주로 비동기 작업 중에 예기치 않은 오류가 발생했을 때, 해당 오류를
 *  CompletableFuture에 알리는 데 사용됩니다. 
 *  
 *  < 기본적인 동작원리 >
 *  ㅇ completeExceptionally(Throwable ex)는 Throwable ex 객체, 즉 예외 객체를 인자로 받습니다.
 *  ㅇ 현재 CompletableFuture의 상태를 예외 ex를 발생시킨 상태로 변경합니다.
 *  ㅇ 이 메서드를 호출한 후에는, get() 메서드를 호출하면 ExcutionException이 발생합니다.
 *  ㅇ 이 메서드는 true(성공적으로 예외를 설정) 또는 false (이미 완료된 CompletableFutur에 예외를 설정하려고 시도)
 *     를 반환합니다. 
 *  ㅇ completeExceptionally()는 해당 CompletableFuture에 연결된 다른 모든 종속적인 ComkpletableFuture
 *    들에게도 예외르 전파합니다.
 *  
 *  < 파라미터 설명 >
 *  ㅇ ex: CompletableFuture가 완료될 때 발생시킬 예외 객체입니다. Throwble의 하위 클래스
 *    인스턴스여야 합니다.(예: Exception, RuntimeException, IOException 등)
 *    
 *  < completeExceptionally()의 장점:
 *  ㅇ 예외 전파: 비동기 작업 중에 발생한 예외를 CompletableFuture 파이프라인 전체에 전파할 수 있다.
 *  ㅇ 오류 처리: 비동기 작업의 오류를 효과적으로 처리하고, 사용자에게 적절한 피드백을 제공할 수 있다.
 *  ㅇ 유연성: 다양한 예외 상황을 시뮬레이션하고, 예외 처리 로직을 테스트할 수 있다.
 **/
public class Thread_16_completeExceptionally_Method {

	public static void main(String[] args) {
		
		CompletableFuture<String> future = new CompletableFuture<String>();
		
		// 예외 발생 시뮬레이션
		new Thread(() -> {
			
			try {
				Thread.sleep(1000); // 1초 후 예외 발생
				future.completeExceptionally(new RuntimeException("Something went wrong!"));
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}).start();
		
		// 결과 확인 (예외 발생)
		try {
			future.get();
		} catch (ExecutionException e) {
			// Something went wrong! 출력
			System.err.println("ExecutionException: " + e.getCause().getMessage());
		} catch (InterruptedException e) {
			System.err.println("InterruptedException: " + e.getCause().getMessage());
			Thread.currentThread().interrupt();
		}
	}
}
package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** failedFuture 메서드
 *  CompletableFuture.failedFuture(Throwable ex) 메서드는 지정된 예외를 던지며,
 *  즉시 완료되는 CompletableFuture 인스턴스를 생성하는 데 사용된다.
 *  비동기 작업 중 발생한 예외를 시뮬레이션하거나, 예외 처리 로직을 테스트하는 데 적합하다.
 *  
 *  < 기본적인 동작원리 >
 *  ㅇ CompletableFuture.failedFuture(Throwable ex)는 Throwable ex 객체, 즉 예외 객체를 인자로 받는다.
 *  ㅇ 주어진 예외 ex를 발생시키며 완료되는 CompletableFuture를 즉시 생성하여 반환한다.
 *  ㅇ 반환된 CompletableFuture는 이미 완료된 상태이므로, get() 메서드를 호출하면 즉시
 *     ExecutionException을 던진다. ExecutionException은 InterruptedException과 함께
 *     checked exception이므로 try-catch 블록으로 감싸주어야 한다.
 *  ㅇ 이 CompletableFuture는 별도의 스레드에서 작업을 수행하지 않는다. 단순히 예외를 감싸는 역할만 한다.
 *  
 *  < 파라미터 설명 >
 *  ㅇ ex: CompletableFuture가 완료될 때 발생시킬 예외 객체이다.
 *        Throwable의 하위 클래스 인스턴스여야 한다.(예: Exception, RuntimeException, IOException 등).
 *        
 *  < failedFuture의 장점 >
 *  ㅇ 즉시 실패: CompletableFuture를 즉시 실패한 상태로 생성할 수 있다.
 *  ㅇ 테스트 용이성: 테스트 코드에서 특정 예외를 던지는 CompletableFuture를 쉽게 생성하여 예외 처리 로직을 테스트할 수 있다.
 *  ㅇ 예외 시뮬레이션: 비동기 작업에서 발생할 수 있는 예외 상황을 시뮬레이션할 수 있다.
 * */
public class Thread_15_failedFuture_Method {

	public static void main(String[] args) {
		// RuntimeException을 발생시키는 failedFuture 생성
		CompletableFuture<String> failedFuture 
			= CompletableFuture.failedFuture(new RuntimeException("Something went wrong!"));
		
		// 결과 확인
		try {
			failedFuture.get();
		} catch (ExecutionException e) {
			System.err.println("ExecutionException: " + e.getCause().getMessage());
		} catch (InterruptedException e) {
			System.err.println("InterruptedException: " + e.getCause().getMessage());
			Thread.currentThread().interrupt();
		}
	}
}

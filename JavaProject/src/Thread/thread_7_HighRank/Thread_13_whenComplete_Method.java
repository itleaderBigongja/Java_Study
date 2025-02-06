package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** whenComplete() 메서드
 *  CompletableFuture 작업이 완료되었을 때, 정상적으로 완료되었든
 *  예외가 발생했든 관계없이 항상 실행되는 콜백을 등록하는데 사용한다.
 *  whenComplete()는 결과를 확인하거나 예외를 처리하고 싶지만, 
 *  결과 값을 변경하거나 예외를 다시 던지고 싶지 않을 때 유용하다.
 *  
 *  < 기본적인 동작원리 >
 *  ㅇ whenComplete(BiConsumer action)는 BiConsumer action을 인자로 받는다.
 *  ㅇ 현재 CompletableFuture가 완료되면( 정상 또는 예외 발생 ), action이 실행된다.
 *  ㅇ action은 두 개의 인자를 받습니다.
 *    -> 결과: 작업이 정상적으로 완료된 경우, 결과 값이 전달된다. 그렇지 않은 경우 null이 전달된다.
 *    -> 예외: 작업이 예외를 던진 경우, 예외 객체가 전달된다. 그렇지 않은 경우 null이 전달된다.
 *  ㅇ action은 값을 반환하지 않는다.(Void 형)
 *  ㅇ whenComplete()는 원래 CompletableFuture와 동일한 결과를 가진 새로운 CompletableFuture를 반환한다.
 *    즉, whenComplete()는 결과 값이나 예외를 변경하지 않는다.
 *    
 *  < whenComplete의 장점 >
 *  ㅇ 항상 실행 : 작업의 성공 여부에 관계없이 항상 실행된다.
 *  ㅇ 결과 불변 : 원래 CompletableFuture의 결과나 예외를 변경하지 않는다.
 *  ㅇ 비동기 처리 : 이전 작업이 완료될 때까지 블로킹하지 않고, 비동기적으로 처리한다.
 *  ㅇ 코드 가독성: 복잡한 콜백 체인을 간결하고 표현할 수 있다.
 * */
public class Thread_13_whenComplete_Method {
	
	public static void main(String[] args) {
		
		// 무조건 성공하는 문자열 반환 CompletableFuture
		CompletableFuture<String> successFuture = CompletableFuture.supplyAsync(() -> "Result");
		
		// WhenCompletable: 결과 및 예외 로깅
		CompletableFuture<String> completableSuccessFuture 
				= successFuture.whenComplete((result, exception) -> {
					if(exception == null || "".equals(exception.toString())) {
						System.out.println("Success: " + result);
					} else {
						System.out.println("Exception: " + exception.getMessage());
					}
		});
		
		// 무조건 예외를 던지는 CompletableFuture
		CompletableFuture<String> exceptionFuture = CompletableFuture.supplyAsync(() -> {
			throw new RuntimeException("Something went wrong!");
		});
		
		// whenCompletable: 결과 및 예외 로깅
		CompletableFuture<String> completeExceptionFuture 
				= exceptionFuture.whenComplete((result, exception) -> {
				if(exception == null || "".equals(exception.toString())) {
					System.out.println("Success: " + result);
				}else {
					System.out.println("Exception: " + exception);
				}
		});
		
		// 결과 대기
		try {
			completeExceptionFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			System.err.println("Caught Exception from completedExceptionFuture: " + e.getMessage());
		}
		
		System.out.println("Done");
	}
}

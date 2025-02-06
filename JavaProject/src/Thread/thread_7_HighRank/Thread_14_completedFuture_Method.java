package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** completedFuture 메서드
 *  CompletableFuture.completedFuture() 메서드는 주어진 값을 즉시, 
 *  완료되는 CompletableFuture 인스턴스를 생성하는 데 사용된다. 
 *  이미 가지고 있는 결과를 CompletableFuture로 감싸서 비동기 파이프라인에 통합하거나 테스트 목적으로 사용하기에 적합하다.
 *  
 *  < 기본적인 동작원리 >
 *  ㅇ CompletableFuture.completedFuture(T value)는 값 value를 인자로 받는다.
 *  ㅇ 주어진 value를 결과 값으로 가지는 CompletableFuture를 즉시 생성하여 반환한다.
 *  ㅇ 반환된 CompletableFuture는 이미 완료된 상태이므로, get() 메서드를 호출하면 즉시 value를 반환한다.
 *  ㅇ 이 CompletableFuture는 별도의 스레드에서 작업을 수행하지 않는다. 단순히 주어진 값을 감싸는 역할만 한다.
 *  
 *  < 파라미터 설명 >
 *  ㅇ value: CompletableFuture가 완료될 때 반환할 값이다.
 *           어떤 타입의 객체든 가능하다.( T는 제네릭 타입 파라미터를 나타낸다. ) 
 **/
public class Thread_14_completedFuture_Method {
	
	public static void main(String[] args) {
		// "Hello" 값을 가지는 completedFuture 생성
		CompletableFuture<String> completedFuture = CompletableFuture.completedFuture("Hello");
		
		// 결과 출력
		try {
			String result = completedFuture.get();
			System.out.println("1차 결과 : " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		// completedFuture에 thenApply 적용
		CompletableFuture<String> upperCaseFuture = completedFuture.thenApply(String::toUpperCase);
		
		try {
			String upperCaseResult = upperCaseFuture.get();
			System.out.println("2차 결과 : " + upperCaseResult);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
	}
}

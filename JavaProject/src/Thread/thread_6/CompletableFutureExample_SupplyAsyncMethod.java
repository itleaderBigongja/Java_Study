package Thread.thread_6;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample_SupplyAsyncMethod {
	/** supplyAsync() 메서드 개념
	 *  람다 표현식 (또는 Supplier 인터페이스 구현체)을 비동기적으로 실행할 작업을 생성합니다. 즉, 현재 스레드와는 별도의 스레드에서 코드를 실행
	 *  
	 *  ㅇ 결과값 반환
	 *  supplyAsync()에 제공된 람다 표현식은 어떤 결과값을 반환해야 합니다. 이 반환값은 CompletableFuture 객체에 저장되어,
	 *  나중에 비동기 작업이 완료되었을 때 결과를 가져오거나 처리하는 데 사용
	 **/
	public static void main(String[] args) {
		
		System.out.println("CompletableFuture 시작.........");
		
		/** 첫 번째 : 비동기 작업 : 1초 후에 정수 값을 반환하는 CompletableFuture 생성
		 *  - CompletableFuture.supplyAsync()를 사용하여 비동기 작업을 생성
		 *  - 람다 표현식 () -> {...} 내부에 비동기적으로 실행될 코드 작성
		 **/ 
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(
				() -> {
					try {
						TimeUnit.SECONDS.sleep(1);	// 1초 동안 스레드 일시 중단(작업 시뮬레이션)
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();	// 스레드 인터럽트 처리
						throw new RuntimeException();
					}
					System.out.println("작업 1 완료 -> 결과 : 10");
					return 10;
				});
		
		/** 두 번째 : 비동기 작업 : 2초 후에 문자열을 반환하는 CompletableFuture 생성
		 *  - supplyAsync()를 사용하여 비동기 작업을 생성
		 **/
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(
				()-> {
					try {
						TimeUnit.SECONDS.sleep(2);	// 2초 동안 스레드 일시 중단( 작업 시뮬레이션 )
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();  // 스레드 인터럽트 처리
						throw new RuntimeException();
					}
					System.out.println("작업 2 완료 -> 결과 : itleaderBigongja");
					return "itleaderBigongja";
				});
		
		/** 세번째 : 두 작업의 결과를 결합하고 결과를 출력하는 CompletableFuture 생성
		 *         - thenCombine()을 사용하여 두 CompletableFuture의 결과 결합
		 *         - 람다 표현식 (num, str) -> {...} 내부에 결합 작업을 수행하는 코드 작성
		 *         - future1과 future2가 모두 완료된 후에 실행됨 
		 **/
		CompletableFuture<Void> combinedFuture = future1.thenCombine(future2, (num, str) -> {
			System.out.println("결과 결합 : " + num + " " + str);	// 결합된 결과 출력
			return null;	// thenCombine은 Void를 반환할 수 있으므로 null 반환
		});
		
		/** 네 번째 : 모든 작업이 완료될 때까지 메인 스레드 대기
		 *          - get() 메서드를 호출하여 combineFuture가 완료될 때까지 대기
		 **/
		try {
			combinedFuture.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		
		System.out.println("future_1 실패 여부 : " + future1.isCompletedExceptionally() + " 작업 완료 여부 : " + future1.isDone());
		System.out.println("future_2 실패 여부 : " + future2.isCompletedExceptionally() + " 작업 완료 여부 : " + future2.isDone());
	}
}

/** CompletableFuture 객체의 주요 특징
 *  상태 관리: CompletableFuture 비동기 작업 상태
 *  ㅇ 미완료(Incomplete) : 비동기 작업이 아직 완료되지 않은 상태
 *  ㅇ 완료(Completed)   : 비동기 작업이 성공적으로 완료된 상태
 */
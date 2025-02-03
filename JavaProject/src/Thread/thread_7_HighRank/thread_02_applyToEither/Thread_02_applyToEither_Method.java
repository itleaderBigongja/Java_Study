package Thread.thread_7_HighRank.thread_02_applyToEither;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** applyToEither 메서드
 *  정의 : CompletableFuture 클래스에서 제공하는 메서드로, 두 개의 CompletableFuture 중 먼저 완료되는
 *        CompletableFuture의 결과를 받아 변환(mapping)한 후, 새로운 CompletableFuture를 반환
 *        
 *  기능 : 두 개의 비동기 작업 중 먼저 완료되는 작업의 결과를 특정 함수를 통해 변환하고 싶을 때 사용
 *  
 *  특징 : 비동기 동작 : applyToEither 메서드 자체는 비동기적으로 동작한다.
 *        즉, 첫 번째 작업이 완료될 때까지 기다리지 않고 바로 반환
 *        
 *        결과 반환 : 두 CompletableFuture 중 먼저 완료된 작업의 결과 값을 Function에 전달하여 새로운 값으로 변환
 *                  applyToEither 메서드의 결과를 추가적인 비동기 작업에 연결할 수 있다.
 *                  
 *  applyToEither 메서드의 필요사항
 *  ㅇ 결과 변환 : 여러 비동기 작업 중 먼저 완료된 작업의 결과를 변환하여 다른 작업에 사용해야 할 때,
 *              예를 들어, 먼저 응답하는 서버의 응답 데이터를 특정 형식으로 변환해야 할 때 유용
 *              
 *  ㅇ FallBack 작업 : 메인 작업이 실패할 경우, 다른 대안 작업의 결과를 특정 방식으로 변환해야할 때
 *  
 *  ㅇ 조건부 반환 : 여러 작업 중 먼저 완료된 작업 결과에 따라 다른 변환 로직을 적용해야할 때
 *  */
public class Thread_02_applyToEither_Method {

	public static void main(String[] args) {
		CompletableFuture<Integer> future1 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(2);
				return 100;
			} catch (Exception e) {
				return -1;
			}
		});
		
		CompletableFuture<Integer> future2 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(1);
				return 200;
			} catch (Exception e) {
				return -1;
			}
		});
		
		// 먼저 완료되는 작업의 결과를 문자열로 반환
		CompletableFuture<String> result = future1.applyToEither(future2, num -> {
			return "결과 값 : " + num;
		});
		
		try {
			System.out.println(result.join());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
}

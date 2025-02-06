package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/** CompletableFuture의 thenCompose() 메서드는 비동기 작업의 결과를 기반으로
 *  또 다른 비동기 작업을 연결하여 실행하는 데 사용한다.
 *  
 *  기본적인 동작 원리
 *  ㅇ thenCompose(Function fn)는 Function fn을 인자로 받는다.
 *  ㅇ 현재 CompletableFuture가 정상적으로 완료되면, fn이 실행된다.
 *  ㅇ fn은 현재 CompletableFuture의 결과를 인자로 받아, 새로운 CompletableFuture를 반환한다.
 *  ㅇ thenCompose()는 fn이 반환한 CompletableFuture를 반환한다. 즉, 중첩된 CompletableFuture가 평탄화된다.
 *  
 *  파라미터 설명
 *  fn : Function 함수형 인터페이스를 구현한 람다 표현식 또는 메서드 참조이다.
 *  ㅇ 인자 : 현재 CompletableFuture의 결과
 *  ㅇ 반환 값 : 새로운 CompletableFuture 인스턴스
 * */
public class Thread_11_thenCompose_Method {

	public static void main(String[] args) {
		
		// 첫 번째 CompletableFuture: 2초 후, 사용자 ID 반환
		CompletableFuture<String> userIdFuture = CompletableFuture.supplyAsync(() -> {
			
			try {
				Thread.sleep(2000);
			} catch (Exception e) {
				Thread.currentThread().interrupt();
			}
			return "user123";
		});
		
		// thenCompose: 사용자 ID를 이용하여 사용자 정보 CompletableFuture 생성
		CompletableFuture<String> userInfoFuture = userIdFuture.thenCompose(userId -> {
			
			// 두 번째 CompletableFuture: 사용자 정보를 1초 후 반환
			return CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return "User Info for " + userId;
			});
		});
		
		// 결과 출력
		try {
			String result = userInfoFuture.get();
			System.out.println("Result : " + result);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

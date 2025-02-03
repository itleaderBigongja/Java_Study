package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** acceptEither 메서드
 *  정의 : CompletableFuture 클래스에서 제공하는 메서드로, 두 개의 CompletableFuture 중 먼저 완료되는
 *        CompletableFuture의 결과를 소비하는 Consumer를 실행
 *        
 *  기능 : 두 개의 비동기 작업 중 먼저 완료되는 작업의 결과에 따라 특정 동작을 수행하고 싶을 때 사용
 *  
 *  acceptEither 메서드가 필요한 상황
 *  경쟁 조건 : 여러 비동기 작업 중 먼저 결과를 얻을 수 있는 작업이 있고, 해당 결과에 따라 다음 동작을 수행해야할 때,
 *            예를 들어, 서버에서 동일한 데이터를 가져오는 경우, 가장 빠른 서버의 응답을하고 싶을 때 유용하다.
 *  
 *  Fallback 처리 : 메인 작업이 실패할 경우, 다른 대안 작업을 시도해야할 때 사용
 **/
public class Thread_01_acceptEither_Method {

	public static void main(String[] args) {
		
		CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
			
			try {
				TimeUnit.SECONDS.sleep(2);
				return "작업 1 완료!";
			} catch (Exception e) {
				return "작업 1 예외!";
			}
		});
		
		CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				return "작업 2 완료!";
			} catch (Exception e) {
				return "작업 2 예외!";
			}
		});
		
		// 먼저 완료되는 작업의 결과를 받아 출력
		CompletableFuture<Void> result = future1.acceptEither(future2, resultStr -> {
			System.out.println("먼저 완료된 작업은 : " + resultStr + " 입니다.");
		});
		
		// 메인 스레드 종료 방지, ( 비동기 작업 완료를 확인하기 위해 잠시 대기 )
		try {
			result.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

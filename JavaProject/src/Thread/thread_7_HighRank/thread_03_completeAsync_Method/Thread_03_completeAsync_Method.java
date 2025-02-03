package Thread.thread_7_HighRank.thread_03_completeAsync_Method;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/** completeAsync Method
 *  정의 : CompletableFuture 클래스에서 제공하는 메서드로, CompletableFuture의 결과를 비동기적으로 설정
 *  기능 : CompletableFuture가 아직 완료되지 않은 상태에서, 외부 비동기 작업 결과를 받아 해당
 *        CompletableFuture를 완료시키고 싶을 때 사용
 *        
 *  completeAsync() 메서드가 필요한 상황
 *  외부 비동기 작업 완료 : 외부에서 실행되는 비동기 작업( 예 : API 호출, 데이터베이스 쿼리)이 완료되었을 때
 *                     해당 결과를 CompletableFuture에 설정 해야할 때
 *                     
 *  결과 지연 처리 : 특정 조건이 만족되었을 때, CompletableFuture의 결과를 설정하고 싶을 때
 *  
 *  비동기 작업의 결합 : 여러 비동기 작업의 결과를 합쳐서 CompletableFuture를 완료해야할 때 
 *  
 * */
public class Thread_03_completeAsync_Method {

	public static void main(String[] args) {
		
		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// 비동기적으로 결과를 받을 Completable 생성
		CompletableFuture<String> future = new CompletableFuture<String>();
		
		// 외부 비동기 작업 시뮬레이션 ( 결과를 Supplier를 통해 제공 )
		Supplier<String> asyncTask = (() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("외부 비동기 작업 완료");
				return "외부 작업 결과!";
			} catch (Exception e) {
				return "외부 작업 실패!";
			}
		});
		
		// 외부 비동기 작업을 완료하면 결과를 설정하는 completeAsync() 호출
		// 결과를 공급하는 asyncTask를 executor 스레드 풀에서 비동기적으로 실행
		future.completeAsync(asyncTask, executor);
		
		// 메인 스레드 종료 방지 (비동기 작업 완료를 확인하기 위해 잠시 대기)
        try {
            System.out.println("비동기 작업 결과를 기다리는 중...");
            System.out.println("비동기 작업 결과: " + future.join()); // 결과 출력
        } catch (Exception e) {
           e.printStackTrace();
        } finally {
            executor.shutdown();
        }
	}
}

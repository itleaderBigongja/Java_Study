package Thread.thread_6_Intermediate.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/** obtrudeValue 메서드
 *  CompletableFuture 비동기 작업을 강제로 성공 상태로 만들고, 지정된값(value)을 작업의 결과 값으로 설정
 *  
 *  obtrudeException 메서드
 *  CompletableFuture 비동기 작업을 강제로 실패 상태로 만들고, 지정된 예외(ex)를 작업의 결과 예외로 설정 
 **/
public class CompletableFutureExample_obtrudeValueAndObtrudeException {
	
	public static void main(String[] args) {
		System.out.println("obtrude 예제 시작...");
		
		// 1. obtrudeValue() 예제
		CompletableFuture<String> future1 = new CompletableFuture<>();
		
		// 비동기 작업 시작( 실제 작업은 없음 )
		new Thread(() -> {
			try {
				// 3초 후, 성공 처리
				TimeUnit.SECONDS.sleep(3);
				future1.obtrudeValue("강제 성공 값");	// 외부에서 작업 성공처리
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				future1.obtrudeException(e);		// 예외 발생 시 외부에서 작업 실패 처리
			}
		}).start();
		
		System.out.println("future1 작업 시작 후, 완료 여부 : " + future1.isDone());
		System.out.println("future1 결과 : " + future1.join());
		
		// 2. obtrudeException() 예제
		CompletableFuture<String> future2 = new CompletableFuture<>();
		
		// 비동기 작업 시작 ( 실제 작업은 없음 )
		new Thread(() -> {
			
			try {
				// 3초 후, 실패 처리
				TimeUnit.SECONDS.sleep(3);	// 3초 후, 실패 처리
				future2.obtrudeException(new RuntimeException("강제 실패!"));		// 외부에서 작업 실패 처리
			} catch (InterruptedException e2) {
				Thread.currentThread().interrupt();
			}
		}).start();
		
		System.out.println("future2 작업 시작 후 완료 여부: " + future2.isDone());
		
		try {
			System.out.println("future2 작업 결과 : " + future2.get());	// 결과를 가져오려 하지만 예외 발생
		} catch (Exception e2) {
			System.out.println("future2 예외 발생 : " + e2.getMessage());	// 예외 메시지 출력
		}
		
		System.out.println("obtrude 예제 종료!");
	}
	
}

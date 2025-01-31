package Thread.thread_6_Intermediate.CountDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/** CountDownLatch란? 
 *  [개념] 
 *   -> 하나 이상의 스레드가 특정 시점까지 대기하도록 하는 데 사용되는 동기화 클래스입니다.
 *  [동작 방식]
 *   -> CountDownLatch는 초기 카운트 값을 가지고 시작
 *      여러 스레드가 작업을 완료할 때마다 countDown() 메서드를 호출하여 카운트 값을 감소 시킵니다.
 *      대기 스레드는 await() 메서드를 호출하여 카운트 값이 0이 될 때까지 대기합니다.
 *      카운트 값이 0이 되면 대기 중인 모든 스레드가 동시에 진행됩니다.
 *  */
public class Thread_CountDownLatch {
	
	public static void main(String[] args) {
		// CountDownLatch(): 여러 스레드가 특정 시점까지 대기하도록 하는 동기화 클래스
		// 사용 이유: 여러 스레드가 모두 작업을 완료하거나 특정 시점에 도달할 때까지 메인 스레드를 대기시키기 위해 사용
		// 초기 카운트 값을 3으로 설정한 CountDownLatch 객체 생성
		CountDownLatch latch = new CountDownLatch(3);
		
		// 3개의 스레드가 모두 countDown() 메서드를 호출할 때까지 대기
		System.out.println("작업 시작...");
		
		// 3개의 스레드를 생성하여 실행
		for(int i = 1; i <= 3; i++) {
			
			// Thread: 스레드를 생성하는 클래스
			// 사용 이유: 여러 작업을 병렬로 수행하기 위해 스레드를 생성
			Thread thread = new Thread(() -> {
				System.out.println("스레드 : " + Thread.currentThread().getName() + " 시작...");
				
				try {
					// 스레드를 1초간 일시 정지(작업 시뮬레이션)
					TimeUnit.SECONDS.sleep(1);  
					// 사용 이유 : 각 스레드의 작업 시간을 시뮬레이션 하기 위해 스레드를 잠시 멈춤
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException(e);
				}
				
				// CountDownLatch.countDown() : CountDownLatch의 카운트 값을 1 감소시키는 메서드
				// 사용 이유: 작업이 완료될 때마다 카운트 값을 감소시켜 모든 스레드가 완료되엇음을 알리기 위함
				// 스레드 작업 완료 후, 카운트 감소
				latch.countDown();
				System.out.println("스레드 : " + Thread.currentThread().getName() + "완료");
			});
			
			// 스레드 시작
			thread.start();
		}
		
		// CountDownLatch.await() 메서드
		// -> CountDownLatch의 카운트 값이 0이 될 때까지 현재 스레드를 대기시키는 메서드
		// 사용 이유: 모든 스레드가 완료될 때까지 메인 스레드를 대기시키기 위해 사용
		try {
			System.out.println("현재 대기중인 스레드 : " + Thread.currentThread().getName());
			latch.await();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
		
		// 모든 스레드 작업이 완료 메시지 출력
		System.out.println("모든 스레드 작업 완료!");
	}
}

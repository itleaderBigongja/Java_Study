package Thread.thread_6.Semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/** Semaphore[신호기]란? 
 *  [개념]
 *  -> java.util.concurrent 패키지에서 제공하는 동기화 도구 중 하나로,
 *     공유 자원에 접근할 수 있는 스레드의 수를 제한하는 데 사용
 *     
 *  [동작 방식]
 *  -> Semaphore 객체를 생성할 때 자원 접근 허용 개수(permits)를 설정
 *     스레드가 자원을 사용하려면 acquire() 메서드를 호출하여 허가를 얻고, 자원 사용 후 release() 메서드를 호출하여
 *     허가를 반환합니다. 만약 허가 개수가 0이면 acquire()를 호출한 스레드는 허가가 생길 때까지 대기합니다.
 *     
 *  [주요 용도]
 *  -> 리소스 풀 관리: 데이터베이스 연결, 스레드 풀 등과 같이 제한된 자원에 접근하는 스레드의 수를 제어
 *  -> 동시성 제어: 특정 코드 블록에 동시에 접근할 수 있는 스레드의 수를 제한
 *  -> 제한된 접근 권한: 특정 리소스에 접근할 수 있는 스레드의 수를 제한할 때 사용
 **/
public class Thread_Semaphore {

	// Semaphore: 공유 자원에 접근할 수 있는 스레드의 수를 제한하는 동기화 클래스
	// 사용 이유: 특정 자원에 동시에 접근할 수 있는 스레드 수를 제한하여 리소스 고갈을 방지하거나 동시성 문제를 제어하기 위해 사용
	// 최대 2개 스레드 접근 허용(permits[허가], acquire[얻다, 습득하])
	private final Semaphore semaphore = new Semaphore(2);

	// accessResource() 메서드: 공유 자원에 저근하고 작업을 수행하는 메서드
	public void accessResource() {
		// Semaphore.acquire(): 사용 권한(permits)을 획득하는 메서드
		// 사용 이유: 자원에 접근하기 전에 사용 권한을 얻기 위해 사용, 권한이 없다면 스레드는 대기
		try {
			// 허가 획득
			semaphore.acquire();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		} 
		
		try {
			System.out.println("현재 " + Thread.currentThread().getName() + "스레드가 자원 사용중");
			TimeUnit.SECONDS.sleep(1); // 자원 사용 시뮬레이션(스레드를 1초 일시 정지)
			// 사용 이유: 자원 사용에 걸리는 시간을 시뮬레이션하기 위해 스레드를 잠시 멈춤
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		} finally {
			// Semaphore.release(): 사용 권한(permits)을 반환하는 메서드
			// 사용 이유: 자원 사용 후 권한을 반환하여 다른 스레드가 자원을 사용할 수 있도록 함
			semaphore.release(); // 허가 반환
			System.out.println("현재 " + Thread.currentThread().getName() + "스레드가 자원 사용 완료");
			
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		
		// Thread_Semaphore 객체 생성
		Thread_Semaphore example = new Thread_Semaphore();
		
		for(int i = 0; i < 5; i++) {
			// Thread: 스레드를 생성하는 클래스
			// 사용 이유: 여러 스레드에서 동시에 자원에 접근하는 상황을 시뮬레이션 하기 위해 스레드를 생성
			Thread thread = new Thread(() -> {
				// 공유 자원에 접근하여 작업 수행
				example.accessResource();
			});
			thread.start();
		}
	}
}

package Thread.thread_6.CyclicBarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import jdk.internal.org.jline.terminal.TerminalBuilder.SystemOutput;

/** CyclicBarrier 메서드
 *  [개념] 
 *  여러 스레드가 특정 지점에 도달할 때까지 서로를 기다리도록 하는 동기화 도구
 *  CountDownLatch()와 유사하지만, CyclicBarrier는 재사용이 가능하다는 특징을 가지고 있다.
 *  즉, 스레드들이 장벽을 넘어선 후 다시 장벽에 도달할 때까지 기다릴 수 있습니다.
 *  
 *  [동작 방식]
 *  CyclicBarrier 객체를 생성할 때 참여할 스레드 수를 지정합니다. 스레드들은 await() 메서드를 호출하여 장벽에 도달하고,
 *  지정된 스레드 수가 모두 장벽에 도달하면 모든 스레드가 동시에 진행됩니다. 장벽이 깨진 후에는 재사용이 가능하다.
 *  
 *  [주요 용도]
 *  병렬 알고리즘 : 여러 스레드가 작업을 분할하여 계산하고, 특정 시점에서 결과를 합쳐야 하는 상황에 사용
 *  게임 개발 : 게임에서 여러 캐릭터가 특정 지점에 도달하면 동시에 다음 단계를 시작해야 할 때 사용
 *  시뮬레이션 : 여러 컴포넌트가 동시에 특정 상태에 도달해야 하는 시뮬레이션에 사용
 * */
public class Thread_CyclicBarrier {

	public static void main(String[] args) {
		
		// CyclicBarrier: 여러 스레드가 특정 시점에 도달할 때까지 대기하도록 하는 동기화 클래스
		// 사용 이유: 여러 스레드가 동시에 특정 단계를 시작해야 하는 상황에서 스레드를 동기화하기 위해 사용
		// 3개의 스레드가 모두 await()에 도달하면 실행되는 Runnable 객체 정의
		CyclicBarrier barrier = new CyclicBarrier(3, () -> {
			// 장벽을 넘어 실행될 작업 출력
			System.out.println("모든 스레드 준비 완료, 작업 시작");
			// 3개의 스레드를 대기시키는 CyclicBarrier 객체 생성
		});
		// 생성자 두 번째 인자 값으로 Runnable객체를 넣어 장벽이 깨졌을 때, 실행할 작업을 설정할 수 있습니다.
		
		System.out.println("작업 시작...");
		
		for(int i = 0; i < 3; i++) {
			// Thread: 스레드를 생성하는 클래스
			// 사용 이유: 여러 작업을 병렬로 수행하기 위해 스레드를 생성
			Thread thread = new Thread(() -> {
				System.out.println("스레드 : " + Thread.currentThread().getName() + " 준비 완료");
				
				try {
					// CyclicBarrier.await(): 스레드가 장벽에 도달할 때까지 대기시키는  메서드
					// 사용 이유: 모든 스레드가 특정 지점에 도달할 때까지 기다리기 위해 사용
					barrier.await();
					
					System.out.println("스레드 : " + Thread.currentThread().getName() + "작업 시작...");
					Thread.sleep(1000);
					// 사용 이유: 각 스레드의 작업 시간을 시뮬레이션하기 위해 스레드를 잠시 멈춤
					System.out.println("스레드 : " + Thread.currentThread().getName() + "작업 완료!!!");
				} catch (InterruptedException | BrokenBarrierException e) {
					Thread.currentThread().interrupt();
					throw new RuntimeException(e);
				}
			});
			
			thread.start();
		}
	}
	
}

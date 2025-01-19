package Thread.thread_5;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** ScheduledExecutorService
 *  Java의 java.util.concurrent 패키지에 속한 인터페이스로, 주기적인 작업이나 일정 시간 후 작업을 실행할 때,
 *  유용한 고수준 API를 제공합니다. 이는 ExecutorService를 확장하며, 주요 기능으로 일정 시간 후,
 *  작업 실행, 주기적 작업 실행 등을 지원합니다.
 *  
 *  [ ScheduledExecutorService의 주요 특징 ]
 *  1. 스레드 관리 : 작업을 스레드 풀에서 실행하여 효울적인 스레드 관리를 제공합니다.
 *  2. 스케줄링 : 지정한 지연 시간 이후 작업을 실행하거나, 주기적으로 작업을 반복 실행할 수 있습니다.
 *  3. 동시성 제어 : 스레드 간의 충돌이나 불필요한 리소스 소모를 방지합니다.
 *  
 *  [ Schedule(Runnable command, long delay, TimeUnit unit)
 *  ㅇ 지정한 시간(delay) 후에 작업을 실행합니다.
 *  ㅇ 파라미터 : command == 실행할 작업
 *             delay == 지연 시간
 *             unit == 시간 단위(TimeUnit 객체)
 * */
public class ExecutorServiceExample_6 {

	public static void main(String[] args) {
		
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);
		
		Runnable task_1 = () -> { System.out.println("Task_1 executed at " + System.currentTimeMillis()); };
		Runnable task_2 = () -> { System.out.println("Task_2 executed at " + System.currentTimeMillis()); };
		
		// 첫 번째 : 3초 후, 단일 작업 실행
		scheduler.schedule(task_1, 3, TimeUnit.SECONDS);
		
		// 두 번째 : 초기 1초 후, 시작하고 2초마다 작업 실행
		scheduler.scheduleAtFixedRate(task_2, 1, 2, TimeUnit.SECONDS);
		
		// 세 번째 : 초기 1초 후, 시작하고, 작업 완료 후, 3초 지연 후 반복 실행
		scheduler.scheduleWithFixedDelay(() -> {
			System.out.println("Fixed delay task at " + System.currentTimeMillis());
		}, 1, 3, TimeUnit.SECONDS);
		
		// 마지막 10초 후, 모든 작업 종료
		scheduler.schedule(() -> {
			System.out.println("Shutting down scheduler...");
			scheduler.shutdown();
		}, 10, TimeUnit.SECONDS);
	}
}

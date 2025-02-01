package Thread.thread_2_ControlFunction;

/** 스레드 isAlive()
 *  기능 : 스레드가 활성 상태인지(실행 중인지) 여부를 반환
 *  설명 : 스레드의 상태를 확인하고, 특정 조건에서 스레드 관련 작업을 수행할 때, 유용하다.
 * */
public class Thread_2_IsAlive {

	public static void main(String[] args) {
		
		Thread workThread = new Thread(() -> {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		});
		
		workThread.start();
		
		// 스레드가 실행중 상태라면 true를 반환
		System.out.println("스레드 시작 후 alive : " + workThread.isAlive());
		try {
			// 여기서 멈추는 스레드 == 주 스레드(메인)
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		// 스레드가 종료된 상태라면 false를 반환
		System.out.println("스레드 종료 후 alive : " + workThread.isAlive());
	}
}

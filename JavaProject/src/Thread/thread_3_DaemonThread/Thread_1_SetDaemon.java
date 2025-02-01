package Thread.thread_3_DaemonThread;

public class Thread_1_SetDaemon {

	public static void main(String[] args) {
		
		// 데몬 스레드를 생성합니다. 데몬 스레드는 백그라운드에서 실행되며, 메인 스레드가 종료될 때 자동으로 종료됩니다.
		Thread daemonThread = new Thread(() -> {
			
			// 무한 루프로 계속 백그라운드 작업을 실행
			while(true) {
				System.out.println("백그라운드 작업 중...");
				
				try {
					// 1초 마다 잠시 멈춤
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		// 해당 스레드를 데몬 스레드로 설정합니다.
		daemonThread.setDaemon(true);
		
		// 데몬 스레드를 실행한다.
		daemonThread.start();
		
		try {
			// 메인 스레드를 3초 동안 멈춘다.
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
		// 메인 스레드가 종료되면 데몬 스레드는 자동으로 종료됩니다.
		System.out.println("메인 스레드 종료");
	}
}

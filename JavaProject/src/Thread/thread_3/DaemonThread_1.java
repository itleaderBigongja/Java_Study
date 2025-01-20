package Thread.thread_3;

/** 데몬스레드와 사용자스레드
 *  스레드는 종료 방식에 따라 사용자스레드(User Thread)와 데몬스레드(Daemon Thread)
 *  
 *  [ 사용자 스레드의 경우 ] 
 *  모두 종료되어야 프로그램이 끝나며 하나라도 실행 중이면 마지막 스레드가 실행을 마칠 때까지 기다린다.
 *  
 *  [ 데몬 스레드의 경우 ] 
 *  프로그램 종료 즉, 주 스레드가 종료될 시 데몬 스레드는 강제 종료가 된다.
 *  
 *  사용자 스레드의 설정 : 스레드의 default 설정은 사용자 스레드이며, 설정을 한다면 setDaemon(false)를 하면된다.
 *  데몬 스레드의 설정 : setDaemon(true)로 설정을 하면된다.
 *  
 *  [ Thread와 JVM의 관계 ]
 *  Thread는 JVM이 종료가 될 시, 종료가 됩니다. 여기서
 *  주 스레드가 종료가 되면 데몬 스레드는 강제 종료가 되며, 사용자 스레드가 종료되지 않으면
 *  각각 따로 구현이 되었을 때 일어나는 흐름이며, 주 스레드의 모든 활동이 종료가 되어 프로세스를 멈추기 전에
 *  사용자 스레드가 하나라도 실행중이라면 주 스레드는 사용자 스레드를 종료를 기다립니다.
 *  그런 다음 모든 남아 있던 사용자 스레드가 모두 종료가 되었을 때, 대기하고 있던 주스레드가 종료가 되며,
 *  주 스레드가 종료가 된다면 데몬 스레드 또한 같이 종료가 됩니다.
 */
public class DaemonThread_1 {

	public static void main(String[] args) {
		DaemonThread daemonWorker = new DaemonThread();		// 데몬 스레드
		UserThread userWorker = new UserThread();			// 사용자 스레드
		daemonWorker.setDaemon(true);						// 데몬 스레드 설정( true )
		daemonWorker.start();
		userWorker.start();
		
		int health = 0;
		for(int num = 20; num >= health; num--) {
			
			try {
				Thread.sleep(1000);
				if(num == 20) System.out.println("주 스레드 시작!");
				else System.out.println("주 스레드 health : " + num);
			} catch (InterruptedException e) {
				;
			}
		}
		// stop() 취소선은 stop()메소드를 못쓰는것이 아닌 버전 업이 되면서 업어질 위험이 있다는걸 알려주는 역할
		Thread.currentThread().stop();
		System.out.println("주 스레드 종료");
	}
}

class DaemonThread extends Thread {
	
	@Override
	public void run() {
		int count = 0;
		while(true) {
			count++;
			if(count == 1) System.out.println("데몬 스레드 시작");
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
				;
			}
			System.out.println("데몬 스레드 생성 시간 : " + count);
		}
	}
}

class UserThread extends Thread {
	
	@Override
	public void run() {
		int count = 0;
		for(int i = 1; i <= 30; i++) {
			count++;
			try {
				Thread.sleep(1000);
				if(count == 1) System.out.println("사용자 스레드 시작");
				else System.out.println("사용자 스레드 실행중");
			} catch (InterruptedException e) {
				;
			}
		}
		
		System.out.println("사용자 스레드 종료됩니다.");
	}
}
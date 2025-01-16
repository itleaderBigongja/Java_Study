package thread_3;

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
 *  각각 따로 구현이 되었을 때 일어나는 흐름이며, 주 스레드가 죽었지만, 사용자 스레드가 아직 처리해야할 스케줄이 남았다면
 *  JVM은 종료가 되지 않아 데몬스레드는 주 스레드의 종료 여부와 관계 없이 사용자 스레드 스케줄에 맞춰 JVM이 종료가 될 시,
 *  데몬 스레드가 종료됩니다. 
 */
public class DaemonThread_1 {

	public static void main(String[] args) {
		DaemonThread daemonWorker = new DaemonThread();
		UserThread userWorker = new UserThread();
		daemonWorker.setDaemon(true);
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
package thread_4;

/** 스레드 종료 대기( Thread Termination Waiting )
 *  하나의 스레드가 다른 스레드의 작업완료(종료)를 기다리는 상황을 의미 
 *  이는 일반적으로 스레드가 작업자(worker) 스레드가 종료될 때까지 기다려야 하는 경우에 사용됩니다.
 *  
 *  [ 스레드 종료 대기 방식 ]
 *  1. Thread.join() 메서드 사용
 *  -> Thread.join() 메서드는 호출한 스레드가 종료될 때까지 
 *     현재 스레드를 BLOCKED(다른 스레드가 락을 해제할 때까지 대기)상태로 전환합니다.
 *     
 *  2. Thread.join(timeout)
 *  -> Thread.join(long millis)메서드는 특정 시간(밀리초) 동안만 스레드의 종료를 기다립니다.
 *     지정된 시간이 지나도 스레드가 종료되지 않으면 대기를 중단하고 실행을 계속합니다.
 *     
 *  3. 플래그와 폴링(Polling)
 *  -> 스레드가 종료되었는지 확인하기 위해 Thread.isAlive() 메서드를 사용할 수 있습니다.
 *     이 방법은 반복적으로 스레드 상태를 확인하면서, 종료를 기다리는 방식입니다.
 *     Thread worker = new Thread( () -> { try{Thread.sleep} catch{} });
 *     while(worker.isAlive()){ }
 *     
 *  4. ExecutorService의 종료 대기
 *  -> ExcutorService를 사용하면 스레드 풀의 작업이 모두 완료될 때까지 대기할 수 있습니다.
 *     이때 awaitTermination() 메서드를 사용합니다.
 * */
public class ThreadSync_7 {

	public static void main(String[] args) {
		
		MyThread worker_1 = new MyThread();
		worker_1.setName("첫번째 스레드");
		MyThread worker_2 = new MyThread();
		worker_2.setName("두번째 스레드");
		
		worker_1.start();
		try {
			worker_1.join();	// worker_1이 끝날때까지 기다림
		} catch (InterruptedException e) {
			;
		}
		worker_2.start();		// worker_1이 완료된 후 worker_2가 실행이됨
	}
}

class MyThread extends Thread {	
	public void run() {
		for(int i = 0; i < 5; i++) {
			System.out.println(getName() + " - " + i);
			
			try {
				Thread.sleep(500);
			} catch (Exception e) {
				;
			}
		}
	}
}

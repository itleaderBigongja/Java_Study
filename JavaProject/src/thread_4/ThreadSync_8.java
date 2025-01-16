package thread_4;
/** 스레드 인터럽터(interrupt)
 *  interrupt()
 *  ㅇ 실행중인 스레드에 인터럽트를 보냅니다.
 *  ㅇ 스레드가 sleep(), wait(), join() 상태에 있을 때 인터럽트를 받으면
 *    InterruptedException이 발생합니다. */
public class ThreadSync_8 {
	
	public static void main(String[] args) {
		
		MyThread_2 worker = new MyThread_2();
		worker.start();
		
		try {
			Thread.sleep(3000);	// 3초 후에 인터럽트를 보냄
			worker.interrupt();
		} catch (Exception e) {
			System.out.println("Exception.....");
		}
	}
}

class MyThread_2 extends Thread {	
	
	@Override
	public void run() {
		try {
			for(int i = 1; i <= 5; i++) {
				System.out.println("Thread Running...");
				Thread.sleep(1000); // 1초 대기
			}
		} catch (InterruptedException e) { // 스레드가 Interrupted를 보내면 해당 catch문에서 잡는다.
			System.out.println("Thread was interrupted ");
		}
	}
}
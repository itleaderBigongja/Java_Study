package Thread.thread_1;

public class Runnable_2 {
	
	/** 주 스레드 -> [ 데몬 스레드 실행 ]
	 *  주 스레드가 죽을 시, 데몬 스레드는 종료가 된다.
	 *  마지막으로 JVM이 종료된다.
	 * @throws InterruptedException */ 
//	public static void main(String[] args) throws InterruptedException {
//		
//		PrintRunnable_2 runnable_2 = new PrintRunnable_2();
//		Thread work = new Thread(runnable_2);
//		work.setDaemon(true);
//		work.start();
//		Thread.sleep(100);
//		System.out.println("메인 스레드 호출!!");
//	}
	
	/** 주 스레드 -> [ 일반 스레드 실행 ]
	 *  주 스레드가 죽더라도 일반스레드는
	 *  일반 스레드가 죽어야지만 JVM이 종료된다.  
	 * @throws InterruptedException */
	public static void main(String[] args) throws InterruptedException {
		
		PrintRunnable_2 runnable_2 = new PrintRunnable_2();
		Thread work = new Thread(runnable_2);
		work.setDaemon(false);
		work.start();
		Thread.sleep(100);
	}
}

class PrintRunnable_2 extends GetNameClass implements Runnable{

	@Override
	public void run() {
		try {
			getName();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}

class GetNameClass {
	private String name;
	
	// 현재 클래스의 이름을 불러오는 메소드
	public void getName() throws InterruptedException {
		for(int i = 0; i < 10000; i++) {
			Thread.sleep(100);
			System.out.println(i);
		}
	}	
}

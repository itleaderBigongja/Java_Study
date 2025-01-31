package Thread.thread_1_ClassAndInterface;

import java.util.concurrent.TimeUnit;

// Runnable 인터페이스를 구현하는 MyRunnable 클래스 정의
class MyRunnable implements Runnable {

	// Runnable 인터페이스의 run()메서드 재정의
	@Override
	public void run() {
		// 현재 스레드의 Thread 객체를 얻습니다. 이를 통해 스레드 이름 등을 확인할 수 있다.
		Thread thread = Thread.currentThread();
		
			// 스레드가 실행될 때 반복적으로 작업을 수행하는 for 루프
			for(int i = 0; i < 5; i++) {
				// 현재 스레드 이름과 반복 횟수를 콘솔에 출력
				System.out.println(thread.getName() + " 스레드 실행중");
				try {
	                // Thread.sleep() 메서드는 현재 스레드를 지정된 시간(밀리초) 동안 일시 정지시킵니다.
	                // 여기서는 500밀리초(0.5초) 동안 스레드를 멈춥니다.
	                Thread.sleep(500);
	            } catch (InterruptedException e) {
	                // InterruptedException은 스레드가 sleep() 상태에서 깨어났을 때 발생할 수 있는 예외입니다.
	                // 예외가 발생하면 스택 트레이스를 출력하여 어떤 오류가 발생했는지 확인합니다.
	                e.printStackTrace();
	            }
			}
	}
}

public class Thread_2_RunnableInterface {

	public static void main(String[] args) {
		
		boolean threadYn;
		
		// MyRunnable 클래스의 객체를 생성
		MyRunnable myRunnable = new MyRunnable();
		
		// Thread 객체를 생성하면서 첫 번째 인자로 Runnable 객체를 전달하고, 두 번째 인자로 스레드 이름을 설정
		Thread thread_1 = new Thread(myRunnable, "첫 번째 스레드");
		Thread thread_2 = new Thread(myRunnable, "두 번째 스레드");
		
		// thread_1과 thread_2 스레드를 시작
		// start() 메서드를 호출하면 새로운 스레드가 생성되고, 스레드의 run() 메서드가 실행
		thread_1.start();
		thread_2.start();
		
		
		// 생성했던 thread_1과 thread_2의 상태가 완료된 상태가 아니라면 while 루프
		while (!"TERMINATED".equalsIgnoreCase(String.valueOf(thread_1.getState()))
				&& !"TERMINATED".equalsIgnoreCase(String.valueOf(thread_2.getState()))) {
			try {
				Thread.sleep(1000);
				System.out.println("주 스레드 실행중..");
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		System.out.println("모든 스레드 종료");
	}
}

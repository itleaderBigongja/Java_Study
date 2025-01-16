package thread_1;

public class Runnable_1 {

	/**
	 * Runnable = 인터페이스
	 * Thread 클래스 상속이 어려운 경우, 
	 * 이럴 때는 run 메서드를 정의하는 Runnable 인터페이스를 구현 클래스를 정의하고
	 * 이 클래스의 객체를 Thread의 생성자로 전달하는 방법을 사용한다. 
	 * run() 메서드를 누가 구현하는가의 차이만 있을 뿐 거의 같은 방법이다.
	 * */
	public static void main(String[] args) {
		
		// Runnable 인터페이스( void run()메소드의 구현 객체 )
		PrintRunnable_1 runnable = new PrintRunnable_1();
		
		// Runnable 인터페이스의 void run()를 Thread 객체에 장착
		Thread work = new Thread(runnable);
		
		/** 실제 Runnable의 구현체의 run()를 실행
		 *  스레드를 생성하는 형식만 다를 뿐, run() 메서드의 코드는 같아 실행 결과도 동일하다.
		 *  Thread 클래스로부터 상속받는 방법보다 한 단계 더 거쳐 번거롭고 직관적이지 못한다.
		 *  자바의 언어 특성상 다중 상속을 지원하지 않기 때문에 이런 방법을 제공한다.
		 */
		work.start();
		
		for(int num = 0; num < 30; num++) {
			System.out.println("main Thread 호출");
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}

// implements는 여러 개의 인터페이스를 상속 받을수 있다.
class PrintRunnable_1 implements Runnable{
	@Override
	public void run() {
		for(int num = 0; num < 30; num++) {
			System.out.println("sub Thread 호출");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
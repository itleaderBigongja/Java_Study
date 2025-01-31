package Thread.thread_1_ClassAndInterface;

/** Thread 클래스를 상속
 *  Thread 클래스를 상속 받아 run() 메서드를 재정의하여 구현  
 *  @author : 비공자
 **/ 
class MyThread extends Thread{
	
	// run() 메서드 오버라이드: 스레드가 실행할 작업을 정의
	@Override
	public void run() {
		// 스레드가 실행될 때 반복적으로 작업을 수행하는 for루프
		for(int i = 0; i < 5; i++) {
			System.out.println(i + "번째 스레드 이름 : " + Thread.currentThread().getName() + "스레드 실행 중!!");
			
			try {
				// Thread.sleep() 메서드: 현재 스레드를 지정된 시간(밀리초) 동안 일시 정지 시킨다.
				// 여기서는 5밀리초(0.5초) 동안 스레드를 멈춥니다.
				Thread.sleep(500);;
			}catch (InterruptedException e) {
				// InterruptedException은 스레드가 sleep() 상태에서 깨어났을 때, 발생할 수 있는 예외이다.
				// 예외가 발생하면 Stack Trace()를 출력하여 어떤 오류가 발생했는지 확인
				e.printStackTrace();
				
			}
		}
	}
}
public class Thread_1_ExtendsClass extends MyThread {

	public static void main(String[] args) {
		// MyThread 클래스의 객체를 생성( 2개의 Thread 생성 )
		MyThread thread1 = new MyThread();
		MyThread thread2 = new MyThread();
		
		// 스레드 이름을 설정 ( thread_1, thread_2 )
		thread1.start();
		thread2.start();
		
		// 메인 스레드가 종료되었음을 알리는 메시지
		// 서브 스레드가 끝나지 않으면 주 스레드는 종료하지 않고 서브 스레드가 모두 종료가 되었을 때, 종료한다. 
		System.out.println("메인 스레드 종료!!");
	}
}


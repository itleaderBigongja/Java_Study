package Thread.thread_4;

/** 스레드 대기( Thread Waiting )
 *  스레드가 특정 조건이 충족될 때까지 실행을 멈추고 기다리는 상태를 의미합니다.
 *  자바에서는 스레드가 공유 자원에 안전하게 접근할 수 있도록 
 *  스레드 대기와 관련된 메커니즘을 제공합니다.
 *  이를 통해 스레드 간 효율적인 협력을 구현할 수 있다.
 *  
 *  [ 스레드 상태와 대기 ]
 *  스레드는 다음과 같은 상태를 가질 수 있으며, 대기는 주로, 
 *  WATING, TIMED_WAITING, 또는 BLOCKED 상태와 관련이 있습니다.
 *  ㅇ Thread.currentThread().getState(); 
 *  ㅇ NEW : 스레드가 생성되었지만 아직 시작되지 않은 상태
 *  ㅇ RUNNABLE : 실행 가능한 상태. 실제 실행 중일 수도 있고, CPU 할당을 기다릴 수도 있음.
 *  ㅇ WAITING : 다른 스레드가 신호를 줄 때까지 무기한 대기
 *  ㅇ TIMED_WAITING : 일정 시간 동안만 대기
 *  ㅇ BLOCKED : 다른 스레드가 락을 해제할 때까지 대기.
 *  ㅇ TERMINATED : 스레드 실행이 완료된 상태
 *  
 *  [ 스레드 대기 방법 ]
 *  Object 클래스의 wait()와 notify(), notifyAll()
 *  ㅇ wait()      : 호출한 스레드를 WAITING 상태로 전환
 *  ㅇ notify()    : 대기 중인 스레드 중 하나를 깨움.
 *  ㅇ notifyAll() : 대기 중인 모든 스레드를 깨움.
 *  ㅇ 위 3개의 메서드들은 반드시 syncronized 블록 내에서 호출되어야 합니다.
 *  
 *  [ 스레드 대기 시 주의할 점 ]
 *  데드락 방지
 *  -> 락(Lock)을 걸고 해제하지 않으면 교착 상태가 발생할 수 있습니다.
 *  스레드 간 협력 
 *  -> wait()와 notify()를 적절히 사용해야 합니다.
 *  InterruptedException 처리
 *  -> 스레드 대기 중 인터럽트(interrupte)가 발생하면 예외가 발생하므로 이를 적절히 처리해야 합니다. 
 *  */  
public class ThreadSync_6 {

	public static void main(String[] args) {
		SharedResource resource = new SharedResource();
		Thread producer = new Thread(()-> {
			try {
				resource.produce();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		
		// resource(SharedResource)객체의 consume 메서드 참조
		Thread consumer = new Thread(resource::consume);
//		Thread consumer2 = new Thread(() -> {
//			resource.consume();
//		});
		producer.start();
		consumer.start();
	}
}

class SharedResource {
	
	public synchronized void produce() throws InterruptedException{
		System.out.println("Producing...");
		wait();	// Thread 대기상태로 진입
		System.out.println("Resumed production.");
	}
	
	public synchronized void produce2() throws InterruptedException{
		System.out.println("Producing...");
		wait();	// Thread 대기상태로 진입
		System.out.println("Resumed production.");
	}
	
	public synchronized void consume() {
		System.out.println("Consuming...");
		notify(); // 대기 상태인 Thread를 깨움
	}
}
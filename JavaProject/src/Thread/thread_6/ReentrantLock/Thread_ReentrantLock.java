package Thread.thread_6.ReentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/** ReentrantLock이란?
 *  [개념]
 *  -> java.util.concurrent.locks 패키지에서 제공하는 락(Lock) 구현체 중 하나로,
 *     synchronized 키워드와 유사하지만, 더 많은 기능과 유연성을 제공
 *     
 *  [주요 특징]
 *  -> 재진입 가능: 한 스레드가 이미 획득한 락을 다시 획득할 수 있다.
 *               이를 통해 synchronized 키워드와 비슷한 방식으로 내부 메서드 호출 시 락을 획득할 수 있다.
 *               
 *  -> 공정성 : 락(Lock)을 획득하기 위해 기다리는 스레드를 대기 시간 순서대로 처리할 수 있습니다.(생성자 설저 가능)
 *  
 *  -> 유연성 : tryLock(), lockInterruptibly() 등 
 *             다양한 락 획득 방법을 제공하여 스레드 제어를 세밀하게 할 수 있다.
 *             
 *  [주요 용도]
 *  -> 세밀한 락 제어: 특정 스레드가 락을 기다리는 시간을 제한하거나, 락 획득을 중단 할 수 있다.
 *  -> 공정성 보장: 대기 스레드에게 락을 공정하게 할당해야 하는 경우에 사용
 *  -> 조건 변수: Condition 인터페이스를 통해 대기 및 알림 기능을 사용할 수 있다.
 * */
public class Thread_ReentrantLock {

	// ReetrantLock: 재진입 가능한 락을 제공하는 클래스
	// 사용 이유: synchronized보다 더 세밀한 락 제어와 다양한 락 획득 방법을 제공
	private int count = 0;
	
	// ReentrantLock 객체 생성(공정성 설정을 하지 않음)
	private final Lock lock = new ReentrantLock();
	
	// increment() 메서드: 공유 변수 count를 1증가시키는 메서드
	public void increment() {
		// Lock.lock(): increment() 메서드를 호출한 Thread에 락을 걸어둔다.
		// 첫 번째 스레드가 먼저 들어와 Lock이 걸리면 두 번째 스레드는 대기 상태가 된다.
		// 사용 이유: 다른 스레드가 공유 변수에 동시에 접근하는 것을 막고, 데이터 무결성을 보장하기 위해 사용
		
		// 락 획득
		lock.lock();
		try {
			count++;	// 공용 변수 count를 1 증가
		}finally {
			// Lock.unlock() : 락을 해제하는 메서드
			// 사용 이유: try 블록에[서 예외가 발생하더라도 반드시 락을 해제하여
			//          다른 스레드가 락을 획득할 수 있도록 보장
			// 락 해제 
			lock.unlock();
		}
	}
	
	// getCount(): 현재 count 값을 반환하는 메서드
	public int getCount() {
		// 현재의 count 값 반환
		return count;
	}
	
	public static void main(String[] args) {
		
		//ReentrantLockExample 객체 생성
		Thread_ReentrantLock reentRantLock = new Thread_ReentrantLock();
		
		// Thread: 스레드를 생성하는 클래스
		// 사용 이유: 여러 작업을 병렬로 수행하기 위해 스레드를 생성
		// 첫 번째 스레드 생성
		Thread thread1 = new Thread(() -> {
			for(int i = 0; i < 1000; i++) {
				// increment() 메서드를 호출하여 count 증가
				reentRantLock.increment();
			}
		});
		
		// Thread: 스레드를 생성하는 클래스
		// 사용 이유: 여러 작업을 병렬로 수행하기 위해 스레드를 생성
		// 두 번째 스레드 생성
		Thread thread2 = new Thread(() -> {
			for(int i = 0; i < 1000; i++) {
				// increment() 메서드를 호출하여 count 증가
				reentRantLock.increment();
			}
		});
		
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
		
		System.out.println("Count: " + reentRantLock.getCount() + " 최종 값");
	}
}

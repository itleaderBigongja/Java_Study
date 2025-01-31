package Thread.thread_6_Intermediate.AtomicClass;

import java.util.concurrent.atomic.AtomicInteger;

/** Atomic 클래스란?
 *  개념: java.util.concurrent.atomic 패키지에서 제공하는 클래스들로, 여러 스레드가 동시에 공유 변수에 접근하고 수정할 때
 *       동시성 문제를 해결하기 위한 클래스 입니다.
 *       
 *  원자성 보장: Atomic 클래스는 변수에 대한 읽기, 쓰기, 수정 연산을 원자적으로 수행하여, 
 *            중간에 다른 스레드가 끼어들지 못하도록 보장합니다.
 *            
 *  락 프리: synchronized 키워드나 Lock 객체와 달리 락을 사용하지 않고 동기화를 구현하여 성능 면에서 더 유리합니다.
 **/
public class Thread_AtomicClass_Integer {
	
	/** AtomicInteger : 정수형 변수에 대한 원자적 연산을 제공하는 클래스
	 *  사용 이유: 여러 스레드에서 동시에 접근하여 값을 변경하는 경우에 데이터 무결성을 보장하기 위해 사용*/
	// 원자적 정수형 변수 선언 및 초기화
	private final AtomicInteger counter = new AtomicInteger(0);
	
	
	// increment() 메서드: AtomicInteger 값을 1 증가시키는 메서드
	public void increment() {
		// incrementAndGet() 메서드: AtomicInteger 값을 원자적으로 1 증가시키고, 증가된 값을 반환하는 메서드
		// 사용 이유: 여러 스레드 환경에서 안전하게 값을 증가시키기 위해 사용
		counter.incrementAndGet();
	}
	
	// decrement() 메서드: AtomicInteger 값을 1 감소시키는 메서드
	public void decrement() {
		// decrementAndGet() 메서드: AtomicInteger 값을 원자적으로 1 감소시키고, 감소된 값을 반환하는 메서드
		// 사용 이유: 여러 스레드 환경에서 안전하게 값을 감소시키기 위해 사용
		counter.decrementAndGet();
	}
	
	// getCount() 메서드: AtomicInteger의 현재 값을 반환하는 메서드
	public int getCount() {
		// get() 메서드: AtomicInteger의 현재 값을 반환하는 메서드
		// 사용 이유: AtomicInteger의 현재 값을 얻기 위해 사용
		return counter.get();
	}
	
	public static void main(String[] args) {
		// Thread_AtomicClass_Integer 객체 생성
		Thread_AtomicClass_Integer example = new Thread_AtomicClass_Integer();

        // thread1: increment 메서드를 10000번 호출하는 첫 번째 스레드 생성
		Thread thread1 = new Thread(()-> {
            for(int i=0; i<10000; i++){
                example.increment();
            }
        });
		
		// thread2: increment 메서드를 10000번 호출하는 두 번째 스레드 생성
        Thread thread2 = new Thread(() -> {
           for(int i=0; i<10000; i++){
               example.increment();
           }
        });
        
        thread1.start(); 	// 첫 번째 스레드 시작
        thread2.start(); 	// 두 번째 스레드 시작

        try {
       	 	thread1.join();	// 첫 번째 스레드 종료 대기
       	 	thread2.join();	// 두 번째 스레드 종료 대기
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e.getMessage());
		}
        
        System.out.println("Count: " + example.getCount()); // 최종 카운트 출력

        
        // thread1: decrement 메서드를 10000번 호출하는 첫 번째 스레드 생성
        thread1 = new Thread(() -> {
           for(int i=0; i<10000; i++){
               example.decrement();
           }
        });
        
        // thread2: decrement 메서드를 10000번 호출하는 두 번째 스레드 생성
        thread2 = new Thread(()-> {
           for(int i=0; i<10000; i++){
               example.decrement();
           }
        });

        thread1.start();	// 첫 번째 스레드 시작
        thread2.start();	// 두 번째 스레드 시작
         
        try {
        	thread1.join();	// 첫 번째 스레드 종료 대기
			thread2.join();	// 두 번째 스레드 종료 대기
		} catch(InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e.getMessage());
		}
         
        System.out.println("Count: " + example.getCount()); // 최종 카운트 출력
	}
}
package Thread.thread_6.AtomicClass;

import java.util.concurrent.atomic.AtomicLong;

public class Thread_AtomicClass_Long {

	// AtomicLong: long 타입 변수에 대한 원자적 연산을 제공하는 클래스
    // 사용 이유: 여러 스레드에서 동시에 접근하여 값을 변경하는 경우에 데이터 무결성을 보장하기 위해 사용
    private final AtomicLong counter = new AtomicLong(0); // 원자적 long 타입 변수 선언 및 초기화

    // increment() 메서드: AtomicLong 값을 1 증가시키는 메서드
    public void increment() {
       // incrementAndGet(): AtomicLong 값을 원자적으로 1 증가시키고, 증가된 값을 반환하는 메서드
       // 사용 이유: 여러 스레드 환경에서 안전하게 값을 증가시키기 위해 사용
        counter.incrementAndGet();
    }
    // getCount(): AtomicLong의 현재 값을 반환하는 메서드
    public long getCount() {
        // get(): AtomicLong의 현재 값을 반환하는 메서드
        // 사용 이유: AtomicLong의 현재 값을 얻기 위해 사용
        return counter.get();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread_AtomicClass_Long example = new Thread_AtomicClass_Long(); // Thread_AtomicClass_Long 객체 생성

        // thread1: increment 메서드를 10000번 호출하는 첫 번째 스레드 생성
        Thread thread1 = new Thread(()-> {
            for(int i=0; i<10000; i++){ // 10000번 반복
               example.increment(); 	 // AtomicLong 값 증가
            }
        });

        // thread2: increment 메서드를 10000번 호출하는 두 번째 스레드 생성
        Thread thread2 = new Thread(()-> {
            for(int i=0; i<10000; i++){ // 10000번 반복
                example.increment(); 	// AtomicLong 값 증가
             }
        });

        thread1.start(); 	// 첫 번째 스레드 시작
        thread2.start(); 	// 두 번째 스레드 시작

        thread1.join();		// 첫 번째 스레드 종료 대기
        thread2.join(); 	// 두 번째 스레드 종료 대기
        System.out.println("Count: " + example.getCount()); // 최종 카운트 출력
    }   
}

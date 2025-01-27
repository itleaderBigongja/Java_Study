package Thread.thread_6.AtomicClass;

import java.util.concurrent.atomic.AtomicBoolean;

public class Thread_AtomicClass_Boolean {

	// AtomicBoolean: boolean 타입 변수에 대한 원자적 연산을 제공하는 클래스
	// 사용 이유: 여러 스레드에서 동시에 접근하여 값을 변경하는 경우에 데이터 무결성을 보장하기 위해 사용
	// 원자적 boolean 변수 선언 및 초기화(default 값: false)
	private final AtomicBoolean flag = new AtomicBoolean(false);
	
	// isFlag() 메서드: AtomicBoolean의 현재 값을 변환하는 메서드
	public boolean isFlag() {
		// get() 메서드: AtomicBoolean의 현재 값을 반환하는 메서드
		// 사용 이유: AtomicBoolean의 현재 값을 얻기 위해 사용
		return flag.get();
	}
	// setFlag(boolean value): AtomicBoolean의 현재 값을 설정하는 메서드
	public void setFlag(boolean value) {
		// set() 메서드:  AtomicBoolean의 현재 값을 설정하는 메서드
		// 사용 이유: AtomicBoolean의 현재 값을 설정하기 위해 사용
		flag.set(value);
	}
	
	// compare[비교하다]
	// compareAndSetFlag(boolean expect[요구하다, 예상하다.], boolean update) 메서드
	// -> AtomicBoolean의 값을 원자적으로 변경하는 메서드
	public boolean compareAndSetFlag(boolean expect, boolean update) {
		// compareAndSetFlag(expect, update): AtomicBoolean의 값을 원자적으로 변경하고 성공여부를 반환하는 메서드
		// 사용 이유: 현재 값을 확인하고, 기대 값과 같은 경우 값을 업데이트 하기 위해 사용
		return flag.compareAndSet(expect, update);
	}
	
	// setFlag(boolean value) 메서드: AtomicBoolean의 현재 값을 설정하는 메서드
	public static void main(String[] args) throws InterruptedException {
		
		// Thread_AtomicClass_Boolean 객체 생성
		Thread_AtomicClass_Boolean example = new Thread_AtomicClass_Boolean();
		System.out.println("초기(default) 값: " + example.isFlag());
		
		// thread1: setFlag() 메서드를 사용하여 AtomicBoolean 값을 변경하는 스레드 생성
		Thread thread1 = new Thread(() -> {
			
			// AtomicBoolean 값을 변경하는 스레드 변경
			example.setFlag(true); 
			// 변경 값 출력
			System.out.println("Thread 1 변경 값 " + example.isFlag()); 
		});
		
		// thread2: compareAndSetFlag() 메서드를 사용하여 AtomicBoolean 값을 변경하는 스레드 생성
		Thread thread2 = new Thread(() -> {
			// 기대 값과 같은 경우 변경
			boolean result = example.compareAndSetFlag(false, true);
			// 결과 및 변경 값 출력
			System.out.println("Thread 2 변경 성공 " + result + " 변경 값 " + example.isFlag());
		});
		
		thread1.start();	// 첫 번째 스레드 시작...
		thread2.start();	// 두 번째 스레드 시작...
		
		thread1.join();		// 첫 번째 스레드 종료 대기
		thread2.join();		// 두 번째 스레드 종료 대기
		
		System.out.println("최종 변경된 example 값 : " + example.isFlag());
	}
}
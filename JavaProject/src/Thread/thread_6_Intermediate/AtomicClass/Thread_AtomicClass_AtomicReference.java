package Thread.thread_6_Intermediate.AtomicClass;

import java.util.concurrent.atomic.AtomicReference;

public class Thread_AtomicClass_AtomicReference {

	// AtomicReference: 객체 참조에 대한 원자적 연산을 제공하는 클래스
	// 사용 이유: 여러 스레드에서 동시에 접근하여 객체 참조를 변경하는 경우에 데이터 무결성을 보장하기 위해 사용
	private final AtomicReference<String> reference = new AtomicReference<String>("initial value");
	
	// getReference() 메서드: AtomicReference()의 현재 참조 값을 변환하는 메서드
	public String getReference() {
		// get() 메서드: AtomicReference()의 현재 참조 값을 반환하는 메서드
		// 사용 이유: AtomicReference의 현재 참조 값을 얻기 위해 사용
		return reference.get();
	}
	
	// setReference(String value): AtomicReference()의 참조 값을 설정하는 메서드
	public void setReference(String value) {
		// set() 메서드: AtomicReference()의 참조 값을 설정하는 메서드
		// 사용 이유: AtomicReference()의 참조 값을 설정하기 위해 사용
		reference.set(value);
	}
	
	// compareAndSetReference(String expected, String update)
	// -> AtomicReference()의 값을 원자적으로 변경하는 메서드
	public String compareSetAndReference(String expected, String update) {
		
		// compareAndSet(expect, update): AtomicReference()의 값을 원자적으로 변경하고 성공 여부를 반환하는 메서드
		// 사용 이유: 현재 값이 기대 값과 같을 경우, 원자적으로 값을 업데이트 위해 사용
		if(reference.compareAndSet(expected, update))
			return "변경 성공!";
		else
			return "변경 실패";
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		
		// AtomicReference 객체 생성
		Thread_AtomicClass_AtomicReference example = new Thread_AtomicClass_AtomicReference();
		
		// 초기 값 가져오기
		System.out.println("초기(default) 값 : " + example.getReference());
		
		// thread1: setReference() 메서드
		// -> 사용하여 AtomicReference() 값을 변경하는 스레드 생성
		Thread thread1 = new Thread(() -> {
			// AtomicReference 값을 "changed value"로 변경
			example.setReference("changed value");
			// 변경 값 출력
			System.out.println("Thread 1 변경 값: " + example.getReference());
		});
		
		// thread2: compareAndSetReference() 메서드
		// -> 위 메서드를 사용하여 AtomicReference 값을 변경하는 스레드 생성
		Thread thread2 = new Thread(() -> {
			
			// 기대 값과 같으면 변경
			String result = example.compareSetAndReference("initial value", "other value"); 
			System.out.println("Thread 2 변경 결과: " + result +" 변경 값: " + example.getReference()); // 변경 결과 및 변경 값 출력
		});
		
		thread1.start();	// 첫 번째 스레드 시작...
		thread2.start();	// 두 번째 스레드 시작...
		
		thread1.join();		// 첫 번째 스레드 종료대기
		thread2.join();		// 두 번째 스레드 종료대기
		
		// 최종 값 출력
		System.out.println("최종 값 : " + example.getReference());
	}
}

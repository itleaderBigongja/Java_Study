package Thread.thread_6.Exchanger;
/** Exchanger란?
 *  [개념]
 *  -> 두 개의 스레드가 데이터를 안전하게 교환할 수 있도록 하는 동기화 도구
 *  
 *  [동작 방식]
 *  -> 각 스레드는 Exchanger 객체의 exchange() 메서드를 호출하여 자신의 데이터를 상대 스레드에게 전달하고,
 *     상대 스레드의 데이터를 받는다.
 *     exchange() 메서드는 두 스레드가 모두 호출될 때까지 대기하며, 두 스레드가 모두 데이터를 제공하면 데이터를 교환하고
 *     각각 메서드 호출을 완료 합니다. 
 *     
 *   [주요 용도]
 *   -> 데이터 교환 : 두 스레드 간에 데이터를 주고받아야 할 때 사용
 *   -> 파이프라인 : 생산자와 소비자 모델에서 데이터를 안전하게 전달하는 데 사용
 *   -> 양방향 통신 : 두 스레드가 서로 데이터를 교환하면서 통신해야 할 때 사용
 **/

import java.util.concurrent.Exchanger;

public class Thread_Exchanger {

	public static void main(String[] args) {
		
		// Exchanger 객체 : 두 스레드가 데이터를 교환할 수 있도록 하는 동기화 클래스
		// 사용 이유: 두 스레드가 서로 데이터를 안전하게 주고받아야 할 때 사용
		// String 타입의 데이터를 교환하는 Exchanger 객체 생성
		Exchanger<String> exchanger = new Exchanger<String>();
		
		// Thread: 스레드를 생성하는 클래스
		// 사용 이유: 병렬로 작업을 수행하기 위해 스레드를 생성
		Thread thread1 = new Thread(() -> {
			
			String data1 = "첫 번째 스레드 : 데이터 값";
			System.out.println("첫 번째 스레드 값 : " + data1 + "생성");
			
			// Exchanger.exchange(T data): 데이터를 교환하고 상대 스레드의 데이터를 받을 때까지 대기하는 메서드
			// 사용 이유: 스레드 간 데이터 교환을 위해 사용하며, 데이터를 보냄과 동시에 상대방의 데이터를 받기 위해 사용
			try {
				// 첫 번째 스레드 데이터를 보내고 두 번째 스레드의 데이터를 받음
				String received1 = exchanger.exchange(data1);
				// 수신 데이터 메시지 출력
				System.out.println("첫 번째 스레드: 교환 데이터 수신 = " + received1);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e);
			}
		});
		
		// Thread: 스레드를 생성하는 클래스
		// 사용 이유: 병렬로 작업을 수행하기 위해 스레드를 생성
		Thread thread2 = new Thread(() -> {
			
			String data2 = "두 번째 스레드 : 데이터 값";
			System.out.println("두 번째 스레드 값 : " + data2 + "생성");
			
			// Exchanger.exchange(T data): 데이터를 교환하고 상대 스레드의 데이터를 받을 때까지 대기하는 메서드
			// 사용 이유: 스레드 간 데이터 교환을 위해 사용하며, 데이터를 보냄과 동시에 상대방의 데이터를 받기 위해 사용
			try {
				// 두 번째 스레드 데이터를 보내고 첫 번째 스레드의 데이터를 받음
				String received2 = exchanger.exchange(data2);
				// 수신 데이터 메시지 출력
				System.out.println("두 번째 스레드: 교환 데이터 수신 = " + received2);
			} catch (InterruptedException e2) {
				Thread.currentThread().interrupt();
				throw new RuntimeException(e2);
			}
		});
		
		thread1.start();
		thread2.start();
	}
}

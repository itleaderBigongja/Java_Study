package Thread.thread_4_Syncronization;

public class Thread_1_Syncronized {
	
	// 전역(공유) 변수, 여러 스레드에서 동시에 접근할 수 있다.
	private static int sharedValue = 0;
	
	// syncronized 키워드를 사용하여 해당 메서드를 임계 영역으로 지정
	// 전역 동기화 메서드
	public static synchronized void increment() {
		sharedValue++;
	}
	
	public static void main(String[] args) {
		
		// 여러 개의 스레드를 생성하고 실행
		Thread[] threads = new Thread[1000];
		for(int i = 0; i < 1000; i++) {
			// 각  스레드는 increment() 메서드를 실행
			threads[i] = new Thread(() -> {
				increment();
			});
			threads[i].start();
		}
		
		// 모든 스레드가 종료될 때까지 메인 스레드를 대기 시킵니다.
		for(Thread thread : threads) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		
		// 전역(공유자원) 변수sharedValue의 최종 값
		System.out.println("최종 값 : " + sharedValue);
	}
}

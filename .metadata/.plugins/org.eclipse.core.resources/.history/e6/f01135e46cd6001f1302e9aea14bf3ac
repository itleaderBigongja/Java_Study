package thread_4;
/** 스레드 동기화(syncronized)
 *  ㅇ 스레드가 공유 자원에 접근할 때 충돌을 방지하기 위해 사용됩니다.
 *  ㅇ syncronized 키워드를 사용하여 메서드나 블록을 동기화할 수 있습니다.
 * */
public class ThreadSync_9 {

	public static void main(String[] args) {
		
		Counter counter = new Counter();
		
		// Runnable의 run() 메서드를 람다식으로 표현함
		Runnable task = () -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        };
        
        Thread worker_1 = new Thread(task);
        Thread worker_2 = new Thread(task);
        
        worker_1.start();
        worker_2.start();
        
        try {
			worker_1.join();
			worker_2.join();
		} catch (Exception e) {
			// TODO: handle exception
		}
        System.out.println("마지막 카운트의 개수 : " + counter.getCounter());
	}
}

class Counter {
	private int count = 0;
	
	// 최종 결과가 2000이 나오지 않는 이유는 스레드 간 경쟁 조건(Race Condition) 때문입니다
	// 경쟁 조건은 여러 스레드가 "공유 자원에 동시에 접근하여 예상치 못한 결과를 초래하는 상황을 말합니다."
	// 각 스레드가 데이터를 읽고, 수정하고, 저장하는 과정이 중간에 다른 스레드에 의해 방해 받기 때문에 발생합니다.
	public void increment() {
		// 문제 발생 가능: 읽기-수정-저장 과정이 원자적이지 않음
		count++;
	}	
	public int getCounter() {
		return count;
	}
}
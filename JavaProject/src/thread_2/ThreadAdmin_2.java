package thread_2;

/** Thread 제어 함수 : sleep(millis), yield()
 *  sleep : 메서드는 지정한 시간만큼 스레드의 실행을 중지하고 잠시쉰다. 시간은 1/1000초 단위로 섬세하게 지정한다. 
 *  yield : 할당된 시간을 포기하고 실행 시간을 양보하도록 의사를 표시한다. 이 메서드를 호출한다고 해서
 *          즉시 실행 대기 상태가 되는 것은 아니며 스케쥴러가 결정한다.
 */
public class ThreadAdmin_2 {
	
	/** 우선순위
	 *  스레드는 CPU 시간을 공평하게 배정받는다. 앞에서 만든 예제의 두 스레드는 sleep 시간이 달라서
	 *  끝나는 시간에 차이가 있는데 지연 시간을 일치 시키면 거의 똑같이 끝난다.
	 *  우선순위는 CPU 시간을 얼마나 더 사용할 것인가를 지정하는 스레드의 속성이다. */
	
	
	public static final int NORM_PRIORITY = 5;	// 보통 순위
	public static final int MIN_PRIORITY = 1;	// 낮은 순위
	public static final int MAX_PRIORITY = 10;	// 높은 순위
	
	public static void main(String[] args) {
		PrintThread1 worker1 = new PrintThread1();
		PrintThread2 worker2 = new PrintThread2();
		worker1.start();
		worker2.setPriority(Thread.MAX_PRIORITY);
		worker2.start();
		
		
	}
}

class PrintThread1 extends Thread {
	
	@Override
	public void run() {
		double sum = 0;
		for(int i = 0; i < 10000000; i++) {
			sum += Math.cos(i);
		}
		System.out.println("cos = " + sum);
	}
}

class PrintThread2 extends Thread {
	
	@Override
	public void run() {
		double sum = 0;
		for(int i = 0; i < 10000000; i++) {
			sum += Math.sin(i);
		}
		System.out.println("sin = " + sum);
	}
}
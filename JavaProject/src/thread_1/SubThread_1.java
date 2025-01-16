package thread_1;
/**
 * RoundRobin
 * 운영체제는 CPU의 시간을 잘게 쪼개 교댜ㅐ로 스레드를 실행하여 동시에 여러 가지 일을 처리하는 것처럼
 * 흉내 내는데, 이런 방식을 라운드 로빈(RoundBin)
 * 병렬성이 향상되는 이점이 있지만, CPU의 속도가 유한하고 작업 전환으ㅡ로 인해 전체적인 성능은 오히려 떨어질 수도 있다.
 * 코어가 여러 개인 CPU는 스레드 처리 능력이 탁월하여 멀티 스레드를 활용하는 것이 확실히 유용하다.
 * 
 * 백그라운드 작업이 많을 때, 적합하며, 사용자를 대면하는 작업은 싱글 스레드가 유용하다.
 * 스레드는 클래스이며, Thread를 상속 받으면 run()메소드 안에서 작업을 시작한다.
 */ 
public class SubThread_1{

	/**
	 * 자바 프로그램은 main에서 시작하며, main이 주 스레드이다. 최초 기동할 때는 주 스레드 하나밖에 없지, 
	 * 백그라운드 작업이 필요할 때, 추가로 스레드를 더 만든다.
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		
		// 주 스레드 실행
		for(int i = 1; i <= 5; i++) {
			// 스레드 생성(1, 2, 3, 4, 5)
			ThreaRun work = new ThreaRun(i);
			
			// 서브 스레드 실행
			work.start();
		}
		
		Thread.sleep(4000);
		System.out.println("O");
	}
}

// 서브 스레드
class ThreaRun extends Thread{
	
	private int i;
	
	public ThreaRun(int i) {
		this.i = i;
	}

	@Override
	public void run() {
		for(int num = 0; num < 30; num++) {
			System.out.println(i);
			try {
				Thread.sleep(100); // 0.1
			} catch (InterruptedException e) {
				;
			}
		}
	}
}
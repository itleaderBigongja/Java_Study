package thread_2;

/** 스레드 관리
 *  스레드를 생성하고 start로 기동시키면 이후부터 운영체제는 주기적으로 시간을 할당하여 스레드를 실행한다.
 *  run()메서드가 리턴하여 작업을 완료하면 스레드는 실행을 종료하고 관련 정보나 호출 스택을 제거한다.
 *  스레드를 관리하는 일은 운영체제의 몫이며, 프로그램은 필요할 때, 스레드를 생성하여 기동시키면 된다.
 * */
public class ThreadAdmin_1 {

	/** start에 의해 스레디ㅡ를 기동할 때, 바로 실행을 시작하지 않고 실행 대기큐에서 자기 차례를 기다린다.
	 *  스케줄러는 CPU 시간을 쪼개 대기열의 스레드를 번갈아 실행한다.
	 *  자기 차례가 되면 run 메서드의 코드를 실행하며 할당 받은 시간을 다 스면 대기열로 들어가 다음 차례를 기다린다
	 *  run()메서드의 모든 코드를 실행하고, 리턴하면 스레드는 종료된다.
	 * */
	public static void main(String[] args) {
		
		/** 
		 * */
		PrintThread work = new PrintThread();
		work.start();
		
		for(int num = 1; num < 30; num++) {
			try {
				Thread.sleep(100);
				if(num == 5) {
					System.out.println(num + "번째에서 주 스레드 일시정지");
					work.suspend(); 
				}
				
				/** 정지를 하게 되면 resume을 실행을 한다고 해도 스레드가 재시작이 되지 않는다. 
				 *  stop을 하더라도, 스레드의 생명주기를 정확히 control을할 수 없기 때문에
				 *  stop을 사용하는것은 권장하지 않는다.
				 * */
//				if(num == 10) {
//					System.out.println(num + "번째에서 주 스레드 정지");
//					work.stop();
//				}
				
				if(num == 25) {
					System.out.println(num + "번째에서 주 스레드 일시정지 재시작");
					work.resume();
				}
			} catch (Exception e) {
				;
			}
		}
	}
}

class PrintThread extends Thread {
	
	@Override
	public void run() {
		for(int num = 1; num <= 30; num++) {
			System.out.println(num + "번째 서브 스레드");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}

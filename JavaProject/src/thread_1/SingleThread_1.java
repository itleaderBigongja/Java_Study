package thread_1;

public class SingleThread_1 {

	/**
	 * @param args
	 * @throws InterruptedException
	 * (Single Thread)main 주 스레드 실행이 된다.
	 */
	public static void main(String[] args) throws InterruptedException {
		for(int num = 0; num < 30; num++) {
			System.out.println("O");
			Thread.sleep(num);
		}
		
		for(int num = 0; num < 30; num++) {
			System.out.println("X");
			Thread.sleep(num);
		}
	}
}

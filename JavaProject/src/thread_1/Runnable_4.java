package thread_1;

public class Runnable_4 {

	public static void main(String[] args) {
		
		/** Runnable을 익명 클래스를 생성하는 부분 */
		Thread work = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					// try문 안에 for문이 있는 것이 맞다.
					for(int num = 0; num < 30; num++) {
						System.out.println(num + "번째 서브 스레드 호출!!");
						Thread.sleep(100);
					}
				}catch (InterruptedException e){
					;
				}
			}
		});
		work.start();
		
		try {
			for(int num = 0; num < 30; num++) {
				System.out.println(num + "번째 메인 스레드 호출!!");
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			;
		}
	}
}

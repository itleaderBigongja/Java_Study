package Thread.thread_1;

public class Runnable_3 {

	public static void main(String[] args) {
		Thread work = new Thread(mRunnable);
		work.start();
		
		for(int num = 1; num <= 30; num++) {
			try {
				System.out.println(num + "번째 메인 스레드 호출!!");
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
	/** 익명 클래스로 Runnable 생성 
	 *  익명 클래스란?
	 *  클래스 이름 없이 객체를 생성하는 방식
	 *  익명 클래스는 Runnable의 run()를 구현하고, new Runnable(){...}형태로 객체가 생성되며
	 *  이 객체는 mRunnable 변수에 할당 됩니다.*/
	static Runnable mRunnable = new Runnable() {
		
		@Override
		public void run() {
			// 서브 스레드에서 실행할 코드
			for(int num = 1; num <= 30; num++) {
				try {
					System.out.println(num + "번째 서브 스레드 호출");
					Thread.sleep(200);
				} catch (InterruptedException e) {
					;
				}
			}
		}
	};
}

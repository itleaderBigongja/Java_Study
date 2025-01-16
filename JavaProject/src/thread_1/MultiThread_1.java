package thread_1;

import java.awt.Toolkit;

/** 여러 개의 스레드
 * 생성 가능한 스레드의 개수에는 물리적으로 제한은 없지만, 스레드 관리를 위한 정보와 호출 스택을 위해 
 * 메모리를 소모하기 때문에 무한히 생성할 수는 없다. 또 CPU의 처리 속도에도 한계가 있어
 * 너무 많이 만들면 전체적인 성능이 떨어진다. 그러나 적어도 문법적으로 제약이 없어 원하는 만큼 만들어도 상관없다.
 * 실제 서버 프로세스는 다중 사용자를 대기하기 위해 수백 개의 스레드를 생성하여 돌리기도 한다.
 * */
public class MultiThread_1 {
	
	public static void main(String[] args) {
		
		PrintThread worker_1 = new PrintThread('X');
		worker_1.start();
		PrintThread worker_2 = new PrintThread('O');
		worker_2.start();
		BeepThread worker_3 = new BeepThread(5, 1000);
		worker_3.start();
	}
}

class PrintThread extends Thread {
	
	char ch;
	public PrintThread(char ch) {
		this.ch = ch;
	}
	
	@Override
	public void run() {
		try {
			for(int num = 0; num < 30; num++) {
				System.out.println(num + "번째 서브 스레드 실행");
				System.out.println(ch);
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			;
		}
	}
}

/** BeepThread 
 *  소리를 출력하거나 특정 작업을 반복적으로 수행하는 스레드를 의미
 *  BeepThread의 목적
 *  ㅇ 컴퓨터의 시스템 비프음을 출력하거나, 일정 간격으로 작업을 수행하는 데모 스레드로 사용됩니다.
 *  ㅇ 특히, 멀티스레드 프로그래밍을 배우는 초급 과정에서 자주 사용되는 예제 입니다.
 */
class BeepThread extends Thread {
	
	int count;
	int gap;
	Toolkit tool = Toolkit.getDefaultToolkit();
	
	public BeepThread(int count, int gap) {
		this.count = count;
		this.gap = gap;
	}
	
	@Override
	public void run() {
		try {
			for(int num = 0; num < 30; num++) {
				tool.beep();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			;
		}
	}
}
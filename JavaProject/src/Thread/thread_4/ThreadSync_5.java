package Thread.thread_4;
/** 스레드 동기화( Thread Synchronization )
 *  여러 스레드가 동시에 하나의 공유 자원에 접근할 때, 발생할 수 있는
 *  데이터 불일치나 충돌 문제를 방지하기 위해 필요한 개념이다.
 *  동기화는 프로그램의 일관성을 유지하고, 여러 스레드가 안전하게 협력할 수 있도록 보장
 *  
 *  
 *  동기화가 필요한 이유
 *  1. 데이터 무결성 유지
 *  -> 여러 스레드가 동시에 동일한 자원(예:변수, 파일 등)을 수정하면
 *     예상치 못한 결과가 발생할 수 있습니다.
 *     
 *  2. 경쟁 조건(Race Condition)
 *  -> 두 개 이상의 스레드가 자원에 접근하는 순서에 따라 결과가 달라지는 상황입니다.
 *  
 *  3. 데드락(Deadlock)
 *  -> 두 스레드가 서로가 소유한 자원을 기다리며 영원히 멈추는 상황입니다.
 * */
public class ThreadSync_5 {

	public static void main(String[] args) {
		Memory_5 mem = new Memory_5(16);
		DownLoad_5 down = new DownLoad_5(mem);
		Play_5 play = new Play_5(mem);
		
		down.start();
		play.start();
	}
}

class Memory_5 {
	int[] buffer;
	int size;
	int progress;
	
	Memory_5(int asize) {
		size = asize;
		buffer = new int[asize];
		progress = 0;
	}
}

class Play_5 extends Thread{
	Memory_5 mem;
	
	Play_5(Memory_5 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		synchronized (mem) {
			try {
				mem.wait();
			} catch (InterruptedException e) {
				;
			}
		}
		System.out.println("플레이 시작!");
		for(int off = 0; off < mem.progress; off++) {
			System.out.print(mem.buffer[off] + " ");
		}
		System.out.println();
	}
}

class DownLoad_5 extends Thread{
	
	Memory_5 mem;
	DownLoad_5(Memory_5 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		System.out.println("다운로드 시작!");
		synchronized (mem) {
			// 현재 잠들어 있던 모든 스레드를 깨운다.
			// notify()는 동기화에서 부적절한 메소드입니다.
			// 이유 : 특정 어느것을 깨우는지 아무도 모르기 때문에 동기화에 부적합
			//       그렇기 때문에 깨울 때, 모두를 깨우는 notifyAll()가 동기화에 적합
			mem.notifyAll();
		}
		for(int off = 0; off < mem.size; off++) {
			mem.buffer[off] = off;
			mem.progress = off + 1;
			try {
				Thread.sleep(200);
				System.out.print(mem.progress + " ");
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}
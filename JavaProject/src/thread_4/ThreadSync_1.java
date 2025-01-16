package thread_4;

/** 스레드 통신(동기화)
 *  스레드는 보통 완전히 독립적이다. 그러나 때로는 연관된 작업을 분담하여 실행하는 경우가 있다.
 *  이때는 공유 데이터가 있고, 데이터를 적절히 간리하기 위해 서로 통신해야 한다.
 *  두 스레드가 공유 객체에 대한 참조를 통해 데이터를 주고 받으며 서로 작업 과정이나 현재 상황을 조사한다.
 *  
 *  사본이 전달이 되므로 공유 데이터로 사용할 수 없으며, 위치를 전달하는 참조형만 공유 데이터로 사용할 수 있다.
 * */
public class ThreadSync_1 {
	
	public static void main(String[] args) {
		Memory mem = new Memory(16);
		Download_2 down = new Download_2(mem);
		Play_2 play = new Play_2(mem);
		
		down.start();
		play.start();
	}
}

class Memory {
	int[] buffer;
	int size;
	int progress;
	
	Memory(int asize) {
		size = asize;
		buffer = new int[asize];
		progress = 0;
	}
}

class Download extends Thread {
	
	Memory mem;
	Download(Memory amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		
		for(int off = 0; off < mem.size; off++) {
			mem.buffer[off] = off;
			mem.progress = off + 1;
			
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
}

class Play extends Thread {
	
	Memory mem;
	Play(Memory amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		for(;;) {
			for(int off = 0; off < mem.progress; off++) {
				System.out.print(mem.buffer[off] +" ");
			}
			System.out.println();
			if(mem.progress == mem.size) break;
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}

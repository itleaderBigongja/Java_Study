package Thread.thread_4;

public class ThreadSync_3 {
	
	public static void main(String[] args) {
		Memory mem = new Memory(16);
		Download down = new Download(mem);
		Play play = new Play(mem);
		
		down.start();
		play.start();
	}
}

class Memory_3 {
	int[] buffer;
	int size;
	int progress;
	
	Memory_3(int asize) {
		size = asize;
		buffer = new int[asize];
		progress = 0;
	}
}

class Download_3 extends Thread {
	
	Memory_3 mem;
	public Download_3(Memory_3 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		
		for(int off = 0; off < mem.size; off += 2) {
			// 크리티컬 섹션
			// -> 두 스레드가 동시에 진입할 수 없는 영역( 공유객체 : Memory )
			synchronized (mem) {
				for(int chunk = 0; chunk < 2; chunk++) {
					mem.buffer[off+chunk] = off + chunk;
					mem.progress = off+chunk +1;
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						;
					}
				}
			}
		}
	}
}

class Play_3 extends Thread {
	
	Memory_3 mem;
	Play_3(Memory_3 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		for(;;) {
			/** 크리티컬 섹션
			 *  두 스레드 동시에 진입할 수 없는 영역( Memory : 공유객체 )
			 *  두 스레드 모두 mem 객체를 액세스 하는 부분을 크리티컬 섹션으로 둘러쌋다.
			 *  이렇게 하면 한 번에 한 스레드만 크리티컬 섹션으로 들어갈 수 있다.
			 *  다운로드 스레드가 실행중이라면 재생 스레드는 크리티컬 섹션으로 들어가지 못하며
			 *  다운로드 스레드가 크리티컬 섹션에서 빠져 나올때까지 기다린다.
			 */ 
			synchronized (mem) {
				for(int off = 0; off < mem.progress; off++) {
					System.out.println(mem.buffer[off] + " ");
				}
				System.out.println();
			}
			if(mem.progress == mem.size) break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				;
			}
		}
	}
}

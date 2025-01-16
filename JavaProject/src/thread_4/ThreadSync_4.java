package thread_4;
/** 동기화 스레드 캡슐화
 *  다운로드 받은 동작과 재생하는 동작은 모두 Memory 객체를 대상으로 한다. 그래서
 *  두 동작을 Memory 클래스 안의 메서드로 캡슐화하고, 스레드에서 공유 객체의 메서드를 호출하는 방법을 쓸 수 있다.
 * */
public class ThreadSync_4 {
	
	public static void main(String[] args) {
		Memory_4 mem = new Memory_4(16);
		DownLoad_4 down = new DownLoad_4(mem);
		Play_4 play = new Play_4(mem);
		
		down.start();
		play.start();
	}
}

// 공유객체
class Memory_4 {
	int[] buffer;
	int size;
	int progress;
	
	// 공유객체 생성자 호출
	Memory_4(int asize) {
		size = asize;
		buffer = new int[size];
		progress = 0;
	}
	
	// 공유 클래스의 DownChunk 메서드 캡슐화
	void DownChunk(int off) {
		for(int chunk = 0; chunk < 2; chunk++) {
			buffer[off+chunk] = off+chunk;
			progress = off + chunk + 1;
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				;
			}
		}
	}
	
	// 공유 클래스의 PlayDownload 메서드 캡슐화
	void PlayDownload() {
		for(int off = 0; off < progress; off++) {
			System.out.print(buffer[off] + " ");
		}
		System.out.println();
	}
}

class DownLoad_4 extends Thread{
	
	Memory_4 mem;
	
	DownLoad_4(Memory_4 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		for(int off = 0; off < mem.size; off += 2) {
			// 공유 객체에 있는 DownChunk()메서드 호출
			mem.DownChunk(off);
		}
	}
}

class Play_4 extends Thread {
	
	Memory_4 mem;
	Play_4(Memory_4 amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		for(;;) {
			// 공유 클래스 Memory에 있는 공유 메서드 PlayDownload() 호출
			mem.PlayDownload();
			if(mem.progress == mem.size) break;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO: handle exception
			}
		}
	}
}

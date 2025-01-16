package thread_4;

/** 스레드의 동기화
 *  스레드의 실행 순서나 시간은 전혀 예측할 수 없으며, 통제할 수도 없다.
 *  시스템 상황과 스케줄러의 판단에 따라 달라진다. 그러다 보니 데이터가 아직 준비되지 않은 상황인데
 *  스레드가 실행되어 엉뚱한 결과가 나오기도 한다.
 * */
public class ThreadSync_2 {
	
	public static void main(String[] args) {
		Memory mem = new Memory(16);
		Download down = new Download(mem);
		Play play = new Play(mem);
		
		down.start();
		play.start();
	}
}

class Memory_2 {
	int[] buffer;
	int size;
	int progress;
	
	Memory_2(int asize) {
		size = asize;
		buffer = new int[asize];
		progress = 0;
	}
}

class Download_2 extends Thread {
	
	/** 다운로드 스레드
	 *  다운로드 스레드의 코드가 조금 변경 되었는데 동영상을 1바이트씩 받지 않고,
	 *  한번에 2바이트씩 받는다. 동영상 같은 압축 파일은 내부에 블록이 나누어져 있으며
	 *  한 블록은 여러 개의 청크로 구성된다. 위 코드는 2바이트를 한 블록으로 가정하고
	 *  블록 단위로 다운로드 받는 것을 흉내 낸다.
	 *  완전히 동영상이 되려면메모리 용량으ㅡㄴ 짜==  
	 * */
	
	Memory mem;
	Download_2(Memory amem) {
		mem = amem;
	}
	
	@Override
	public void run() {
		
		for(int off = 0; off < mem.size; off += 2) {
			
			for(int chunk = 0; chunk < 2; chunk++) {
				mem.buffer[off + chunk] = off + chunk;                                                                                           
			}
		}
	}
}

class Play_2 extends Thread {
	
	Memory mem;
	Play_2(Memory amem) {
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
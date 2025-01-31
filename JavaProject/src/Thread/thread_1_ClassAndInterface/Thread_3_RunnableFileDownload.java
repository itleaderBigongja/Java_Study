package Thread.thread_1_ClassAndInterface;

import java.io.IOException;
import java.util.Random;

/** Runnable 인터페이스를 구현하여 파일 다운로드 시뮬레이션 구현 */
class FileDownloader implements Runnable {
	
	private String fileName;	// 다운로드할 파일 이름
	private int fileSize;		// 파일 크기(단위: KB)
	
	// 사용자 생성자: 파일 이름과 파일 크기를 초기화
	public FileDownloader(String fileName, int fileSize) {
		this.fileName = fileName;
		this.fileSize = fileSize;
	}
	
	public String getName() {
		return fileName;
	}
	
	// 스레드가 실행할 작업을 정의하는 run() 메서드
	@Override
	public void run() {
		Thread thread = Thread.currentThread();
		
		System.out.println(thread.getName() + ": " + fileName + "다운로드 시작...(크기: " + fileSize + ")");
		
		Random random = new Random();
		int downloadSize = 0;
		
		// 다운로드 시뮬레이션 하는 while문
		while(downloadSize < fileSize) {
			
			// 임의의 다운로드 속도(1 ~ 10 KB/s)
			int downloadSpeed = random.nextInt(10) + 1;
			downloadSize += downloadSpeed;
			// 다운로드 진행 상황을 출력합니다.
            System.out.println(thread.getName() + ": " + fileName + " 다운로드 중... (" + downloadSize + "/" + fileSize + "KB)");
            
			try {
				// 스레드를 잠시 멈추어 실제 다운로드처럼 보이게 한다.
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println(thread.getName() + ": " + fileName + " 다운로드 완료!");
	}
}
public class Thread_3_RunnableFileDownload{

	public static void main(String[] args) {
		  // 여러 개의 파일 다운로드를 시뮬레이션하기 위해 여러 FileDownloader 객체를 생성합니다.
	      FileDownloader file1 = new FileDownloader("Document.pdf", 50);
	      FileDownloader file2 = new FileDownloader("Image.jpg", 30);
	      FileDownloader file3 = new FileDownloader("Music.mp3", 80);
	
	       // 각 FileDownloader 객체를 사용하여 Thread 객체를 생성하고, 스레드 이름을 설정합니다.
	      Thread thread1 = new Thread(file1, "Downloader-1");
	      Thread thread2 = new Thread(file2, "Downloader-2");
	      Thread thread3 = new Thread(file3, "Downloader-3");
	
	      // 각 스레드를 시작합니다.
	      thread1.start();
	      thread2.start();
	      thread3.start();
	      
	      try {
			  thread1.join(); // thread1의 작업이 종료될때까지 기다립니다.
			  thread2.join(); // thread2의 작업이 종료될때까지 기다립니다.
			  thread3.join(); // thread3의 작업이 종료될때까지 기다립니다.
		  } catch (InterruptedException e) {
			  throw new RuntimeException(e);
		  }
	      
	      System.out.println("메인 주 스레드 종료");
	}
}

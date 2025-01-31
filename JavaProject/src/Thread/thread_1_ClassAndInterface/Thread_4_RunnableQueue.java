package Thread.thread_1_ClassAndInterface;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

// Runnable 인터페이스를 구현하여 작업을 처리하는 클래스
class TaskProcessor implements Runnable {

	private Queue<String> taskQueue;	// 작업 큐
	private Random random = new Random();
	
	// 생성자: 작업 큐를 초기화
	public TaskProcessor(Queue<String> taskQueue) {
		this.taskQueue = taskQueue;
	}
	
	// 스레드가 실행할 작업을 정의하는 run() 메서드
	@Override
	public void run() {
		
		Thread thread = Thread.currentThread();
		
		// 작업 큐에서 작업을 가져와 처리하는 반복문
		while(true) {
			String task = null;
			// 작업 큐에서 작업 가져오기(동기화 필요)
			synchronized (taskQueue) {
				if(!taskQueue.isEmpty()) {
					
					// 큐에서 작업 가져오 
					task = taskQueue.poll();
				}
			}
			
			if(task != null) {
				// 작업 처리 시뮬레이션
				System.out.println(thread.getName() + ": " + task + " 작업 처리 시작");
				
				try {
					Thread.sleep(random.nextInt(1000) + 500);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
				
				System.out.println(thread.getName() + ": " + task + " 작업 처리 완료");
			}else {
				// 작업 큐가 비어있으면 잠시 대기
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
}

public class Thread_4_RunnableQueue {
	
	public static void main(String[] args) {
		
		// 작업 큐 생성( 작업을 담을 큐를 생성 )
		Queue<String> taskQueue = new LinkedList<String>();
		
		// 작업 생성
		for(int i = 0; i < 10; i++) {
			// 작업 큐에 작업을 추가한다.
			taskQueue.offer("Task-" + i);
		}
		
		// TaskProcessor 객체 생성 및 스레드 생성
		TaskProcessor taskProcessor = new TaskProcessor(taskQueue);
		Thread thread1 = new Thread(taskProcessor, "Processor-1");
		Thread thread2 = new Thread(taskProcessor, "Processor-2");
		
		
		// 스레드 시작..
		thread1.start();
		thread2.start();
		
		try {
			thread1.join();
			thread2.join();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		
	}
}

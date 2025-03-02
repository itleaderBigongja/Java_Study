package Thread.thread_5_Executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

//import thread_5.ExecutorServiceExample_4.ThreadPoolWithForkJoinExample.SumTask;

/** 스레드 풀(ThreadPool) 작업 단위 세분화 예제
 *  스레드 풀로 생성한 스레드 단위에서 ForkJoinPool을 사용하여 세분화 작업을 진행할 예정 
 */
public class Executor_ForkJoinPool2 {
	
	// ForkJoinPool에서 실행할 작업 정의
	static class SumTask extends RecursiveTask<Integer> {

		private static final long serialVersionUID = 1L;
		
		private final int[] array;
		private final int start;
		private final int end;
		
		public SumTask(int[] array, int start, int end) {
			this.array = array;
			this.start = start;
			this.end = end;
		}
		
		// compute()메서드
		// RecursiveTask 또는 RecursiveAction 클래스에서 실제 스레드에서 처리할 작업의 논리를 정의하는 메서드
		// 이 메서드는 분할된 작업이 수행해야 할 작업의 내용을 구현합니다.
		
		/** compute() 메서드
		 *  동기적 작업 실행
		 *  ㅇ compute() 메서드는 작업을 동기적으로 실행합니다.
		 *  ㅇ 작은 작업(분할이 필요 없는 경우)을 처리하거나, 작업을 더 세분화(fork)할 때 사용됩니다.
		 *  
		 *  직접 호출 가능
		 *  ㅇ 작업 분할이 필요 없는 경우, compute()를 호출해 동기적으로 작업을 수행합니다.
		 **/
		@Override
		protected Integer compute() {
			if(end - start <= 5) { // 작은 작업이면 직접 계산
				System.out.println("compute() 동기적 계산 시작");
				System.out.println("시작 값 : " + start + "마지막 값 : " + end);
				int sum = 0;
				for(int i = start; i < end; i++) {
					sum += array[i];
				}
				
				return sum;
			}
			else { // 큰 작업이면 분할
				System.out.println("fork() 세분화 비동기 계산 시작");
				System.out.println("시작 값 : " + start + "마지막 값 : " + end);
				int mid = (start + end) / 2;
				SumTask leftTask = new SumTask(array, start, mid);	// 배열에서 첫 번째 부터 가운데까지의 연산
				SumTask rightTask = new SumTask(array, mid, end);   // 배열에서 가운데 이상부터 마지막까지 연산
				
				// fork()는 Fork/Join Framework에서 작업(Task)을 다른 스레드에 비동기적으로
				// 실행되도록 제출하는 메서드입니다. 이 메서드를 호출하면 작업이 작업 큐(Queue)에 추가되어
				// 다른 작업 스레드가 이를 가져가 실행할 수 있습니다.
				
				/** fork() 메서드 
				 *  비동기 작업 분할
				 *  ㅇ 현재 작업을 병렬로 실행할 수 있도록 작업을 큐(Queue)에 추가
				 *  ㅇ 즉시, 실행되지 않을 수 있으며, Fork/Join Framework의 work-stealing 알고리즘에 따라 실행
				 *  
				 *  병렬 실행 가능
				 *  ㅇ 작업을 비동기적으로 분할하여 여러 CPU 코어에서 실행할 수 있게 최적화.
				 */
				leftTask.fork();						// 왼쪽 작업 분할
				int rightResult = rightTask.compute();	// 오른쪽 작업 실행
				int leftResult = leftTask.join();		// 왼쪽 작업 실행
				
				
				// 왼쪽에서 작업한 데이터와 오른쪽에서 작업한 테이블 병합
				return leftResult + rightResult;
			}
		}
	}
	
	public static void main(String[] args) {
		// Executors.newFixedThreadPool(3)으로 3개의 스레드가 있는 스레드 풀 생성
		ExecutorService threadPool = Executors.newFixedThreadPool(3);
		
		// 작업할 배열 생성
		int[] array = new int[20];
		
		for(int i = 1; i < array.length; i++) {
			array[i] = i;
		}
		
		// ThreadPoolExecutor의 스레드에서 ForkJoinPool 실행
		for(int i = 1; i <= 3; i++) {
			
			int threadId = i;
			
			// submit() : 자바에서 스레드 풀에 작업을 제출하는 데 사용됩니다.
			//            이 메서드는 실행할 작업을 스레드 풀 큐(Queue)에 추가하고, 작업이 실행 되도록
			//			  스레드를 할당합니다. 주로 Runnable이나 Callable 인터페이스를 사용하여 작업을 제출합니다.
			threadPool.submit(() -> {
				
				System.out.println("ThreadPoolExecutor 스레드 ID : " + threadId + "에서 ForkJoinPool 시작");
				
				ForkJoinPool forkJoinPool = new ForkJoinPool();
				SumTask task = new SumTask(array,			// 데이터 유형 
											   0, 			// 시작 위치 : 배열의 0번째 인덱스
											   array.length	// 종료 위치 : 배열의 마지막 인덱스
										  );
				
				// invoke(): invoke() 메서드는 ForkJoin 작업을 동기적으로 실행하고 결과를 반환
				//           RecursiveTask 또는 RecursiveAction과 같은 작업을
				//			 ForkJoinPool에 제출하고, 그 결과를 즉시 반환받고 싶을 때 사용
				
				/** invoke 동기적 실행
				 *  ㅇ invoke()는 작업이 완료될 때까지 호출한 스레드를 블록(block)합니다.
				 *  ㅇ 작업이 완료되면 결과를 반환하거나, 예외가 발생한 경우 예외를 던집니다.
				 *  ㅇ 내부적으로 작업-도난(work-stealing) 알고리즘을 사용하여 작업을 병렬로 처리
				 *  ㅇ 큰 작업을 작은 작업으로 나누고(fork), 각 결과를 병합(join)하여 최종 결과를 생성
				 *  ㅇ 예외처리 : 작업 중 예외가 발생하면 'RuntimeException'으로 래핑되어 호출자에게 전달
				 **/ 
				int result = 0;
				try {
					result = forkJoinPool.invoke(task);
					System.out.println("Thread ID : " + threadId + ". ForkJoinPool 결과: " + result);
				} catch (RuntimeException e) {
					e.printStackTrace(); 
				}
			});
		}
		
		// ThreadPoolExecutor 종료
		try {
			threadPool.awaitTermination(1, TimeUnit.MINUTES);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			threadPool.shutdown();
		}
	}
}

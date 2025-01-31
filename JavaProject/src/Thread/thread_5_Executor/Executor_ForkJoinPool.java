package Thread.thread_5_Executor;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Executor_ForkJoinPool {

	public static void main(String[] args) {
		
		// ForkJoinPool 예제
		// ForkJoinPool을 사용하여 병렬 계산 작업을 효율적으로 처리.
		// RecursiveTask를 구현하여 재귀적으로 작업을 분할(fork)하고 병합(join).
		ForkJoinPool forkJoinPool = new ForkJoinPool(4);	// 병렬 레벨 4로 설정
		ForkJoinTask<Integer> sumTask = forkJoinPool.submit(new RecursiveSumTask(1, 100));
		
		try {
			System.out.println("ForkJoinPool Result : " + sumTask.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			forkJoinPool.shutdown();
		}
	}

}

// Fork/Join Framework를 활용한 RecursiveTask 구현
// RecursiveSumTask :
// ㅇ 작은 범위의 합계는 직접 계산.
// ㅇ 큰 범위는 작업을 분할하여 병렬로 처리.
class RecursiveSumTask extends RecursiveTask<Integer> {
	
	private static final long serialVersionUID = 1L;
	private final int start;
	private final int end;
	
	public RecursiveSumTask(int start, int end) {
		this.start = start;
		this.end = end;
	}
	
	@Override
	protected Integer compute() {
		if(end - start <= 10) {
			return calculateSum();
		}else { // 큰 작업은 분할
			int mid = (start + end) / 2;
			RecursiveSumTask leftTask = new RecursiveSumTask(start, mid);
			RecursiveSumTask rightTask = new RecursiveSumTask(mid + 1, end);
			
			leftTask.fork();	// 왼쪽 작업 비동기 실행
			
			int rightResult = rightTask.compute(); // 오른쪽 작업 동기 실행
			int leftResult = leftTask.join();	// 왼쪽 작업 결과 대기
			
			return leftResult + rightResult;
		}
	}

	private Integer calculateSum() {
		
		int sum = 0;
		for(int i = start; i <= end; i++) {
			sum += 1;
		}
		return sum;
	}
	
}
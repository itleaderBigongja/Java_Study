package Thread.thread_5;

import java.util.Arrays;
import java.util.List;

public class Excutor_ParallelStream {

	public static void main(String[] args) {
		// 3. Parallel Stream을 이용한 병렬 작업
		// ㅇ 리스트를 병렬로 처리하여 각 요소에 대해 동시 작업을 수행합니다.
		// ㅇ 실행 원리 : 내부적으로 Fork/Join Framework를 사용해 작업을 병렬로 나눕니다.
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		numbers.parallelStream().forEach(number -> {
			System.out.println("Processing number" + number + "on " + Thread.currentThread().getName());
			try {
				Thread.sleep(1000);	// 작업 시뮬레이션
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		});
	}
}

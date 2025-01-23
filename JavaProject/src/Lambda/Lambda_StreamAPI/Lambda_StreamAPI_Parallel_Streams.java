package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;

// 병렬 스트림(Parallel Streams)
public class Lambda_StreamAPI_Parallel_Streams {

	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

		// [ 순차 스트림으로 합계 계산 및 시간 측정 ]
		long startTime1 = System.currentTimeMillis();				// 시작 시간 기록
		long sum1       = numbers.stream().reduce(0, Integer::sum);	// 순차 스트림 생성 후, reduce 메서드로 합계 계산
		long endTime1   = System.currentTimeMillis();				// 마지막 시간 기록
		/** reduce() 
		 *  매개변수(1) : 초기값(identity) 여기서는 0이 사용, 합계를 계산하므로 초기값을 0으로 설정
		 *  매개변수(2) : Integer::sum은 (a, b) -> a + b 와 동일합니다.
		 *  스트림의 첫 번째 요소와 초기값을 더하고, 그 결과를 다음 요소와 더해나가는 방식으로 모든 요소를 합산
		 *  즉, 스트림의 모든 숫자를 순차적으로 더한다.
		 * */
		// 결과 및 실행 시간 출력
		System.out.println("sum1 : " + sum1 + "\ntime : " + (endTime1 - startTime1)+"초");
		
		
		// 병렬 스트림으로 합계 계산 및 시간 측정
		long startTime2 = System.currentTimeMillis();						// 시작 시간 기록
		long sum2		= numbers.parallelStream().reduce(0, Integer::sum); // 병렬 스트림 생성
		long endTime2   = System.currentTimeMillis();						// 마지막 시간 기록
		/** parallelStream() 메서드는 컬렉션을 병렬 스트림으로 변환
		 *  기능 : 스트림의 각 요소들을 여러 개의 쓰레드에서 동시에 처리할 수 있게 합니다.
		 *        즉, 요소들을 여러 개의 부분으로 나누어 각 부분을 다른 쓰레드에서 병렬로 처리하고, 마지막에 결과를 합칩니다.
		 *        
		 *  reduce() 메서드는 병렬 스트림에서도 동일하게 사용되지만, 병렬로 계산한 부분 결과를 결합하는 과정을 포함
		 *  reduce() 메서드는 내부적으로 여러 쓰레드에서 부분적으로 계산한 결과를 합쳐서 최종 결과를 만든다.
		 *  병렬 스트림은 멀티 코어 환경에서 성능 향상을 가져올 수 있다.
		 * */
		System.out.println("sum2 : " + sum2 + "\ntime :" + (endTime2 - startTime2)+"초");
	}
}

package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Lambda_StreamAPI_LimitAndSkip {

	public static void main(String[] args) {
		
		// 정수 리스트 생성
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
		// 스트림 API를 사용하여 처음 5개의 요소 추출
		List<Integer> limitedNumbers 
			= numbers.stream()
					  // limit() 메서드를 사용하여 처음 5개의 요소만 추출
					  /** limit() 메서드는 스트림의 요소를 지정된 개수만큼 제한하는 메서드
					   *  기능 : 스트림에서 지정된 개수만큼의 요소를 가져와 새로운 스트림을 생성
					   *  매개변수 : 추출할 요소의 개수( long 타입 )을 받는다.
					   * */
					 .limit(5)
					 .collect(Collectors.toList());
		System.out.println(limitedNumbers);
		
		
		// 스트림 API를 사용하여 5개의 요소를 건너뛰고 나머지 요소를 추출
		List<Integer> skipedNumbers
			= numbers.stream()
					  /** skip() 메서드를 사용하여 지정된 개수만큼 건너뛰는 메서드
					   *  기능 : 스트림에서 지정된 개수만큼의 요소를 건너뛴 후, 나머지 요소를 포함하는 새로운 스트림을 생성
					   *  매개변수 : 건너뛸 요소의 개수( long 타입 )을 받습니다.
					   * */
					 .skip(5)
					 .collect(Collectors.toList());
		System.out.println(skipedNumbers);
	}
}

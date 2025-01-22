package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;

// 중간 연산 과정에서 요소를 확인 또는 특정 작업을 수행
public class Lambda_StreamAPI_peek {

	public static void main(String[] args) {
		
		// 스트림 API를 사용하여 중간 연산 과정 확인
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		
		/** peek() 메서드는 스트림의 요소를 소비하지 않고, 중간 연산 과정에서 요소를 확인하거나 특정 작업을 수행하는 메서드
		 *  - 기능 : 스트림의 요소에 영향을 주지 않으면서 요소를 검사하거나, 디버깅, 로깅, 등에 사용됩니다.
		 *  - 매개변수 : Comsumer<T> 인터페이스를 구현한 객체를 매개변수로 받는다.
		 *  
		 *  - 람다 표현식 n -> System.out.println("Mapped: " + n)는 Consumer<Integer> 인터페이스를 구현한 익명 객체입니다.
		 *  - 람다 표현식에서 n은 스트림의 각 요소 (매핑된 값)를 의미하고, System.out.println("Mapped: " + n)는 콘솔에 매핑된 요소를 출력하는 동작을 수행합니다.
		 * */
		numbers.stream()
				// 2의 배수만 필터링 된다.
			   .filter(number -> number % 2 == 0)
			    // 필터링된 요소 출력
			   .peek(number -> System.out.println("Filterd : " + number))
			    // 각 요소를 2배로 매핑
			   .map(number -> number * 2)
			   // 매핑된 요소를 출력
			   .peek(number -> System.out.println("Mapped: " + number))
			   .forEach(number -> System.out.println("Final : " + number + "\n"));
	}
}

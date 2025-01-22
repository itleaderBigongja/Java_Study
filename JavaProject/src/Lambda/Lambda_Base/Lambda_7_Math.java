package Lambda.Lambda_Base;

import java.util.Arrays;
import java.util.List;

// anyMath, allMath, noneMath(매칭)
public class Lambda_7_Math {
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1,2,3,4,5);
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		checkMathes(numbers);
		
	}
	
	public static void checkMathes(List<Integer> numbers) {
		
		// anyMath : 람다  표현식을 만족하는 요소가 하나라도 있는지 확인
		/** anyMath() 메서드는 스트림의 요소 중 주어진 조건을 만족하는 요소가 하나라도 있는지 확인하는 메서드
		 *  기능 : 스트림의 요소 중 하나라도 주어진 람다 표현식 조건을 만족하면 true를 반환하고 만족하는 요소가 하나라도 없으면 false를 반환
		 *  매개변수 : Predicate<T> 인터페이스를 구현한 객체를 매개변수로 받습니다. 여기서 T는 스트림의 요소 타입
		 *  Predicate<T> 인터페이스는 test(T t)메서드를 가지고 있으며, 이 메서드는 인자로 전달된 요소를 검사하여 true 또는 false를 반환
		 *  
		 *  람다 표현식 : n -> n % 2 == 0는 Predicate<Integer> 인터페이스를 구현한 익명 객체
		 *  람다 표현식에서 n는 스트림의 각 요소를 의미하고, n % 2 == 0는 해당 요소가 짝수인지 검사하는 조건을 의미
		 */
		boolean anyEven = numbers.stream()
						 .anyMatch(n -> n % 2 == 0);
		System.out.println("anyMath() 2의 배수가 하나라도 존재 여부 확인 : " + anyEven);
		
		
		// allPositive : 람다 표현식을 모든 요소가 만족하는지 확인
		/** allMath() 메서드는 스트림의 모든 요소가 주어진 조건을 만족하는지 확인하는 메서드
		 *  - 기능 : 스트림의 모든 요소가 주어진 람다 표현식 조건을 만족하면 true를 반환하고, 하나라도 만족하지 않으면 false를 반환
		 *  - 매개변수 : Predicate<T> 인터페이스를 구현한 익명 객체
		 *  
		 *  - 람다 표현식 : n -> n > 0는 Predicate<Integer> 인터페이스를 구현한 익명 객체
		 *  - 람다 표현식에서 n는 스트림의 각 요소를 의미하고, n > 0는 해당 요소가 양수인지 검사하는 조건을 의미
		 */
		boolean allPositive = numbers.stream()
							 .allMatch(n -> n > 0);
		System.out.println("allMath() 모두 양수인가? " + allPositive);
		
		
		// noneMath : 람다 표현식을 만족하는 요소가 없는지 확인
		/** noneMath() 메서드는 스트림의 모든 요소가 주어진 조건을 만족하지 않는지 확인하는 메서드
		 *  - 기능 : 스트림의 모든 요소가 주어진 람다 표현식 조건을 만족하지 않으면 true를 반환하고, 하나라도 만족하면 false를 반환
		 *  - 매개변수 : Predicate<T> 인터페이스를 구현한 객체를 매개변수로 받습니다. 여기서 T는 스트림의 요소 타입
		 *  
		 *  - 람다 표현식 n -> n < 0는 Predicate<Integer> 인터페이스를 구현한 객체
		 *  - 람다 표현식에서 n는 스트림의 각 요소를 의미하고, n < 0는 해당 요소가 음수인지 검사하는 조건
		 * */
		boolean noneNegative = numbers.stream()
							  .noneMatch(n -> n < 0);
	}
}

package Lambda;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// reduce 메서드는 스트림의 요소들을 하나의 결과값으로 축소 하는 데 사용( 숫자의 경우 합계, 문자열의 경우 연결 )
public class Lambda_5_reduce {
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		sumNumbersBeforeLambda(numbers);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		sumNumbersAfterLambda(numbers);
	}
	
	// 람다 표현식 사용 전: reduce 메서드 사용
	public static void sumNumbersBeforeLambda(List<Integer> numbers) {
		
		int sumNumber = 0;
		
		for(int number : numbers) {
			sumNumber += number;
		}
		System.out.println("총합 : " + sumNumber);
	}
	
	// 람다 표현식 사용 후: reduce 메서드 사용
	public static void sumNumbersAfterLambda(List<Integer> numbers) {
		/** reduce() 메서드 : 스트림의 요소들을 하나의 결과값으로 축소하는 메서드
		 *  - 기능 : 스트림의 요소들을 주어진 연산(람다 표현식)을 사용하여 하나의 값으로 합친다.
		 *  - 매개변수 : BinaryOperator<T> 인터페이스를 구현한 객체를 매개변수로 받는다.
		 *  - BinaryOperator<T> 인터페이스는 apply(T t1, T t2)메서드를 가지고 있으며, 
		 *                      이 메서드는 두 요소를 결합하여 결과값을 반환
		 *  - 람다 표현식 : (a, b) -> a + b는 BinaryOperator<Integer> 인터페이스를 구현한 익명 객체
		 *  - 람다 표현식에서 a, b는 스트림의 두 요소를 의미하고, a + b는 두 숫자를 더하는 연산을 의미
		 *  - reduce() 메서드는 비어있는 스트림인 경우 연산 결과가 없으므로 Optional을 반환 
		 * */
		Optional<Integer> sumNumber = numbers.stream()
									  .reduce((num1, num2) -> num1 + num2);
		
		/** Optional.orElse() 메서드는 Optional 객체에 값이 존재하면 해당 값을 반환하고, 값이 없으면 인자로 전달된 값을 반환
		 *  - 기능 Optional 객체의 값의 유무를 확인하고, 값이 없을 때 기본 값을 제공할 수 있도록 한다.
		 *   
		 * */
		System.out.println("숫자 합계(reduce) : " + sumNumber.orElse(0) );
	}
}

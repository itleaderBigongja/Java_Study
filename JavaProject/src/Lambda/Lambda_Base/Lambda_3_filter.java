package Lambda.Lambda_Base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// Filter : 특정 조건을 만족하는 리스트 요소만 추출하는 데 람다 표현식을 사용
public class Lambda_3_filter {
	
	public static void main(String[] args) {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		filterNumbersBeforeLambda(numbers);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		filterNumbersAfterLambda(numbers);
	}
	
	// 람다 표현식 사용 전 : for 루프와 조건문 사용
	public static void filterNumbersBeforeLambda(List<Integer> numbers) {
		List<Integer> evenNumbers = new ArrayList<Integer>();
		
		for(int number : numbers) {
			if(number % 2 == 0) {
				evenNumbers.add(number);
			}
		}
		System.out.println("2의 배수 : " + evenNumbers);
	}
	
	// 람다 표현식 사용 후 : stream, filter, collect 메서드 사용
	public static void filterNumbersAfterLambda(List<Integer> numbers) {
		/** numbers.stream() -> 컬렉션 객체였던 List<Integer> numbers를 Stream 객체로 변환하는 메서드
		 *  - 기능 : 스트림 API를 사용하여 데이터를 처리할 수 있게 해준다.
		 *  - Stream<T> 객체는 데이터를 처리하는 데 사용되는 일련의 연산을 제공
		 *  - 스트림은 원본 데이터를 변경하지 않고, 새로운 스트림을 생성
		 *  - 스트림은 데이터를 순차적으로 처리하거나 병렬로 처리할 수 있다.
		 * */
		
		/** stream().filter() -> 람다 표현식을 사용하여 스트림의 요소(number) 필터링하는 메서드
		 *  - 기능 : 주어진 람다 표현식의 조건을 만족하는 요소만으로 새로운 스트림을 생성
		 *  - 매개변수 : Predicate<T> 인터페이스를 구현한 객체를 매개변수로 받는다. 여기서 T는 스트림의 요소(number) 타입(Int)이다.
		 *  - Predicate<T> 인터페이스는 test(T t) 메서드를 가지고 있으며, 이 메서드는 인자로 전달된 요소를 검사하여
		 *                 true 또는 false를 반환
		 *  - 람다 표현식 number -> number % 2 == 0는 predicate<Integer> 인터페이스를 구현한 객체
		 *  - 람다 표현식에서 number는 스트림의 각 요소를 의미하고, number % 2 == 0는 해당 요소가 짝수인지 검사하는 조건을 의미
		 */
		
		/** collect() 메서드는 특정 자료형으로 수집하는 메서드
		 *  - 기능 : 스트림의 요소들을 컬렉션(List, Set, 등)으로 변환하거나, 문자열로 연결하는 등 다양한 방식으로 요소를 수집
		 *  - 매개변수 : Collector 인터페이스를 구현한 객체를 매개변수로 받는다.
		 *  - Collector.toList()는 스트림의 요소(number)들을 List로 변환하는 Collector 객체입니다.
		 * */
		List<Integer> evenNumbers = numbers.stream()
										   .filter(number -> number % 2 == 0)
										   .collect(Collectors.toList());
		System.out.println("2의 배수 : " + evenNumbers);
	}
}

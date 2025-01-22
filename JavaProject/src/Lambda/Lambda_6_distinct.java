package Lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// distinct : 중복 제거
public class Lambda_6_distinct {

	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(32, 29, 29, 29, 29, 29, 35, 35, 39, 50);
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		removeDuplicatesBeforeLambda(numbers);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		removeDuplicatesAfterLambda(numbers);
	}
	
	// 람다 표현식 사용 전 : Set 사용 (중복 제거)
	public static void removeDuplicatesBeforeLambda(List<Integer> numbers) {
		
		// HashSet으로 중복 제거( 정렬이 되지 않음 )
		java.util.HashSet<Integer> distinctNumbers = new java.util.HashSet<Integer>(numbers);
		System.out.println("중복 제거 (Set)" + distinctNumbers);
	}
	
	// 람다 표현식 사용 후 : distinct 메서드 사용
	public static void removeDuplicatesAfterLambda(List<Integer> numbers) {
		
		// Stream의 distinct 메서드로 중복 제거
		/** .distinct() 메서드는 스트림에서 중복된 요소를 제거하는 메서드 입니다.
		 *  - 기능 : 스트림의 요소들을 비교하여 중복된 요소를 제거하고, 중복되지 않은 요소로 새로운 스트림을 생성
		 *  - 매개변수 : Comparator<T> 인터페이스를 구현한 객체를 매개변수로 받지 않는다. 
		 *  - 동일성을 비교할 때 객체의 equals() 메서드를 사용
		 *  - 정렬 기준 : sorted() 정렬 후, distinct()로 중복을 제거 한다고 하여도 정렬이 된다는 가정을 하면 안된다.
		 *              distinct()로 중복을 제거한 다음 sorted()로 정렬을 해주어야 확실한 정렬을 했다고 할 수 있다.
		 *  - 중복 제거된 스트림을 List로 변환
		 */
		List<Integer> distinctNumbers = numbers.stream()
										.distinct().sorted()
										.collect(Collectors.toList());
		System.out.println("중복 제거(distinct) : " + distinctNumbers);
	}
}

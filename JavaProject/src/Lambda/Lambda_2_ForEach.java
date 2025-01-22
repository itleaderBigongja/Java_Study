package Lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda_2_ForEach {
	
	public static void main(String[] args) {
		
		List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry", "Date");
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		printFruitsBeforeLambda(fruits);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		printFruitsAfterLambda(fruits);
	}
	
	// 람다 표현식 사용 전 : for 루프 사용
	public static void printFruitsBeforeLambda(List<String> fruits) {
		for(String fruit : fruits) {
			System.out.println("과열 : " + fruit);
		}
	}
	
	// 람다 표현식 사용 후 : forEach 루프 사용
	public static void printFruitsAfterLambda(List<String> fruits) {
		
		/** fruits.forEach()는 List 인터페이스에서 제공하는 메서드
		 *  기능 : 리스트의 모든 요소를 순회 하면서 특정 동작을 수행
		 *  매개변수 : Consumer<T> 인터페이스를 구현한 객체를 매개변수로 받는다. 여기서 T는 리스트의 요소 타입
		 *  람다 표현식 : fruit -> System.out.println("과일 명 : " + fruit)는
		 *  		   Consumer<String> 인터페이스를 구현한 익명 객체
		 */
		
		fruits.forEach(fruit -> System.out.println("과일 명 : " + fruit));
		// fruit 변수는 Object형이며 모든 변수의 타입의 최상위라서 다 포용할 수 있다.
	}
}

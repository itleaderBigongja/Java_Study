package Lambda;

import java.util.Arrays;
import java.util.List;

public class Lambda_2 {
	
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
		// fruit 변수는 Object형이며 모든 변수의 타입의 최상위라서 다 포용할 수 있다.
		fruits.forEach(fruit -> System.out.println("과일 명 : " + fruit));
	}
}

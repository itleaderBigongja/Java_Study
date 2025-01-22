package Lambda.Lambda_Base;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** 람다 표현식의 기본 개념
 *  람다 표현식은 익명 함수를 만드는 방법 -> 기존의 자바에서 메서드를 정의하려면 클래스 내부에 있어야 했지만,
 *  람다 표현식을 사용하면 메서드처럼 동작하는 코드를 간결하게 표현할 수 있음
 * */
public class Lambda_1_Comparator {
	/** ㅇ 익명함수( Anonymous Function
	 *  -> 이름이 없는 함수, 즉, 함수를 정의하면서 함수 이름을 지정하지 않습니다.
	 *  ㅇ 함수형 인터페이스( Functional Interface )
	 *  -> 람다 표현식을 사용할 수 있는 인터페이스, 함수형 인터페이스는 단 하나의 추상 메서드를 가지고 있어야 함
	 *     @FunctionalInterface 어노테이션을 사용하여 명시적으로 함수형 인터페이스임을 나타낼수 있음
	 * */
	public static void main(String[] args) {
		
		List<String> names = Arrays.asList("사용자_1", "사용자_2", "사용자_3", "사용자_4", "사용자_5");
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		sortBeforeLambda(names);
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 후 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		sortAfterLambda(names);
	}
	
	// 람다 표현식 사용 전 : Comparator 익명 클래스 사용
	public static void sortBeforeLambda(List<String> names) {
		
		// Collections.sort 메서드를 이용하여 리스트를 정렬 합니다.
		Collections.sort(names, new Comparator<String>() {

			// Comparator 인터페이스를 익명 클래스로 구현하여 compare 메서드를 Override(재정의)합니다.
			// Compare 메서드에서 문자열을 사전순으로 비교하여 정렬합니다.
			@Override
			public int compare(String s1, String s2) {
				return s1.compareTo(s2);
			}
		});
		System.out.println("람다 사용 전 : 정렬된 이름 리스트 : " + names + "\n");
	}
	
	// 람다 표현식 사용 후 : Comparator 구현을 람다 표현식으로 대체
	public static void sortAfterLambda(List<String> names) {
		Collections.sort(names, (s1, s2) -> s1.compareTo(s2)); // 람다 표현식 사용
		System.out.println("람다 사용 후 : 정렬된 이름 리스트 : " + names);
	}
}

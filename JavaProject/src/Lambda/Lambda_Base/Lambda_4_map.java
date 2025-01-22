package Lambda.Lambda_Base;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// map : 리스트의 각 요소를 다른 형태로 변환하는 데 람다 표현식을 map을 사용하는 방법
public class Lambda_4_map {

	public static void main(String[] args) {
	
		List<String> words = Arrays.asList("woogeun", "woohyeun", "woojong", "taewoo", "yonggil", "junhang");
		
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		transforWordsBeforeLambda(words);
		System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ 람다 표현식을 사용 하기 전 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		transforWordsAfterLambda(words);
	}
	
	// 람다 표현식 사용 전 : for 루프와 새 리스트 생성
	public static void transforWordsBeforeLambda(List<String> words) {
		
		List<String> upperCaseWords = new ArrayList<String>();
		for(String word : words) {
			upperCaseWords.add(word.toUpperCase());
		}
		System.out.println("대문자로 치환 : " + upperCaseWords);
	}
	
	// 람다 표현식 사용 후 : stream, map, collect 메서드 사용
	public static void transforWordsAfterLambda(List<String> words) {
		
		/** words.stream()는 List<String> words : 람다 표현식을 사용하여 스트림의 각 요소를 변환
		 *  
		 *  stream().map()
		 *  기능 - 주어진 람다 표현식을 사용하여 스트림의 각 요소를 다른 값으로 변환하고, 변환된 요소 
		 *  매개변수 : Function<T, R> 인터페이스를 구현한 객체를 매개변수로 받는다. 여기서 T는 스트림의 요소 타입이고, R은 변환될 요소 타입
		 *  Function<T, R> 인터페이스는 apply(T t) 메서드를 가지고 있으며, 이 메서드는 인자로 전달된 요소를 변환하여 결과값을 반환
		 *  람다 표현식 word -> word.toUpperCase()는 Function<String, String> 인터페이스를 구현한 익명 객체
		 *  람다 표현식에서 word는 스트림의 각 요소를 의미, word.toUpperCase()는 해당 요소를 대문자로 변환하는 동작을 수행
		 *  */
		
		/** collect() 메서드는 특정 자료형으로 수집하는 메서드
		 *  - 기능 : 스트림의 요소들을 컬렉션(List, Set, 등)으로 변환하거나, 문자열로 연결하는 등 다양한 방식으로 요소를 수집
		 *  - 매개변수 : Collector 인터페이스를 구현한 객체를 매개변수로 받는다.
		 *  - Collectors.toList()는 스트림의 요소들을 List로 변환하는 Collector 객체
		 * */
		List<String> upperCaseWords = words.stream()
									  .map(word -> word.toUpperCase())
									  .collect(Collectors.toList());
		System.out.println("대문자로 치환 : " + upperCaseWords);
	}
	
}

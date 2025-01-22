package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// 문자열 리스트에서 단어 추출
public class Lambda_StreamAPI_flatMap {

	public static void main(String[] args) {
		
		List<String> sentences = Arrays.asList("Hello world", "Java Stream API", "Lambda expressions");
		
		/** flatMap() 메서드는 스트림의 각 요소를 다른 스트림으로 변환한 후, 이 모든 스트림을 하나의 스트림으로 병합하는 메서드
		 *  - 기능 : 중첩된 스트림 구조를 평탄화(flatten)하여 단일 스트림으로 만들 때 사용됩니다.
		 *  - 매개변수 : Function<T, Stream<R>> 인터페이스를 구현한 객체를 매개변수로 받습니다.
		 *             여기서 T는 스트림의 요소 타입이고, R은 변환될 스트림의 요소 타입
		 *             
		 *  - 람다식 표현 sentence -> Stream.of(sentence.split(" "))는 Function<String, Stream<String>> 인터페이스를 구현한 익명 객체
		 *  - 람다 표현식에서 sentence는 스트림의 각 문장을 의미
		 *  - sentence.split(" ")는 문장을 공백을 기준으로 나누어 단어 배열을 생성합니다.
		 *  - Stream.of()는 단어 배열을 스트림으로 변환 합니다.
		 * */
		List<String> words = sentences.stream()
									  .flatMap(sentence -> Stream.of(sentence.split(" "))) // 문장을 단어 하나 하나를 스트림으로 변환
									  .collect(Collectors.toList());
		System.out.println(words);
	}
}

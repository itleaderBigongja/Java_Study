package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// 문자열 리스트를 첫 글자로 그룹화
public class Lambda_StreamAPI_groupingBy_1 {
	
	public static void main(String[] args) {
		
		List<String> names = Arrays.asList("Alice", "Bob", "David", "Amy", "Alex");
		
		// 스트림 API를 사용하여 문자열 리스트를 첫 글자로 그룹화
		Map<Character, List<String>> groupedNamed = 
				 // groupingBy를 사용하여 첫 글자를 기준으로 그룹화 작업을 수행
				/** groupingBy() 메서드는 스트림의 요소들을 주어진 기준에 따라 그룹화하여 Map으로 수집하는 메서드
				 *  - 기능 : 스트림의 요소를 특정 속성이나 기준으로 그룹화하고, 그룹화된 결과를 Map으로 반환
				 *  - 매개변수: Function<T, K> 인터페이스를 구현한 객체를 매개변수로 받습니다. 여기서 T는 스트림의 요소 타입이고, K는 그룹의 키 타입입니다.
                    - 람다 표현식 name -> name.charAt(0)는 Function<String, Character> 인터페이스를 구현한 익명 객체입니다.
                    - 람다 표현식에서 name은 스트림의 각 문자열 요소를 의미하고, name.charAt(0)는 문자열의 첫 번째 문자를 가져오는 동작을 수행
				 * */
				 names.stream().collect(Collectors.groupingBy(name -> name.charAt(0)));
				
		System.out.println(groupedNamed);
	}
}
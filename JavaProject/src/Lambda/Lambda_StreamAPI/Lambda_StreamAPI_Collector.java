package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;

// 사용자 정의 Collector
public class Lambda_StreamAPI_Collector {
	
	public static void main(String[] args) {
		
		List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");
		
		// 스트림 API를 사용하여 사용자 정의 컬렉터로 문자열 연결
		String concatenatedNames =
			// Collector.of() 메서드는 스트림 API의 collect() 메서드에서 사용자 정의 컬렉션을 구현하는데 사용
			names.stream().collect(Collector.of(
						  () -> new StringBuilder()					// 누적기 생성 함수( StringBuilder 초기화 )
				  		, (sb, str) -> sb.append(str).append(", ")	// 누적기 요소(sb, str)추가 후, 문자열 연결
				  		, (sb1, sb2) -> sb1.append(sb2)				// 누적기 병합함수
				  		, sb -> sb.toString().replace(", $", "")	// 최종 결과 생성 함수
				 ));
		System.out.println(concatenatedNames);
	}
}

package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Lambda_StreamAPI_FindFirst_And_FindAny {
	
	public static void main(String[] args) {
		
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		
		// 스트림 API FindFirst()를 사용하여 첫 번째 짝수를 찾기
		Optional<Integer> firstEven
			= numbers.stream()
					 .filter(n -> n % 2 == 0)
					 // findFirst() 메서드를 사용하여 첫 번째 요소 찾기
					 .findFirst();
					 /** findFirst() 메서드는 스트림에서 첫 번째 요소를 찾는 메서드
					  *  - 기능 : 스트림에서 첫 번째 요소를 찾아 Optional 객체로 반환
					  *  - 반환 값 : Optional<T> 타입으로, 스트림이 비어있으면 empty()인 Optional을 반환하고
					  *            요소가 존재하면 해당 요소를 담은 Optional을 반환 합니다.
					  * */
		System.out.println(firstEven.orElse(0));
		
		
		// 스트림 API를 사용하여 임의의 짝수 찾기
		Optional<Integer> anyEven 
			= numbers//.stream()
			 		 .parallelStream()
					 // 짝수만 필터링
					 .filter(n -> n % 2 == 0)
					 .findAny();
					 /** findAny() 메서드는 스트림에서 임의의 요소를 찾는 메서드
					  *  기능 : 스트림에서 아무 요소나 찾아 Optional 객체로 반환, 병렬 스트림에서 성능이 향상될 수 있다.
					  *  반환 값 : Optional<T> 타입으로, 스트림이 비어있으며 empty()인 Optional을 반환하고, 
					  *          요소가 존재하면 해당 요소를 담은 Optional을 반환한다.
					  *  병렬 스트림에서는 findAny가 첫 번째 값이 반환될 수도 있고, 임의의 값이 반환될 수도 있다.
					  */
		System.out.println(anyEven.orElse(0));
		
		/* [참고]
		 * 병렬 스트림에서 findAny()를 사용할 때, 항상 같은 요소가 반환되는 것처럼 보이는 것은 JVM 최적화 및 실행 환경의 특성에 따른
		 * 우연적인 현상일 뿐이다. findAny()는 비결정적인 메서드이며, 실제로는 여러 요소중 하나가 임의의로 반환될 수 있다.
		 */   
	}
}

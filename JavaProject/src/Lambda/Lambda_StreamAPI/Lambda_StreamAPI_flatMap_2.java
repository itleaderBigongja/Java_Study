package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// 예시 : 객체 리스트에서 내부 리스트 추출하는 예제
class Person {
	
	private String name;			// 이름
	private List<String> skills;	// 기술목록
	
	// 생성자
	public Person(String name, List<String> skills) {
		this.name   = name;
		this.skills = skills;
	}
	
	// Getter
	public String getName() {
		return name;
	}
	public List<String> getSkills() {
		return skills;
	}
}

public class Lambda_StreamAPI_flatMap_2 {

	public static void main(String[] args) {
		// 사람 객체들을 담고 있는 리스트 생성
		List<Person> people = Arrays.asList(
				new Person("탁우근", Arrays.asList("JAVA", "SQL", "Spring", "SpringBatch"))
			  , new Person("이지섭", Arrays.asList("Python", "DeepRunning", "API", "BigDATA"))
		);
		
		// 스트림 API를 사용하여 모든 사람의 기술 목록 추출
		/** flatMap() 메서드는 스트림의 각 요소를 다른 스트림으로 변환한 후, 이 모든 스트림을 하나의 스트림으로 병합하는 메서드
		 * - 매개변수: Function<T, Stream<R>> 인터페이스를 구현한 객체를 매개변수로 받습니다. 여기서 T는 스트림의 요소 타입(Person), R은 변환될 스트림의 요소 타입(String)입니다.
         * 
         * - 람다 표현식 person -> person.getSkills().stream()는 Function<Person, Stream<String>> 인터페이스를 구현한 익명 객체입니다.
		 * - 람다 표현식에서 person은 스트림의 각 Person 객체를 의미
		 * - person.getSkills()는 Person 객체의 기술 리스트를 반환
		 * - person.getSkills().stream()는 기술 리스트를 스트림으로 변환 합니다.
		 * */
		List<String> allSkills = people.stream()
								.flatMap(person -> person.getSkills().stream())
														 .distinct()
														 .collect(Collectors.toList()
										);
		System.out.println(allSkills);
	}
}
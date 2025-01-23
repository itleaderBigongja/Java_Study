package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Person_Test1{
	
	private String name;
	private int age;
	
	public Person_Test1(String name, int age) {
		this.name = name;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}	
}

// 여러 조건 정렬( thenComparing )
public class Lambda_StreamAPI_thenComparing {
	
	public static void main(String[] args) {
		
		List<Person_Test1> people = Arrays.asList(
								new Person_Test1("탁우근", 32)
							  , new Person_Test1("정우현", 29)
							  , new Person_Test1("정우종", 29)
							  , new Person_Test1("한서종", 29)
							  , new Person_Test1("이준행", 29));
		
		List<Person_Test1> multiSortedPeople 
				= people.stream()
				  // sorted() 메서드와 thenComparing()을 사용하여 여러 조건으로 정렬
				  .sorted(Comparator.comparing(Person_Test1::getAge)
			  /** Comparator.comparing(Person::getAge)는 Person 객체의 나이를 기준으로 오름차순 정렬을 수행
			   *  - 기능 특정 속성을 기준으로 정렬할 때 사용
			   */
				  .thenComparing(Person_Test1::getName))
				  /** thenComparing(Person_Test1::getName)는 나이가 같을 경우, 이름을 기준으로 오름차순 정렬을 수행합니다.
				   *  - 기능 : 첫 번째 정렬 조건과 동일한 경우, 추가 정렬 조건을 적용할 때 사용
				   * */
		          .collect(Collectors.toList());
		
		multiSortedPeople.forEach(p -> System.out.println(p.getName() + ", " + p.getAge()));
	}
}

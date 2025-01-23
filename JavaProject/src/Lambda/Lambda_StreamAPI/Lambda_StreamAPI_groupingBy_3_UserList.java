package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class User_1 {
	
	private String name;
	private int age;
	private String city;
	
	public User_1(String name, int age, String city) {
		this.name = name;
		this.age = age;
		this.city = city;
	}
	
	public String getName() {
		return name;
	}
	public int getAge() {
		return age;
	}
	public String getCity() {
		return city;
	}
}

public class Lambda_StreamAPI_groupingBy_3_UserList {
	
	public static void main(String[] args) {
		
		List<User_1> users = Arrays.asList(
					new User_1("탁우근", 32, "경기도")
				   ,new User_1("정우현", 29, "서울")
				   ,new User_1("정우종", 29, "서울")
				   ,new User_1("한서종", 29, "서울")
				   ,new User_1("이준행", 29, "서울")
				   ,new User_1("구태우", 35, "도쿄")
				   ,new User_1("권유철", 35, "서울")
				   ,new User_1("김용길", 39, "부천")
				   );
		
		
		// 30살 미만이면서 서울에서 살고 있는 사람 찾기
		int minAge = 30;
		String local = "서울";
		List<User_1> filteredAndSortedUsers 
			= users.stream()
				   .filter(user -> user.getAge() <= minAge && local.equals(user.getCity()))
				   .sorted(Comparator.comparing(User_1::getName)) // 이름으로 정렬
				   .collect(Collectors.toList());
		filteredAndSortedUsers.forEach(user -> System.out.println("이름: " + user.getName() + " 나이: " + user.getAge()));
		
		
		// local 지역만 그룹화 하기
		Map<String, Long> userBySeoul = users.stream()
				.filter(user -> local.equals(user.getCity()))
			    .collect(Collectors.groupingBy(User_1::getCity, Collectors.counting()));
		
		
		// 도시별로 그룹화 하기
		Map<String, Long> userByCity = users.stream()
											.collect(Collectors.groupingBy(User_1::getCity, Collectors.counting()));
		System.out.println("서울 집계 : " + userBySeoul);
		System.out.println("도시 전체 집계 : " + userByCity);
	}
	
	
	
}

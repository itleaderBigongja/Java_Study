package Lambda.Lambda_StreamAPI;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Collectors;

class Product {
	private String category;
	private String name;
	
	public Product(String category, String name) {
		this.category = category;
		this.name = name;
	}
	
	public String getCategory(){
		return category;
	}
	public String getName() {
		return name;
	}
}

// 객체 리스트를 특정 속성으로 그룹화하고 개수 세기
public class Lambda_StreamAPI_groupingBy_2 {
	
	public static void main(String[] args) {
		
		// Product 리스트 생성
		List<Product> products = Arrays.asList(
								new Product("노트북", "Apple_M1"),
								new Product("노트북", "Apple_M2"),
								new Product("노트북", "Apple_M3"),
							    new Product("노트북", "LG Gram"),
							    new Product("노트북", "Samsung_GalaxyBook"));
		
		// 스트림 API를 사용하여 Product 객체들을 카테고리별로 그룹화하고 개수 세기
		/** groupingBy() 메서드는 스트림의 요소들을 주어진 기준에 따라 그룹화하여 Map으로 수집하는 메서드
		 *  기능 : 스트림의 요소를 특정 속성이나 기준으로 그룹화하고, 그룹화된 결과를 Map으로 반환
		 *  Product::getCategory는 Function<Product, String>을 인터페이스를 구현한 메서드 참조
		 *  Collectors.counting()은 요소의 개수를 세는 컬렉터
		 *  결과적으로 각 캍테고리별 제품 개수를 Map<String, Long>으로 저장
		 * */
		Map<String, Long> productCountByCategory 
				= products.stream()
						  .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));
		System.out.println(productCountByCategory);
	}
}

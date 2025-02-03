package Thread.thread_6_Intermediate.CompletableFuture;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class CompletableFutureExample_MultiAPICall {

	// HTTP 클라이언트는 외부 리소스(API)와 통신하기 위한 도구입니다.
	private static final HttpClient httpClient = HttpClient.newHttpClient();
	
	public static void main(String[] args) {
		
		// 비동기적으로 호출할 API URL 목록을 정의합니다.
		List<String> urls = List.of(
                "https://jsonplaceholder.typicode.com/todos/1",
                "https://jsonplaceholder.typicode.com/todos/2",
                "https://jsonplaceholder.typicode.com/todos/3"
        );
		
		// 사용자에게 API 호출 시작을 알립니다.
        System.out.println("API 호출 시작...");
        
        // 각 URL에 대해 비동기 API를 호출을 수행하고 CompletableFuture 객체를 생성
        List<CompletableFuture<String>>
        		// URL 리스트를 스트림으로 변환( 스트림 API )
        		futures = urls.stream()
        					  // 각 URL을 순회하면서 다음 작업을 수행
							  .map(url ->
							  	   // 각 URL에 대한 비동기 작업 생성( 스레드 풀 사용 )
							  	   CompletableFuture.supplyAsync(() -> fetchUrlContent(url))  
								  ).collect(Collectors.toList()); // CompletableFuture들을 리스트로 모읍니다.
        
        /**개념:** 모든 CompletableFuture 객체가 완료될 때까지 기다리는 CompletableFuture를 생성 */ 
        CompletableFuture<Void> allFutures = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        
        /**개념:** 모든 작업이 완료되면 실행될 콜백 함수를 등록합니다.*/
        try {
        	// thenRun()은 결과를 소비하지 않고 특정 동작을 수행합니다.
        	allFutures.thenRun(() -> { 
            	// 모든 비동기 작업 완료 메시지 출력
            	System.out.println("모든 API 호출 완료");
            	// 각 CompletableFuture의 결과를 모아서 리스트로 만듭니다.
            	// CompltableFuture 리스트를 스트림으로 변환 
            	List<String> contents = futures.stream()
            								   // 각 CompltableFuture의 결과를 가져옵니다( join()은 get()과 유사하나 checked 예외를 던지지 않는다.
            								   .map(CompletableFuture::join)
            								   // 결과들을 리스트로 모은다.
            								   .collect(Collectors.toList());
            	// 각 결과를 출력합니다.
            	contents.forEach(System.out::println);
            }).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**개념:** 외부 API를 호출하고 응답을 반환하는 메서드 */
	private static String fetchUrlContent(String url) {
		try {
			// HTTP 요청을 빌드한다.
			HttpRequest request = HttpRequest.newBuilder()
								  .uri(URI.create(url))	// URL 설정
								  .build();
			
			// HTTP 요청을 보내고 응답을 받습니다.
			HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
			// 시뮬레이션을 위해 스레드를 잠시 중단 시킵니다.
			TimeUnit.SECONDS.sleep(1);
			// fetchUrlContent 완료 메시지 출력
			System.out.println("fetchUrlContent " + url);
			
			// API 응답의 본문(내용)을 반환합니다.
			return response.body();
		} catch (Exception e) {
			// 예외 메시지 출력
			System.out.println("fetchUrlContent " + e.getMessage());
			// 예외 메시지 반환
			return "Error: " + e.getMessage();
		}
	}	
}
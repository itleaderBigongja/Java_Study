package Thread.thread_7_HighRank.thread_03_completeAsync_Method;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

public class Thread_04_completeAsync_Method {

	public static void main(String[] args) {
		
		// 스레드 풀 생성
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		// 외부 API 호출 결과를 받음 CompletableFuture 생성
		CompletableFuture<String> future = new CompletableFuture<String>();
		
		/** 외부 API 호출을 수행하는 비동기 작업 
		 *  Supplier를 인자로 받으며, 해당 Supplier를 통해 얻은 결과를 CompletableFuture에 비동기적으로 설정 */
		Supplier<String> apiCall = (() -> {
			
			try {
				// HTTP 클라이언트 생성
				HttpClient client = HttpClient.newHttpClient();
				
				// HTTP 요청 생성
				HttpRequest request = HttpRequest.newBuilder()
									 .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
									 .build();
				
				// HTTP 요청 전송 및 응답 수신
				HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
				
				// API 호출 성공 시 결과를 반환
				if( response.statusCode() == 200 ) {
					System.out.println("API응답 코드 : " + response.statusCode());
					return response.body();
				}else {
					return "API 호출 실패 : " + response.statusCode();
				}
			} catch (Exception e) {
				return "API 호출 중 오류 발생: " + e.getMessage();
			}
		});
		
		/** 외부 API 호출 결과를 받아 future에 설정 
		 *  Executor를 이용하여 비동기 작업을 실행할 스레드를 지정할 수 있다.*/
		future.completeAsync(apiCall, executor);
		
		// 메인 스레드 종료 방지
		try {
			System.out.println("API 응답 기다리는 중....");
			System.out.println("API 응답 결과: " + future.join());
		} catch (Exception e2) {
			e2.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}

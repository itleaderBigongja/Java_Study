package Thread.thread_7_HighRank;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** newIncompleteFuture 메서드란?
 *  newIncompleteFuture는 CompletableFuture 클래스에서 제공하는 정적 메서드가 아니다.
 *  CompletableFuture의 객체를 생성하는 방법 중 하나일 뿐이다.
 *  이 메서드를 직접 사용하는 경우는 드물고, 주로 라이브러리나 프레임워크 내부에서 사용된다.
 *  
 *  미완료 상태로 시작: newIncompleteFuture를 사용하여 생성한 CompletableFuture는 처음 결과값이 설정되지 않아 미완료 상태로 시작
 *  수동 완료 필요 : CompletableFuture는 complete(), completeExceptionally(), 또는 cancel() 메서드를 사용하여
 *                수동으로 완료해야 한다. 그렇지 않으면 영원히 완료되지 않는다.              
 * */
public class Thread_05_newIncompleteFuture_Method {

	 public static void main(String[] args) {
	        // 서버 목록 생성
	        List<String> servers = Arrays.asList("server1", "server2", "server3");

	        // 스레드 풀 생성
	        ExecutorService executor = Executors.newFixedThreadPool(servers.size());

	        // 각 서버에서 데이터를 가져오는 CompletableFuture 리스트 생성
	        List<CompletableFuture<String>> futures = servers
	                .stream()
	                .map(server -> {
	                    // newIncompleteFuture를 사용하여 미완료 CompletableFuture 생성
	                    CompletableFuture<String> future = new CompletableFuture<>();

	                    // 스레드 풀에서 비동기 작업 실행
	                    executor.submit(() -> {
	                       try{
	                         String result = fetchDataFromServer(server); // 데이터 가져오는 작업
	                         future.complete(result); // 작업 성공시 complete() 메서드로 결과 설정
	                       } catch (Exception e) {
	                           future.completeExceptionally(e); // 작업 실패 시 completeExceptionally() 메서드로 예외 설정
	                       }
	                    });

	                    return future; // 미완료 CompletableFuture 반환
	                })
	                .collect(Collectors.toList());

	        // 모든 CompletableFuture가 완료될 때까지 기다리고, 결과를 처리하는 CompletableFuture 생성
	        CompletableFuture<List<String>> handleFuture =
	                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
	                        .thenApply(v -> futures.stream()
	                                .map(future -> future.handle((result, ex) -> {
	                                    if (ex != null) {
	                                        System.out.println("오류 발생: " + ex.getMessage());
	                                        return "서버 작업 실패";
	                                    } else {
	                                        return "서버 작업 성공: " + result;
	                                    }
	                                }).join()).collect(Collectors.toList()));

	        try {
	           handleFuture.join().forEach(System.out::println);
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	           executor.shutdown();
	        }
	    }

	    // 서버에서 데이터 가져오는 시뮬레이션
	    private static String fetchDataFromServer(String serverName) {
	        try {
	            long delay = (long) (Math.random() * 2000 + 500);
	            TimeUnit.MILLISECONDS.sleep(delay);
	            if (Math.random() < 0.2) {
	                throw new RuntimeException(serverName + " 작업 실패");
	            }
	            return serverName + "에서 데이터를 가져옴";
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	            return serverName + " 작업 실패";
	        }
	    }
	}

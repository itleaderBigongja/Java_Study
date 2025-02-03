package Thread.thread_7_HighRank;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/** handle 메서드란?
 *  정의 : CompletableFuture 클래스에서 제공하는 메서드로, 비동기 작업의 결과 또는 예외를 받아 처리하고,
 *        새로운 결과값을 반환한다.
 *
 *  기능 : 비동기 작업이 성공적으로 완료되었을 때와 예외가 발생했을 때 모두  처리하고 싶을 때 사용
 *  
 *  특징
 *    ㅇ 비동기 동작 : handle 메서드 자체는 비동기적으로 동작한다, 즉, 작업 완료 시 콜백이 실행되고 바로 반환된다.
 *    ㅇ 결과 또는 예외처리 : BiFunction을 사용하여 결과(T) 또는 예외 ( Throwable )를 받아 새로운 결과(U)를 생성한다.
 *    ㅇ 새로운 CompletableFuture 반환: 변환된 결과를 담은 새로운 CompletableFuture를 반환한다.
 *
 *  handle 메서드의 필요한 상황
 *    ㅇ 예외 처리: 비동기 작업 중 발생할 수 있는 예외를 처리하고, 예외 발생 시, 대체 값을 반환하거나 예외 메시지를 로그에 기록해야 할 때,
 *    ㅇ 결과 변환: 비동기 작업의 결과가 성공 또는 실패 여부에 따라 다른 방식으로 변환해야 할 때,
 *    ㅇ Fallback 처리: 비동기 작업이 실패할 경우, 다른 대안 작업을 시도해야할 때
 **/
public class Thread_04_handle_Method {

    public static void main(String[] args) {

        // 서버 목록 생성
        List<String> servers = Arrays.asList("server1", "server2", "server3");

        // 스레드 풀 생성 : 서버 수 만큼 스레드를 생성하여 병렬 처리
        ExecutorService executor = Executors.newFixedThreadPool(servers.size());

        // 각 서버에서 데이터 가져오기( 데이터를 비동기적으로 가져오는 CompletableFuture 생성
        List<CompletableFuture<String>> futures = servers
            .stream()
            .map(server -> // 각 서버에 대해 비동기 작업 실행 
            	CompletableFuture.supplyAsync(
            			() -> fetchDataFromServer(server), executor) // 서버에서 데이터를 가져오는 작업을 비동적으로 실행, 스레드 풀 사용
            ).collect(Collectors.toList()); 						 // CompletableFuture 리스트로 수집
        
        // 모든 CompletableFuture가 완료될 때까지 대기하고, 결과를 처리하는 CompletableFuture 생성
        CompletableFuture<List<String>> handleFuture =
            CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenApply(v -> 			// thenApply를 사용하여 모든 작업 완료 후 실행될 함수를 지정 
                	futures.stream()		// CompletableFuture 리스트 스트림 생성
                		.map(future -> {	// 각 CompltableFuture 결과 처리
                			return future.handle((result, ex) -> {
                				if(ex != null) {
                					System.out.println("오류 발생: " + ex.getMessage());
                					return "서버 작업 실패";
                				}else {
                					return "서버 작업 성공: " + result;
                				}
                			}).join(); 						// CompletableFuture 결과를 가져오기 위해 join() 호출( 결과를 즉시 얻음 )
                		}).collect(Collectors.toList()));   // 처리된 결과를 리스트로 수집
 
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
            // 실제 서버 연동하는 시간 delay를 구현
            long delay = (long)(Math.random() * 2000 + 500); // 0.5 ~ 2.5초 랜덤 대기
            TimeUnit.MILLISECONDS.sleep(delay);
            if(Math.random() < 0.2) {  // 20% 확률로 예외 발생
                 throw new RuntimeException(serverName + "작업 실패");
            }
            return serverName + "에서 데이터를 가져옴";
        } catch (InterruptedException e) {
             Thread.currentThread().interrupt();
            return serverName + " 작업 실패";
        }
    }
}
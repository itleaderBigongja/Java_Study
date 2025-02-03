package Thread.thread_7_HighRank.thread_01_acceptEither_Method;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Thread_02_acceptEither_Method {

	 public static void main(String[] args) {
		 // 서버 목록 생성
	        List<String> servers = Arrays.asList("server1", "server2", "server3");

	        // 스레드 풀 생성
	        ExecutorService executor = Executors.newFixedThreadPool(servers.size());

	        // 각 서버에서 데이터 가져오기
	        List<CompletableFuture<String>> futures = servers.stream()
	            .map(server -> CompletableFuture.supplyAsync(() -> fetchDataFromServer(server), executor))
	            .collect(Collectors.toList());

	        // 먼저 응답하는 서버 데이터 처리 후 결과 반환
	        CompletableFuture<Void> result = futures.get(0).applyToEither(futures.get(1), response -> {
	           return "먼저 응답한 서버 결과:\n" + response;
	         }).thenAccept(response -> {
	             futures.subList(2, futures.size()).forEach(future -> future.cancel(true)); // 나머지 작업 취소
	              System.out.println(response);
	        });

	        try {
	             result.join();
	        } catch (Exception e) {
	            e.printStackTrace();
	        } finally {
	            executor.shutdown();
	       }
	    }

	    // 서버에서 데이터 가져오는 시뮬레이션
	    private static String fetchDataFromServer(String serverName) {
	        try {
	             long delay = (long) (Math.random() * 2000 + 500); // 0.5 ~ 2.5초 랜덤 대기
	             TimeUnit.MILLISECONDS.sleep(delay);
	             return serverName + "에서 데이터 가져옴";
	        } catch (InterruptedException e) {
	            Thread.currentThread().interrupt();
	           return serverName + "작업 실패";
	        }
	    }
}

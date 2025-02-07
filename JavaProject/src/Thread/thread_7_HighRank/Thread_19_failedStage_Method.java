package Thread.thread_7_HighRank;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;

public class Thread_19_failedStage_Method {

	 private static final Map<String, CompletionStage<String>> cache = new ConcurrentHashMap<>();

	    private static CompletableFuture<String> fetchData(String key) {
	        System.out.println("Fetching data for key: " + key);
	        return CompletableFuture.supplyAsync(() -> {
	            // 데이터 가져오는 로직 (실패할 가능성이 있다고 가정)
	            if (key.equals("badKey")) { // 특정 키에 대해 실패 시뮬레이션
	                throw new RuntimeException("Failed to fetch data for key: " + key);
	            }
	            try {
	                Thread.sleep(1000);
	            } catch (InterruptedException e) {
	                Thread.currentThread().interrupt();
	            }
	            return "Data for key: " + key;
	        });
	    }

	    public static CompletionStage<String> getData(String key) {
	        CompletionStage<String> cachedValue = cache.get(key);
	        if (cachedValue != null) {
	            System.out.println("Returning cached data for key: " + key);
	            return cachedValue;
	        }

	        CompletableFuture<String> future = fetchData(key);
	        cache.put(key, future);

	        // 만약 실패하면 completedStage가 아닌 failedStage 사용.
	        future.exceptionally(e -> {
	            System.out.println("Exception occurred while fetching data for key: " + key + ", reason: " + e.getMessage());
	            cache.put(key, CompletableFuture.failedStage(e)); //failedStage를 사용해 캐싱.
	            return null; // 재 스로우를 통해 CompletionStage도 failed 상태로 변경.
	        });

	        return future;
	    }

	    public static void main(String[] args) throws Exception {
	        CompletionStage<String> data1 = getData("key1");
	        CompletionStage<String> data2 = getData("badKey"); // 실패하는 요청

	        data1.thenAccept(result -> System.out.println("Data1: " + result));
	        data2.exceptionally(e -> { // 실패에 대한 처리
	            System.out.println("Data2 failed: " + e.getMessage());
	            return null;
	        });

	        Thread.sleep(3000);
	    }
}

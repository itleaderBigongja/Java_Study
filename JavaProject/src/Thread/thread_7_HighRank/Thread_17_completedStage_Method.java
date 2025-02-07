package Thread.thread_7_HighRank;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ConcurrentHashMap;
/** CompletableFuture.completedState() 메서드
 *  CompletableFuture.completedStage(CompletionStage<T> stage)는 Java 9에서 추가된 메서드이다.
 *  이 메서드는 이미 완료된(즉, 결과가 준비된) CompletionStage를 반환하는 정적 메서드이다.
 *  CompletableFuture는 ComopletionStage 인터페이스를 구현하므로, 이 메서드를 사용하여
 *  완료된 CompletableFuture를 만들 수 있습니다.
 *  
 *  
 *  역할 및 동작
 *  < 즉시 완료된 스테이지 생성 >
 *  ㅇ completedStage() 메서드는 인수로 주어진 CompletionStage가 즉시 완료된 상태로 동작하도록
 *     래핑하여 새로운 CompletableFuture를 생성합니다.
 *     
 *  < 기존 스테이지 재사용 >
 *  ㅇ 인수로 주어진 CompletionStage가 이미 완료된 스테이지라면, 동일한 스테이지를 재사용하여 불필요한 객체 생성을 줄입니다.
 *  
 *  < 결과/예외 공유 >
 *  ㅇ 결과가 이미 준빋되어 있기 때문에, get() 메서드를 호출하면 즉시 결과를 반환하고, 예외가 발생했다면 get() 메서드에서
 *    ExecutionException을 발생시킵니다.
 *    
 *  < 동기적 실행 >
 *  ㅇ 이미 완료된 스테이지이므로, 연결된 후속 작업(thenApply(), thenAccept(), 등)
 *     즉시, 즉 동기적으로 실행될 가능성이 높습니다.( 하지만 Executor를 사용한 경우는 예외입니다.)
 *     
 *  < 사용 이유 및 시나리오 >
 *  1. 캐싱 또는 메모이제이션
 *  -> 미리 계산된 값을 재사용해야 하는 경우, 이전에 계산된 결과를 CompletionStage에 저장하고,
 *     completedStage()를 사용하여 즉시 사용 가능한 형태로 제공할 수 있습니다.
 *     
 *  2. 테스트:
 *  -> 비동기 코드의 테스트를 용이하게 합니다. 완료된 스테이지를 사용하여 특정 결과를 즉시 반환하는
 *     상황을 쉽게 시뮬레이션할 수 있습니다.
 *     
 *  3. API 디자인:
 *  -> 이미 계산된 결과를 CompletionStage 형태로 반환해야 하는 경우, 특히 기존 코드를
 *     CompletionStage 기반ㄴ으로 점진적으로 전환할 때 유용합니다.
 *  
 *  4. 결과 없는 작업: 
 *  -> void를 반환하는 작업(Runnable)에 대해서도 completedStage를 사용할수 있으며,
 *     이 경우 CompletableFuture<Void> 타입으로 반환된다. 
 **/
public class Thread_17_completedStage_Method {
	
	// Map<String, CompletionStage<String>> cache 캐시를 저장하는 Map, key는 String, value는 CompletionStage<String>
	// ConcurrentHashMap: 스레드 안전성을 제공하므로, 동시 환경에서 여러 스레드가 동시에 캐시에 접근하더라도 문제가 발생하지 않는다.
	private static final Map<String, CompletionStage<String>> cache = new ConcurrentHashMap<String, CompletionStage<String>>();

	/** fetchData 메서드: 실제로 데이터를 가져오는 역할을 한다.
	 *  네트워크 호출이나, 데이터베이스 쿼리를 시뮬레이션 해볼 수 있다. 
	 **/
	private static CompletableFuture<String> fetchData(String key) {
		// 데이터를 가져오는 로그 출력
		System.out.println("Fetching data for key: " + key);
		
		// 비동기적으로 작업을 수행하는 CompletableFuture 생성
		return CompletableFuture.supplyAsync(() -> {
			
			try {
				// 실제 데이터베이스나 외부 API에서 데이터를 가져오는 로직
				Thread.sleep(1000);
				// 데이터를 반환( key에 해당하는 데이터 )
				return "Data for key: " + key;
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RuntimeException("Failed to fetch data", e);
			}
		});		
	}
	
	/** getData() : 캐시를 사용하여 데이터를 가져오는 메서드 
	 *  ㅇ 캐시에 데이터를 먼저 확인한다.
	 *  ㅇ */ 
	public static CompletionStage<String> getData(String key) {
		
		// 1. 캐시에 데이터가 있는지 확인
		CompletionStage<String> cachedValue = cache.get(key);	// 캐시에서 가져오는 로그 출력
		
		if(cachedValue != null) {
			// 캐시에서 가져오는 로그 출력
			System.out.println("Returning cached data for key: " + key);
			
			// 캐시된 CompletionStage<String>를 반환 (completedStage() 사용 X)
			return cachedValue;
		}
		
		// 2. 캐시에 데이터가 없으면 데이터를 가져와서 캐시에 저장
		CompletableFuture<String> future = fetchData(key); // fetchData()를 호출하여 비동기적으로 데이터 가져옴
		cache.put(key, future); // 캐시에 key와 future를 저장
		
		// 3.만약 데이터를 가져오는 중에 예외가 발생하면, 캐시에서 제거
		future.exceptionally(e -> { // 예외가 발생했을 때, 실행되는 콜백 함수 등록
			cache.remove(key);	// 캐시에서 해당 key-value 쌍 제거(중요)
			return null;	// 예외를 propagate 시킴( 다음 단계에서 예외를 처리하도록 함)
		});
		
		// CompletionStage<String> 변환
		return future;
	}
	
	public static void main(String[] args) throws Exception {
        // 여러 번 데이터를 요청 (일부는 캐시에서 가져옴)
        CompletionStage<String> data1 = getData("key1"); // "key1"에 해당하는 데이터 요청
        CompletionStage<String> data2 = getData("key1"); // "key1"에 해당하는 데이터 다시 요청 (캐시에서 가져옴)
        CompletionStage<String> data3 = getData("key2"); // "key2"에 해당하는 데이터 요청

        // 결과 출력 (thenAccept 사용)
        data1.thenAccept(result -> System.out.println("Data1: " + result)); // data1이 완료되면 결과 출력
        data2.thenAccept(result -> System.out.println("Data2: " + result)); // data2가 완료되면 결과 출력
        data3.thenAccept(result -> System.out.println("Data3: " + result)); // data3이 완료되면 결과 출력

        // 메인 스레드가 종료되지 않도록 잠시 대기
        Thread.sleep(3000); // 3초 동안 메인 스레드 대기
    }
}

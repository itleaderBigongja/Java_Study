package Thread.thread_5;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** CachedThreadPool을 이용하여 DB SELECT, DB INSERT, DB UPDATE 작업을 수행하고,
 *  작업을 순차적으로 진행하려면, 각 작업이 완료된 후에 다음 작업을 시작하도록 스레드의 실행 흐름을 제어해야 합니다.
 *  이를 구현하려면 Future 객체를 활용하여 작업의 결과를 기다린 후 다음 작업을 실행하면 됩니다.
 * */

/** Runnable과 Callable의 차이
 *  Runnable : 작업 결과가 반환할 필요가 없을 때
 *  Callable : 작업 결과가 반환값이 필요할 때, 
 * */
public class ExecutorServiceExample_5 {

	public static void main(String[] args) {
		
		// CachedThreadPool 생성		// newFixedThreadPool(3);
		ExecutorService executor = Executors.newCachedThreadPool();
		
		try {
			// DB SELECT 작업
			Callable<String> selectTask = () -> {
				System.out.println("SELECT 조회를 하기 위한 영역... 호출!");
				// Map<testVo> selectMap = service.selectOne(queryId);
				return "SELECT 작업 결과 반환";
			};
			
			// DB INSERT 작업
			Callable<String> insertTask = () -> {
				System.out.println("INSERT 작업을 하기 위한 영역... 호출!");
				// Map<testVo> insertMap = service.insertOne(queryId);
				return "INSERT 작업 결과 반환";
			};
			
			// DB UPDATE 작업
			Callable<String> updateTask = () -> {
				System.out.println("UPDATE 작업 결과 반환");
				// Map<testVo> updateMap = service.updateOne(queryId);
				return "UPDATE 작업 결과 반환";
			};
			
			// 순차 실행( submit 메서드가 실행 되었을 때 )
			Future<String> selectFuture = executor.submit(selectTask); // 
			String selectResult = selectFuture.get();				   // 결과 값 나올때까지 기다린다. 
			System.out.println("SELECT 조회 성공 여부 : " + selectFuture.isDone() + ", 결과 확인 : " + selectResult);
			
			Future<String> insertFuture = executor.submit(insertTask);
			String insertResult = insertFuture.get();
			System.out.println("INSERT 작업 성공 여부 : " + insertFuture.isDone() + ", 결과 확인 : " + insertResult);
			
			Future<String> updateFuture = executor.submit(updateTask);
			String updateResult = updateFuture.get();
			System.out.println("UPDATE 작업 성공 여부 : " + updateFuture.isDone() + ", 결과 확인 : " + updateResult);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("스레드 작업을 종료합니다.");
			executor.shutdown();
		}
	}
}

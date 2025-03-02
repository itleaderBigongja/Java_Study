package Thread.thread_5_Executor;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/** ExcutorSerivce란?
 *  ExcutorService는 자바에서 스레드 풀(Thread Pool)을 관리하기 위한 인터페이스로,
 *  스레드 생성과 실행 관리를 효율적으로 처리합니다. 일반적으로 직접 스레드를 생성하고 관리하는 대신
 *  ExcutorService를 사용하면, 스레드의 생성/종료/큐 관리 등을 자동으로 처리하여
 *  성능과 안전성을 높일 수 있습니다.
 *  
 *  [ ExcutorService의 특징 ]
 *  1. 스레드 풀(Thread Pool)
 *    ㅇ 미리 생성된 스레드 집합을 사용하여 작업을 처리합니다.
 *    ㅇ 작업이 끝난 스레드는 재사용되어 스레드 생성 비용을 줄이고, 시스템 자원을 효율적으로 사용합니다.
 *    
 *  2. 작업 큐(Work Queue)
 *    ㅇ 작업이 제출되면 실행 대기 상태로 작업 큐에 저장됩니다.
 *       스레드가 사용 가능하면 큐에서 작업을 꺼내 실행합니다.
 *       
 *  3. 자동 관리
 *    ㅇ 스레드 수, 작업 대기, 작업 완료 등을 자동으로 관리하여 개발자가 직접 스레드를 제어할 필요가 없습니다.
 * */

/** Excutors 클래스
 *  멀티스레딩 환경에서 스레드 풀(Thread Pool)을 생성하고 관리하기 위한 유틸리티로,
 *  Java의 java.util.concurrent 패키지에 포함되어 있습니다.
 *  
 *  [ Executors 클래스의 주요 용도 ]
 *  1. 스레드 풀 생성
 *  Executor는 다양한 유형의 스레드 풀을 생성하는 정적 메서드를 제공, 이를 통해 애플리케이션에서 효율적으로 관리할 수 있습니다.
 *  
 *  2. 스레드 관리 간소화
 *  직접 스레드를 생성하고 관리하는 번거로움을 줄이고, 재사용 가능한 스레드 풀을 통해 리소스를 효율적으로 사용할 수 있습니다.
 *  
 *  3. 테스크 실행
 *  Runnable이나 Callable 작업을 스레드 풀에 제출하여 병렬로 실행할 수 있습니다.
 * */
public class Executor_FixedAndCachedAndSingle_ThreadPool{

	public static void main(String[] args) {
		
		/** newFixedThreadPool
		 *  ㅇ 고정된 스레드 수로 동작하는 스레드 풀(Thread Pool)을 생성합니다.
		 *  ㅇ 모든 스레드가 바쁠 경우, 작업은 큐(Queue)에 저장됩니다.
		 *    -> 생성한 스레드 3개가 모두 작업을 진행 시, 남은 작업은 Queue에 보관
		 */ 
		ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
		
		try {
			/** Future 객체 
			 *  ㅇ 비동기 작업의 결과를 나타내는데 사용
			 *  ㅇ 작업이 완료될 때까지 기다리거나 결과를 가져올 수 있는 매커니즘을 제공
			 *  ㅇ Future 인터페이스는 주로 Java의 병렬 처리와 관련된 기능에서 사용
			 *  
			 *  submit() 메서드로 작업 제출
			 *  ㅇ 작업을 제출하고, 작업의 결과를 Future 객체로 반환받습니다.
			 **/ 
			Future<String> result1 = fixedThreadPool.submit(() -> {
				Thread.sleep(1000);
				return "Result from Task 1";
			});
			
			Future<String> result2 = fixedThreadPool.submit(() -> {
				Thread.sleep(1000);
				return "Result from Task 2";
			});
			
			Future<String> result3 = fixedThreadPool.submit(() -> {
				Thread.sleep(1000);
				return "Result from Task 3";
			});
			
			/** get() : Blocking 메서드 
			 *  ㅇ get()메서드를 호출하면 작업이 완료될 때까지 현재 스레드가 블록됩니다.
			 *  ㅇ 작업이 이미 완료된 경우 결과를 즉시 반환합니다.
			 **/ 
			System.out.println("첫 번째 스레드 작업 상태 : " + result1.isDone() + " 작업결과 : " + result1.get());
			System.out.println("두 번째 스레드 작업 상태 : " + result2.isDone() + " 작업결과 : " + result2.get());
			System.out.println("세 번째 스레드 작업 상태 : " + result3.isDone() + " 작업결과 : " + result3.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			fixedThreadPool.shutdown();	// 스레드 풀 종료
		}
		
		/** 2. newCachedThreadPool
		 *  ㅇ 필요할 때 스레드를 생성하고, 사용하지 않으면 스레드를 종료합니다.
		 *  ㅇ 적은 작업량에는 자원을 절약하고, 큰 작업량에는 유연하게 대처할 수 있습니다.
		 *  ㅇ invokeAll() 메서드 사용 예제
		 */
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		List<Callable<String>> tasks = Arrays.asList(
				() -> "Task A",
				() -> "Task B",
				() -> "Task C"
		);
		
		try {
			// invokeAll() : 여러 작업을 동시에 제출하고, 결과를 기다립니다.
			List<Future<String>> results = cachedThreadPool.invokeAll(tasks);
			for(Future<String> result : results) {
				// get() : 작업이 완료될 때까지 현재 스레드 블록처리 후, 작업이 완료되면 결과를 즉시 반환한다.
				System.out.println("invokeAll Result : " +result.get());
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			cachedThreadPool.shutdown();
		}
		
		/** SingleThreadExecutor
		 *  ㅇ 단일 스레드로 동작하며, 작업은 순차적으로 실행됩니다.
		 *  ㅇ invokeAny() 메서드 사용예제
		 */ 
		ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
		List<Callable<String>> singleTasks = Arrays.asList(
			() -> {
				Thread.sleep(1000);
				return "First completed task";
			},
			() -> {
				Thread.sleep(1000);
				return "Second completed task";
			}
		);
		
		try {
			// invokeAny() : 여러 작업 중 가장 먼저 완료된 작업의 결과만 반환합니다.
			String fastestResult = singleThreadExecutor.invokeAny(singleTasks);
			System.out.println("invokeAny Result : " + fastestResult);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			// shutdown() : 더 이상 새로운 작업을 받지 않고, 기존 작업이 모두 완료될 때까지 대기합니다.
			singleThreadExecutor.shutdown();
		}
		
		/** 4. ScheduledExecutorService 사용 예제
		 *  ㅇ 작업을 지정된 시간 간격으로 실행할 수 있는 스레드 풀입니다.
		 */
		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
		scheduler.schedule(() -> System.out.println("Scheduled task executed afer 3 seconds")
                               , 3, TimeUnit.SECONDS);
		try {
			// awaitTermination : 스레드가 종료될때까지 대기합니다.
			scheduler.awaitTermination(5, TimeUnit.SECONDS);	// 5초 대기 후 종료 확인
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			scheduler.shutdown();
		}
	}
}
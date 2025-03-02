package Thread.thread_6_Intermediate.CompletableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

public class CompletableFutureExample_addShutdownHook {
	
	/** CompletableFuture의 addShutdownHook() 메서드
	 *  */
	public static void main(String[] args) throws TimeoutException {
		
		System.out.println("프로그램 시작...");
		
		/** 1. 스레드 풀 생성
		 *  ExecutorService: 스레드 풀을 관리하는 인터페이스
		 *  사용 이유: 비동기 작업을 효율적으로 관리하고 스레드 생성/소멸 오버헤드를 줄이기 위해 사용 
		 *  
		 *  Executors.newFixedThreadPool(4) : 지정된 크기(4)의 스레드 풀을 생성하는 팩토리 메서드
		 *  사용 이유: 병렬로 비동기 작업을 수행하기 위해 스레드 풀을 생성 */
		ExecutorService executor = Executors.newFixedThreadPool(4);
		
		/** 2. 종료 후크 스레드 생성 및 등록
		 *  Runtime.getRuntime(): 현재 JVM의 Runtime 객체를 반환
		 *  사용 이유: JVM의 런타임 환경에 접근하기 위해 Runtime 객체를 얻음
		 *  
		 *  Runtime.addShutdownHook(Thread hook): JVM 종료 시 실행될 스레드를 등록
		 *  사용 이유: 프로그램 종료 시 특정 작업을 실행하기 위해 종료 후크 스레드를 등록 
		 *  
		 *  JVM 종료 시점 :
		 *  ㅇ 프로그램의 main() 메서드가 종료될 때( 모든 비 데몬 스레드가 종료되었을 때 )
		 *  ㅇ System.exit() 메서드를 명시적으로 호출할 때
		 *  ㅇ 운영채제에서 JVM 프로세스를 강제 종료할 때( 예 : Ctrl + C, 터미널 종료 )
		 *  
		 *  addShutdownHook() 주의사항
		 *  addShutdownHook()는 주 스레드가 죽기 직전 마지막 호출을 하기 때문에 비동기 작업을 하면 안된다.
		 *  비동기 작업은 다른 곳에서 호출을 하여 실행을 해주는건데 주 스레드가 죽은 후에 호출이 될 수도 있고,
		 *  addShutdownHook()은 마지막으로 자원을 정리하는 용도로 사용하는거지 억지로 다른것을 끼워맞추면 안된다. */
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			// addShutdownHook ( 얘는 무조건 동기로 )
			System.out.println("종료 후크 스레드 시작...");
			
			try {
				TimeUnit.SECONDS.sleep(2);
				System.out.println("종료 작업 1 완료");
			} catch (InterruptedException e) {
				// 다른 스레드를 깨운다. ( 본인 스레드를 본인이 깨울수없다 )
				Thread.currentThread().interrupt();
				System.out.println("종료 작업 1 예외 발생: " + e.getMessage());
			}
			
			
			// addShutdownHook의 잘못된 예( 비동기로 실행을 하면 안되기 때문.. )
			/** 3. 비동기 종료 작업 정의 및 실행
			 *  CompletableFuture<Void>: 결과를 반환하지 않는 비동기 작업 결과를 나타내는 객체
			 *  CompletableFuture.runAsync(Runnable action, Executor executor): 주어진 작업을 스레드 풀에서 비동기적으로 실행 */
			CompletableFuture<Void> shutdownTask1 = CompletableFuture.runAsync(() -> {
				try {
					TimeUnit.SECONDS.sleep(2);
					System.out.println("종료 작업 1 완료");
				} catch (InterruptedException e) {
					// 다른 스레드를 깨운다. ( 본인 스레드를 본인이 깨울수없다 )
					Thread.currentThread().interrupt();
					System.out.println("종료 작업 1 예외 발생: " + e.getMessage());
				}
			});
			
			/** CompletableFuture.supplyAsync(Supplier<U> supplier, Executor executor)
			 *  -> 주어진 작업을 스레드 풀에서 비동기적으로 실행하고 결과를 반환
			 *     사용 이유: 비동기적으로 코드를 실행하고 결과를 반환하는 종료 작업을 정의
			 **/
			CompletableFuture<Void> shutdownTask2 = CompletableFuture.supplyAsync(() -> {
				
				/** 배치 처럼 잡으로 묶어서 하기 위해서 쓰는 것 */ 
				try {
					TimeUnit.SECONDS.sleep(3);
					System.out.println("종료 작업 2 완료");
					return null;
				} catch (InterruptedException e2) {
					Thread.currentThread().interrupt();
					System.out.println("종료 작업 2 예외 발생: " + e2.getMessage());
					return null;
				}
			}, executor);
			
			CompletableFuture<Void> shutdownTask3 = CompletableFuture.runAsync(() -> {
				
				try {
					TimeUnit.SECONDS.sleep(1);
					throw new RuntimeException("종료 작업 3 실패");
				} catch (InterruptedException e3) {
					Thread.currentThread().interrupt();
					System.out.println("종료 작업 3 예외 발생: " + e3.getMessage());
				}
			}, executor);
			
			
			/** CompletableFuture.allOf(CompletableFuture<?>... future)
			 *  -> 모든 CompletableFuture가 완료되면 완료되는 CompletableFuture 생성
			 *     사용 이유: 모든 비동기 작업이 완료될 때까지 기다리기 위해 사용 
			 **/
			CompletableFuture<Void> allTasks = CompletableFuture.allOf(shutdownTask1, shutdownTask2, shutdownTask3);
			
			try {
				/** orTimeout(long timeout, TimeUnit unit): 
				 *  -> 지정된 시간 안에 작업이 완료되지 않으면 TimeoutException 발생시키는 메서드
				 *     사용 이유: 작업 시간이 너무 길어질 경우 타임아웃 처리
				 *     join(): 결과를 반환하지 않고 작업이 완료될 때까지 대기하는 메서드
				 **/
				allTasks.orTimeout(5, TimeUnit.SECONDS).join();
				System.out.println("모든 종료 작업 완료");
			} catch (Throwable e4) {
				System.out.println("종료 후크 스레드 예외 발생: " + e4.getMessage());
			} finally {
				// ExecutorService.shutdown(): 스레드 풀을 종료
				executor.shutdown();
				
				try {
					// ExecutorService.awaitTermination(long timeout, TimeUnit unit)
					// -> 스레드 풀이 종료될 때가지 대기
					executor.awaitTermination(1, TimeUnit.MINUTES);
				} catch (InterruptedException e5) {
					Thread.currentThread().interrupt();
				}
			}
			
			System.out.println("종료 후크 스레드 종료!");
		}));
		
	}
}
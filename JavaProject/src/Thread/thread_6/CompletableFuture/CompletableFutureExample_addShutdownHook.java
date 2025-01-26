package Thread.thread_6.CompletableFuture;

/**
 * //예외상태로 실행이 종료되었을 경우
	CompletableFuture.supplyAsync(() -> {return 2000;}).thenApply(s -> {
		return s;
	}).exceptionally(null).whenComplete(null);
	
	// 정상적으로 실행이 종료 됐을 때
	Runtime.getRuntime().addShutdownHook(new Thread() {
		public void run() {
			
		}
	});
	
	// thenRun() : future 작업이 완료된 후 후속 작업을 실행합니다.
	future.thenRun(() -> System.out.println("Follow-up task on" + Thread.currentThread().getName()));
 * */
public class CompletableFutureExample_addShutdownHook {
			
}

package Thread.thread_7_HighRank;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

/** CompletableFuture.failedStage() 메서드
 *  CompletableFuture.failedStage(Throwable ex)는 Java 9에 추가된 정적 메서드입니다.
 *  이 메서드는 지정된 예외(Throwable)로 즉시 완료된(실패한) CompletionStage를 반환합니다.
 *  
 *  역할 및 동작
 *  < 실패한 스테이지 생성 >
 *  ㅇ failedStage() 메서드는 인수로 주어진 예외를 포함하는 실패한 CompletionStage를 생성합니다.
 *  
 *  < 예외 공유 >
 *  ㅇ CompletionStage에 대해 get() 메서드를 호출하면 ExecutionException이 발생하고,
 *     그 원인으로 전달된 예외( ex )가 포함됩니다.
 *     
 *  < 후속 작업 처리 >
 *  ㅇ thenApply(), thenAccept()와 같은 후속 작업은 실행되지 않고,
 *     exceptionally()나 handle()과 같은 예외 처리 메서드로 바로 전달됩니다.
 *     
 *  사용 이유 및 시나리오
 *  1. 에러 처리:
 *  -> 특정 조건에서 비동기 작업을 즉시, 실패시켜야 하는 경우, 예를 들어, 입력 값이 유효하지 않거나 필요한
 *     리소스가 없는 경우, 작업을 시작하기 전에 failedStage()를 사용하며 실패 상태로 만들 수 있습니다.
 *     
 *  2. 테스트:
 *  -> 예외가 발생하는 비동기 코드의 테스트를 용이하게 합니다.
 *     failedStage()를 사용하면 특정 예외를 던지는 비동기 작업을 쉽게 모의(mock)할 수 있습니다.
 *     
 *  3. API 디자인:
 *  -> 에러 상태를 CompletionStage 형태로 반환해야 하는 경우, 특히 기존 코드를
 *     CompletionStage 기반으로 점진적으로 전환할 때 유용합니다.
 **/
public class Thread_18_failedStage_Method {

	public static void main(String[] args) throws Exception {

        // 1. failedStage()를 사용하여 실패한 CompletableFuture 생성
		// failedStage() 사용: CompletableFuture.failedStage()를 사용하여 
		//                    IllegalArgumentException으로 실패한 CompletionStage를 생성
        CompletionStage<String> failedStage = CompletableFuture.failedStage(new IllegalArgumentException("Invalid input"));

        // 2. get() 메서드를 호출하여 예외 확인
        try {
            ((CompletableFuture<String>) failedStage).get(); // 형변환 필요
        } catch (ExecutionException e) {
            System.out.println("Exception caught: " + e.getCause().getMessage());
        }

        // 3. exceptionally()를 사용하여 예외 처리
        // exceptionally() 메서드를 사용하여 예외를 처리하고, 예외가 발생했을 때 대체 값을 반환
        failedStage.exceptionally(e -> {
            System.out.println("Exception handled: " + e.getMessage());
            return "Recovered value"; // 예외 복구
        }).thenAccept(result -> System.out.println("Result: " + result));

        // 4. handle()를 사용하여 예외 및 정상 결과 모두 처리
        failedStage.handle((result, e) -> {
            if (e != null) {
                System.out.println("Exception occurred: " + e.getMessage());
                return "Default value";
            } else {
                return result;
            }
        }).thenAccept(result -> System.out.println("Handled result: " + result));


        // 5. thenApply()는 실행되지 않음
        // thenApply()는 스테이지가 정상적으로 완료되었을 때만 실행됩니다. 
        // failedStage()로 생성된 스테이지는 실패 상태이므로 thenApply()는 실행되지 않습니다
        failedStage.thenApply(s -> s.toUpperCase()) // 이 부분은 실행되지 않음
                .thenAccept(s -> System.out.println("Uppercase: " + s));
    }
}
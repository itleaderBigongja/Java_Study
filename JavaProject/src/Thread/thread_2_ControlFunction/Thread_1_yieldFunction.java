package Thread.thread_2_ControlFunction;

import java.util.ArrayList;
import java.util.List;

class YieldThread extends Thread {

    private String name;

    public YieldThread(String name) {
        this.name = name;
    }

    // run() 메서드는 스레드가 실행될 때 수행되는 코드를 포함
    @Override
    public void run() {

        Thread thread = Thread.currentThread();	// 현재 실행 중인 스레드 객체를 가져옵니다.
        List<Integer> list = new ArrayList<>(); // 정수형 값을 저장할 ArrayList를 생성합니다.
        
        // 리스트에 0부터 9까지의 숫자를 추가, 이로 인해 list의 size는 0이 아니게 된다. 
        for (int i = 0; i < 10; i++) {
            list.add(i);
        }

        for (int i = 1; i <= list.size(); i++) {

            if (i % 2 == 0) {
                // 스레드를 양보, CPU 점유를 다른 스레드에게 넘김
				/** yield() 메서드를 호출한 스레드는 현재 실행 중인 상태에서 실행 가능(Runnable)상태로 변경
				 *  즉, 스레드는 CPU를 양보하고 실행 대기 큐로 다시 들어가 다른 스레드와 함께 스케줄러의 선택을 기다린다.
				 *  yield() 호출 후에도 스레드는 실행을 완전히 멈추는 것이 아니라, 스케줄러에 의해 다시 선택되어 실행될 가능성이 있다. 
				 * */
                Thread.yield();
                
                // 우선순위가 0이 되지 않도록 Math.max로 조정
                Thread.currentThread().setPriority(Math.max(1, list.size() - i));
                System.out.println(Thread.currentThread().getName() + " " + (list.size() - i) + "번째 위치로 우선순위 변경");
            }

            try {
                Thread.sleep(100);    // 스레드를 잠시 쉬게 함
            } catch (InterruptedException e) {
                throw new RuntimeException(thread.getName() + e + "종료!");
            }
        }
    }
}

public class Thread_1_yieldFunction {

    public static void main(String[] args) {

        // 스레드 객체 생성
        YieldThread thread1 = new YieldThread("Thread-1");

        // 스레드 시작
        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            // 스레드가 인터럽트 되면 메시지 출력
            throw new RuntimeException("메인 스레드 인터럽트" + e);
        }

        System.out.println("메인 스레드 종료!");
    }
}
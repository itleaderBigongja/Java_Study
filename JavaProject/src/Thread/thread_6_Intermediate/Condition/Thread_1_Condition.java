package Thread.thread_6_Intermediate.Condition;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread_1_Condition {

	// 공유 큐( 생성자와 소비자가 데이터를 주고 받을 때 사용 )
	private static Queue<Integer> items = new LinkedList<Integer>();
	
	/** ReentrantLock 객체 생성 ( 스레드 동기화를 위한 락 )
	 *  Lock 인터페이스를 구현하며, 스레드 동기화에 사용, 여러 스레드가 공유 자원에 접근할 때,
	 *  순서를 제어하고, 데이터 불일치를 방지 */ 
	private static Lock lock = new ReentrantLock();
	
	/** Condition 객체 생성( 락과 연결되어, 스레드 대기 및 신호에 사용 )
	 *  Lock객체(ReentrantLock)와 연결된 Condition 객체를 생성한다.
	 *  Condition 객체는 스레드를 특정 조건에서 대기시키거나 깨울때 사용 */ 
	private static Condition condition = lock.newCondition();
	
	
	public static void main(String[] args) {
		
		// 생성자 스레드 생성
		new Thread(() -> {
			
			/** 락 획득( 임계 영역 시작 )
			 *  thread가 lock을 획득, 다른 스레드가 이미 락을 가지고 있다면 현재 스레드는 락을 얻을 때까지 대기 */
			lock.lock();
			
			try {
				// 5개의 아이템을 생성
				for(int i = 0; i < 5; i++) {
					// 큐에 아이템 추가( offer() : 큐 안에 요소 삽입 )
					items.offer(i);
					System.out.println("Produced: " + i);
					
					/** 대기 중인 소비자 스레드에게 신호 전송( 하나의 스레드만 깨움 )
					 *  현재 Condition 객체에서 대기 중인 스레드 중 하나를 깨운다.
					 *  보통 생산자가 데이터 생산 후, 소비자에게 신호를 보낼 때 사용 */
					condition.signal(); // 싱글 스레드일 때는 가능하지만, 스레드가 2개이상일 경우는 signallAll()으로 모두가 깨어날수 있게 해야한다.
					
					// 잠시 대기
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			} finally {
				/** 락 해제( 임계 영역 종료 )
				 *  스레드가 락을  해제, 락을 해제해야 다른 스레드가 임계 영역에 접근할 수 있다.
				 *  finally 블록에서 해제하여 예외 발생시에도 락을 안전하게 해제 한다.*/ 
				lock.unlock();
			}
		}).start();
		
		
		// 소비자 스레드 생성
		new Thread(()-> {
			
			// 락 획득
			lock.lock();
			
			try {
				// 무한 루프
				while(true) {
					// 큐가 비어있으면 소비자 스레드를 대기 상태로 변경
					while(items.isEmpty()) {
						System.out.println("소비자 대기");
						
						/** 다른 스레드에서 signal()을 호출하기 전까지 대기
						 *  현재 스레드를 대기 상태로 만들고, 현재 스레드가 가지고 있는 락을 해제한다.
						 *  다른 스레드가 signal() 또는 signAll() 메서드를 호출할 때까지 스레드는 대기 상태를 유지
						 *  보통 소비자가 데이터를 기다릴 때 사용
						 **/ 
						condition.await();
					}
					
					// 큐에서 아이템 소비
					int item = items.poll();
					System.out.println("Consumed: " + item);
					
					// 큐가 비었으면 무한 루프 종료
					if(items.size() == 0) {
						break;
					}
				}
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
		}).start();
	}
}

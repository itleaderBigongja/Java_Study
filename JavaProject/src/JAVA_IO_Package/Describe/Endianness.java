package JAVA_IO_Package.Describe;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Endianness {

	public static void main(String[] args) {
        // 테스트할 정수
        int testInteger = 0x12345678;

        // ByteBuffer 생성 (4바이트, Integer 크기)
        ByteBuffer buffer = ByteBuffer.allocate(4);

        /** 시스템의 기본 바이트 순서 확인( default : LITTLE_ENDIAN )*/
        ByteOrder nativeOrder = ByteOrder.nativeOrder();
        System.out.println("시스템의 기본 바이트 순서: " + nativeOrder);

        // ByteBuffer에 엔디안 설정
        buffer.order(nativeOrder);

        // ByteBuffer에 정수 저장
        buffer.putInt(testInteger);

        // 버퍼 위치 초기화
        buffer.position(0);

        // 바이트 배열로 변환
        byte[] bytes = buffer.array();

        // 바이트 배열 출력 (엔디안 확인)
        System.out.print("바이트 배열(LITTLE_ENDIAN): ");
        for (byte b : bytes) {
            System.out.printf("%02X ", b); // 16진수 형태로 출력
        }
        
        System.out.println();
        
        
        /** 시스템의 빅 엔디안 바이트 순서 확인( BIG_ENDIAN ) */
        // ByteBuffer 생성(4바이트, Integer 크기)
        ByteBuffer buffer2 = ByteBuffer.allocate(4);
        
        // 빅 엔디안으로 명시적 설정
        buffer2.order(ByteOrder.BIG_ENDIAN);
        
        // ByteBuffer 정수 저장
        buffer2.putInt(testInteger);
        
        // 버퍼 위치 초기화
        buffer2.position(0);
        
        // 바이트 배열로 변환 
        byte[] bytes2 = buffer2.array();
        
        // 바이트 배열 출력( 엔디안 확인 )
        System.out.print("바이트 배열(BIG_ENDIAN): ");
        for(byte b : bytes2) {
        	System.out.printf("%02X ", b);
        }
        System.out.println();
    }
}

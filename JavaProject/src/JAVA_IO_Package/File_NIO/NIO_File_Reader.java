package JAVA_IO_Package.File_NIO;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIO_File_Reader {

	public static void main(String[] args) {
		 // try-with-resources 구문: 자원을 자동으로 해제해주는 구문
        // 사용 이유: FileChannel을 사용한 후 자동으로 close()를 호출하여 자원 누수를 방지하기 위해 사용
        try (
             // FileChannel: 파일에서 데이터를 읽고 쓰는 데 사용하는 채널
            // 사용 이유: 지정된 파일에서 데이터를 읽어오기 위해 사용
             FileChannel channel = FileChannel.open(Paths.get("src/fileDir/sample_1.txt"), StandardOpenOption.READ)
             
         ) {
           // ByteBuffer: 데이터를 일시적으로 저장하는 버퍼, 여기서는 바이트 버퍼
           // 사용 이유: 파일에서 읽어들인 데이터를 임시로 저장하기 위해 사용
           ByteBuffer buffer = ByteBuffer.allocate(1024); // 1024 byte 크기의 버퍼 생성
            // while 루프: 파일 채널에서 데이터를 읽어 버퍼에 저장하고, 더 이상 읽을 데이터가 없을 때까지 반복
           while(channel.read(buffer) > 0){ // 파일 채널에서 읽은 바이트 수를 확인하고, 0보다 크면 데이터가 있는 것으로 간주
        	   
        	   // ByteBuffer.flip(): 읽기 모드로 버퍼를 준비
               // 사용 이유: 버퍼에 데이터를 읽은 후, 읽은 데이터를 사용하기 전에 버퍼의 위치를 조정하기 위해 사용
               buffer.flip(); // 버퍼의 읽기 준비
               
               // while 루프 : 버퍼에 읽어들인 데이터를 한 바이트씩 출력
               // hasRemaining버퍼에 아직 읽어들일 데이터가 남아있다면 실행
               while(buffer.hasRemaining()){
            	   // Charset: 문자 인코딩 방식을 나타내는 클래스
                   // 사용 이유: 읽어들인 바이트 데이터를 특정 인코딩으로 디코딩하기 위해 사용
                   Charset charset = StandardCharsets.UTF_8; // UTF-8 인코딩 설정
                   
                   // 버퍼의 바이트 데이터를 String으로 디코딩
                   // 버퍼의 사이즈가 -1에서 -> 0으로 변환
                   String data = charset.decode(buffer).toString();
                   System.out.print(data); // 디코딩된 문자열을 콘솔에 출력
               }
               
               if(channel.read(buffer) == 0) {
            	   // ByteBuffer.clear(): 버퍼를 초기화하는 메서드
                   // 사용 이유: 버퍼를 초기화하여 새로운 데이터를 읽어들일 준비를 하기 위해 사용
            	   System.out.println("\n버퍼를 초기화 합니다.");
                   buffer.clear(); // 버퍼 초기화
               }
            }
        } catch (IOException e) {
            // IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
           // 사용 이유: 파일 입출력 시 발생할 수 있는 예외를 처리하기 위해 사용
           System.out.println(e.getMessage()); // 예외 메시지를 콘솔에 출력
        }
	}
}

package JAVA_IO_Package.File_NIO_Package;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class NIO_File_Copy {

	public static void main(String[] args) {
		
		// 복사할 원본 파일 경로
		String sourceFile = "src/fileDir/sourceFile/copy_example_source.txt";
		String destFile = "src/fileDir/destinationFile/copy_nio_example_destination.txt";
		
		// try-with-resources 구문: 자원을 자동으로 해제해주는 구문
		// 사용 이유: FileChannel을 사용한 후, 자동으로 close()를 호출하여 자원 누수를 방지하기 위해 사용
		try(
			// FileChannel: 파일에서 데이터를 읽고 쓰는 데 사용하는 채널
			// 사용 이유: 지정된 파일에서 데이터를 읽어오기 위해 사용
			FileChannel sourceChannel = FileChannel.open(Paths.get(sourceFile), StandardOpenOption.READ);
         
			// FileChannel: 파일에서 데이터를 읽고 쓰는 데 사용하는 채널
			// 사용 이유: 지정된 파일에 데이터를 쓰기 위해 사용
			// open():[ 파일 경로, 파일 생성, 파일 저장 ]
			FileChannel destChannel = FileChannel.open(Paths.get(destFile), StandardOpenOption.CREATE, StandardOpenOption.WRITE)
	    ){
			// FileChannel.transferTo(long position(전송이 시작될 파일 내 위치), long count, WritableByteChannel target)
			// -> 지정된 위치에서 시작하여 count 만큼의 바이트를 타겟으로 전송
			// 사용 이유: 원본 채널의 데이터를 타겟 채널로 효율적으로 전송하기 위해 사용
			sourceChannel.transferTo(0, sourceChannel.size(), destChannel); // 파일 복사
			System.out.println("파일 생성 후, 복사 완료!"); // 파일 복사 완료 메시지 출력(이미 생성된 경우, 복사만 처리)
		} catch (IOException e) {
			// IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
			// 사용 이유: 파일 입출력 시 발생할 수 있는 예외를 처리하기 위해 사용
			throw new RuntimeException(e.getMessage());
		}
	}
}

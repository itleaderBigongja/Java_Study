package JAVA_IO_Package.File_IO_Package;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class IO_File_Copy {

	public static void main(String[] args) {
		
		// 복사할 원본 파일 경로(source : 출발지)
		String sourceFile = "src/fileDir/sourceFile/copy_example_source.txt";
		
		// 복사될 대상 파일 경로(destination : 목적지)
		String destFile = "src/fileDir/destinationFile/copy_example_destination.txt";
		
		// try-with-resources 구문: 자원을 자동으로 해제해주는 구문
		// 사용 이유: FileInputStream, FileOutputStream을 사용한 후, 
		//          자동으로 close()를 호출하여 자원 누수를 방지하기 위해 사용
		try (
			// FileInputStream: 파일에서 바이트를 읽어들이는 데 사용하는 클래스
			// 사용 이유: 지정된 파일에서 바이트 데이터가 읽기 위해 사용
			FileInputStream sourceFileStream = new FileInputStream(sourceFile);
			
			// FileOutputStream: 파일에 바이트를 쓰는 데 사용하는 클래스
			// 사용 이유: 지정된 파일에 바이트 데이터를 쓰기 위해 사용
			FileOutputStream destinationFileStream = new FileOutputStream(destFile)
		){
			// 데이터를 임시로 저장할 바이트 배열 생성(1024바이트 크기)
			byte[] buffer = new byte[1024];
			// 실제로 읽어들인 바이트 수를 저장할 변수 확인
			int bytesRead;
			
			// while 루프: 파일에서 바이트를 읽어들이고, 읽어들인 바이트가 없을 때까지 반복
			// FileInputStream.read(byte[] b)
			// -> 파일에서 바이트를 읽어 byte 배열에 저장하고 읽어들인 바이트 수를 반환
			// 사용 이유: 파일에서 바이트 데이터를 읽어오기 위해 사용
			while((bytesRead = sourceFileStream.read(buffer)) > 0) {
				
				// FileOutputStream.write(byte[] b, int offset(데이터 읽기 시작부분), int len(바이트 수만큼 저장)
				// -> byte 배열의 데이터를 파일에 쓰기
				// 사용 이유: 파일에 바이트 데이터를 쓰기 위해 사용
				destinationFileStream.write(buffer, 0, bytesRead);
			}
			
			// 파일 복사 완료 메시지 출력
			System.out.println("파일 복사 완료!");;
		} catch (IOException e) {
			// IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
			// 사용 이유: 파일 입출력 시 발생할 수 있는 예외를 처리하기 위해 사용
			throw new RuntimeException(e.getMessage());
		}
	}
}

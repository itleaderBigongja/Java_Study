package JAVA_IO_Package.File_IO_Package;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class IO_File_Reader {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// try-with-resource 구문: 자원을 자동으로 해제해주는 구문
		// 사용 이유: BufferedReader와 FileReader를 사용한 후 자동으로 
		//          close()를 호출하여 자원 누수를 방지하기 위해 사용
		try(
			// FileReader: 파일로부터 문자를 읽어들이는 데 사용하는 클래스
			// 사용 이유: 지정된 파일로부터 문자를 읽기 위해 사용
			FileReader fileReader = new FileReader("src/fileDir/sample_1.txt");
			
			// BufferedReader : 버퍼링을 통해 문자를 효율적으로 읽어들이는 클래스
			// 사용 이유: FileReader로 부터 읽어들인 문자를 버퍼링하여 읽기 성능을 향상시키기 위해 사용
			BufferedReader bufferReader = new BufferedReader(fileReader);
		){
			// 한 줄씩 읽어들일 문자열 변수 선언
			String line;
			
			// while 루프: 파일의 모든 라인을 읽어들일 때까지 반복
			while((line = bufferReader.readLine()) != null) {
				// BufferedReader.readLine() : 한 줄의 문자를 읽어들이는 메서드
				// 사용 이유: 파일에서 한 줄씩 문자를 읽어오기 위해 사용
				System.out.println(line);
			}
			
			fileReader.close();
			bufferReader.close();
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());
			// IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
			// 사용 이유: 파일 입출력 시 발생할 수 있는 예외를 처리하기 위해 사용
		} 
		
//		String str = "가";
		for(byte a : "가".getBytes("UTF-8")) {
			System.out.print(Integer.toHexString(a));
		}
		
		try {
			// ISO-8859-1  1
			// US-ASCII    1
			// UTF-8       3
			// UTF-16      4
			// UTF-16BE    2
			// UTF-16LE    2
		
			
			System.out.println("가".getBytes("UTF-16BE")); 
			System.out.println("가".getBytes("UTF-16LE"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s = "한";
		System.out.println(s.getBytes("UTF-8").length);
	}
}
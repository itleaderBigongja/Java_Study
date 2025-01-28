package JAVA_IO_Package.File_NIO_Package;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

// 직렬화 가능한 객체를 만들기 위해 Serializable 인터페이스 구현
//클래스 버전 관리: 클래스 변경 시 역직렬화 오류 방지
	
class PersonNIO implements Serializable {
private static final long serialVersionUID = 1L;
	
	String name; // 이름
	int age;	 // 나이
	
	// 생성자: 객체 초기화
	public PersonNIO(String name, int age) {
		this.name = name;
		this.age  = age;
	}
	
	// toString() 메서드: 객체 정보를 문자열로 반환(디버깅 용이)
	@Override
	public String toString() {
		return "PersonNIO{name = " + name + ", age = " + age + "}";
	}
}

public class NIO_File_Serializable {
	
	public static void main(String[] args) {
		// 직렬화된 객체를 저장할 파일 경로
		String file = "src/fileDir/person_nio.dat";
		// 파일 경로를 Path 객체로 변환
		Path path   = Paths.get(file);
		
		// 객체 직렬화(NIO)
		try(FileChannel channel = 
				FileChannel.open(path,
						         StandardOpenOption.CREATE,
						         StandardOpenOption.WRITE)
				)
		{
			// 직렬화 객체 생성
			PersonNIO person = new PersonNIO("이순신", 40);
			
			// 객체를 바이트 배열로 변환(직렬화)
			// ByteArrayOutputStream: 바이트 배열을 쓰기 위한 스트림(메모리)
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			// ObjectOutputStream: 객체를 직렬화하여 바이트 스트림으로 변환하는 데 사용하는 클래스
			try(ObjectOutputStream oos = new ObjectOutputStream(bos)){
				// 객체 직렬화하여 스트림에 기록
				oos.writeObject(person);
			} 
			byte[] data = bos.toByteArray(); // 직렬화된 바이트 배열 가져오기

            // 데이터를 버퍼에 담아 채널로 출력
            // ByteBuffer.wrap(byte[] array): 바이트 배열을 기반으로 ByteBuffer 생성
            ByteBuffer buffer = ByteBuffer.wrap(data);
            channel.write(buffer); // 버퍼의 데이터를 채널을 통해 파일에 쓰기

            System.out.println("File NIO 객체 직렬화 완료!");
        } catch (IOException e) {
            // IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
            // 사용 이유: 파일 채널 접근 시 발생할 수 있는 예외를 처리하기 위해 사용
            System.out.println("File NIO 객체 직렬화 중 오류 발생: " + e.getMessage());
        }

        // 객체 역직렬화 (NIO)
        try (FileChannel channel = FileChannel.open(path, StandardOpenOption.READ)) {
            // ByteBuffer.allocate((int) channel.size()): 파일 크기만큼의 ByteBuffer 생성
            ByteBuffer buffer = ByteBuffer.allocate((int) channel.size());
            channel.read(buffer); // 채널에서 버퍼로 데이터 읽기
            buffer.flip(); // 버퍼를 읽기 모드로 변경

            byte[] data = buffer.array(); // 버퍼의 데이터를 바이트 배열로 가져오기
            // ByteArrayInputStream: 바이트 배열에서 데이터를 읽는 스트림 (메모리)
            ByteArrayInputStream bis = new ByteArrayInputStream(data);
             // ObjectInputStream: 바이트 스트림을 객체로 역직렬화하는 데 사용하는 클래스
            try (ObjectInputStream ois = new ObjectInputStream(bis)) {
                // ObjectInputStream.readObject()
                // -> 스트림에서 객체를 읽어 역직렬화하는 메서드
                PersonNIO deserializedPerson = (PersonNIO) ois.readObject(); // 객체 역직렬화
                System.out.println("File NIO 객체 역직렬화 완료: " + deserializedPerson);
            }
        } catch (IOException | ClassNotFoundException e) {
            // IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
            // ClassNotFoundException: 역직렬화 과정에서 클래스를 찾을 수 없을 때 발생하는 예외
            // 사용 이유: 객체 역직렬화 시 발생할 수 있는 예외를 처리하기 위해 사용
            System.out.println("File NIO 객체 역직렬화 중 오류 발생: " + e.getMessage());
        }
    }
}


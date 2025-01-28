package JAVA_IO_Package.File_IO_Package;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

// 직렬화 가능한 객체를 만들기 위해 Serializable 인터페이스를 구현
class Person implements Serializable{

	// 클래스 버전 관리
	private static final long serialVersionUID = 1L;
	
	String name;
	int age;
	
	public Person(String name, int age) {
		this.name = name;
		this.age  = age;
	}
	
	// toString() 메서드: 객체 정보를 문자열로 반환(디버깅 용이)
	@Override
	public String toString() {
		return "Person{" + "name" + name + "age" + age + "}";
	}
}
public class IO_File_Serializable {
	
	public static void main(String[] args) {
		
		// 직렬화된 객체를 저장할 파일 경로
		String file = "src/fileDir/serializable/person_io.dat";
		
		// try-with-resources 구문: 자원을 자동으로 해제해주는 구문
		// 사용 이유: FileOutputStream, ObjectOutputStream을 사용한 후,
		//          자동으로 close()를 호출하여 자원 누수를 방지하기 위해서 사용
		try (
			// FileOutputStream: 파일에 바이트를 쓰는 데 사용하는 클래스
			// 사용 이유: 객체를 직렬화하여 파일에 저장하기 위해 사용
			FileOutputStream fos = new FileOutputStream(file);
			
			// ObjectOutputStream: 객체를 직렬화 하여 바이트 스트림으로 변환하는 데 사용하는  클래스
			// 사용 이유: 객체를 직렬화하여 파일에 저장하기 위해 사용
			ObjectOutputStream oos = new ObjectOutputStream(fos);
		){
			// 직렬화할 객체 생성
			Person person = new Person("홍길동", 30);
			
			// ObjectOutputStream.writeObject(Object obj)
			// -> 객체를 직렬화하여 스트림에 쓰는 메서드
			// 사용 이유: 객체를 직렬화하여 파일에 저장하기 위해 사용
			oos.writeObject(person); // 객체 직렬화
			System.out.println("File I/O 객체 직렬화 완료!");
		} catch (IOException e) {
			// IOException: I/O 작업 중 발생하는 예외를 처리하는 클래스
			// 사용 이유: 객체 직렬화 시 발생할 수 있는 예외를 처리하기 위해 사용
			System.out.println("객체 직렬화 중 오류 발생: " + e.getMessage());
		}
		
		
		// 역직렬화
		try(
			// FileInputStream: 파일에서 바이트를 읽는 데 사용하는 클래스
			// 사용 이유: 직렬화된 객체를 파일에서 읽어오기 위해 사용
			FileInputStream fis = new FileInputStream(file);
				
			// ObjectInputStream: 바이트 스트림을 객체로 역직렬화하는 데 사용하는 클래스
			// 사용 이유: 직렬화된 객체를 파일에서 읽어와 객체로 복원하기 위해 사용
			ObjectInputStream ois = new ObjectInputStream(fis);
		) {
			// ObjectInputStream.readObject()
			// -> 스트림에서 객체를 읽어 역직렬화하는 메서드
			// 사용 이유: 직렬화된 객체를 파일에서 읽어 역직렬화하여 사용하기 위해 사용
			Person deserializabledPerson = (Person)ois.readObject(); // 객체 역직렬
			System.out.println("File I/O 객체 역직렬화 완료!");
		}catch(IOException | ClassNotFoundException e) {
			System.out.println("File I/O 객체 역직렬화 중 오류 발생: " + e.getMessage());
		}
	}
}

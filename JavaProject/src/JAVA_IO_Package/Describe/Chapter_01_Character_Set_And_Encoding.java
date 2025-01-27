package JAVA_IO_Package.Describe;

// Java I/O 패키지를 공부하기 전에 반드시 알아야 할 내용
public class Chapter_01_Character_Set_And_Encoding {
	
	/** 각 나라의 언어를 표현하기 위해 Character Set이 필요하다.
	 *  영어의 경우, 1Byte를 사용하며 1Byte는 8Bit이며 256개 표현할 수 있고 첫 번째 비트의 값은 0으로 되어있다.
	 *  한글의 경우, 2Byte가 필요하며 2Byte는 16Bit이며 65536개 표현을 할 수 있고, 첫 번째 비트의 값은 1로 두 번째 Byte까지 사용한다는 명시를 해준다.
	 *  
	 *  입력 하는 데이터는 Character Set으로 설정하지만, 컴퓨터가 읽을 때는 Encodig 작업을 해줘야 한다.
	 *  1Byte 일 때, [1Byte]Character Set은 ASCII 코드로 표현되어 있으며
	 *  2Byte 일 때, [1Byte]Character Set( ASCII 코드 ) + [1Byte]다른 Character Set을 가진다. 
	 * */
}

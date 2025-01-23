package Solid;
/** SRP( SingleResponsibilityPrinciple )
 *  - 개념 : 단일 책임 원칙
 *  - 하나의 클래스에는 책임이 필요하다.
 *  - 하나의 클래스 내부에는 메서드가 존재 하는데 이러한 메소드들 또한 균등한 Level을 가지면 좋다.
 *  - 예를 들어서 하나의 get()메서드를 생각을 해보면 좋다.
 * */
public class SRP_SingleResponsibilityPrinciple {

	private String srp1 = "";
	private String srp2 = "";
	private String srp3 = "";
	
	// 아래의 생성자는 어느 메소드에 존속 되지 않는 균등한 Level을 가진다.
	public SRP_SingleResponsibilityPrinciple(String srp1, String srp2, String srp3) {
		this.srp1 = srp1;
		this.srp2 = srp2;
		this.srp3 = srp3;
	}
	
	// 아래의 get() 메서드들은 균등한 Level을 가진다.
	public String getSrp1() {
		return srp1;
	}
	public String getSrp2() {
		return srp2;
	}
	public String getSrp3() {
		return srp3;
	}
}

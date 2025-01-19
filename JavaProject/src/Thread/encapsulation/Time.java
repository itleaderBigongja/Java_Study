package Thread.encapsulation;

public class Time {

	// 내부 주요 필드를 private로 지정하여 숨겼으므로 외부에서 조작할 수 없다.
	private boolean am;
	private int hour;		// 시간
	private int minute;		// 분
	private int second;		// 초
	
	Time(int hour, int minute, int second) {
		setHour(hour);
		setMinute(minute);
		setSecond(second);
	}
	
	public boolean isAm() {
		return am;
	}
	public void setAm(boolean am) {
		this.am = am;
	}
	
	public int getHour() {
		return hour;
	}
	
	public void setHour(int hour) {
		if(hour > 0 && hour <= 23) {
			this.hour = hour;
		}else {
			System.out.println("잘못된 시간을 입력\n입력된 시간 : " + hour);
		}
		
	}
	
	public int getMinute() {
		return minute;
	}
	public void setMinute(int minute) {
		
		if(minute > 0 && minute < 60) {
			this.minute = minute;
		}else {
			System.out.println("잘못된 시간을 입력\n 입력된 분 : " + minute);
		}
	}
	
	public int getSecond() {
		return second;
	}
	public void setSecond(int second) {
		this.second = second;
	}	
}

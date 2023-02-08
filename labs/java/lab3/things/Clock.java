package things;

public class Clock extends Thing {
	private int h, m, s;
	
	public Clock(int h, int m, int s) {
		this.h = h % 12;
		this.m = m % 60;
		this.s = s % 60;
	}
		
	public Clock() {
		this(0, 0, 0);
	}
	
	public void setTime(int h, int m, int s) {
		this.h = h % 12;
		this.m = m % 60;
		this.s = s % 60;
		System.out.println(toString());
	}
	
	public void setTime(int h, int m) {
		setTime(h, m, 0);
	}
	
	public void setTime(int h) {
		setTime(h, 0, 0);
	}
	
	@Override
	public String getName() {
		return "Clock (currrent time is " + h + ":" + m + ":" + s + ")";
	}
	
	/*@Override
	public String toString() {
		return "Clock (currrent time is " + h + ":" + m + ":" + s + ")";
	}*/
	/*
	@Override
	public int hashCode() {
		return toString().hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (getClass() != other.getClass()) {
			return false;
		}
		return hashCode() == other.hashCode();
	}*/
}
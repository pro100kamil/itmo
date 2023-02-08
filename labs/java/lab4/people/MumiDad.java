package people;

import exceptions.*;
import things.*;

public class MumiDad extends Person {
	private Ringable alarm;
	
	public MumiDad() {
		super("MumiDad");
	}
	
	public void setAlarm() {
		class Alarm implements Ringable { //звонок тревожной сигнализации
			public void ring() {
				System.out.println("Alarm rings");
			}
		}
		System.out.println("MumiDad sets Alarm");
		alarm = new Alarm();
	}
	
	public void workAlarm() {
		alarm.ring();
	}
}
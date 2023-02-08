import things.*;
import places.*;
import people.*;

public class Main {
	public static void main(String[] args) {
		Ondatr ondatr = new Ondatr();
		MumiDad mumidad = new MumiDad();
		Person person1 = new Person();
		Person[] all = {ondatr, mumidad, person1};
		//все встревожились и болтали
		for (Person person : all) {
			person.changeStatus(Status.ALARMED);
		}
		ondatr.talk(mumidad, person1);
		
		LivingRoom lr = new LivingRoom();
		Room room1 = new Room("room1");
		Room room2 = new Room("room2");
		Room room3 = new Room("room3");
		Home mumiHome = new Home(lr, room1, room2, room3);
		//все разошлись по своим комнатам
		ondatr.go(room1);
		mumidad.go(room2);
		person1.go(room3);
		
		mumiHome.makeSilence();  //тишина в доме
		
		KeroseneLamp kLamp = new KeroseneLamp();
		kLamp.changeStatus(true);  //керосиновая лампа горит
		
		Table table = new Table();
		table.addThings(kLamp);   //керосиновая лампа на столе
		lr.addThings(table);  //стол в гостиной
		Clock clock = new Clock();
		//часы пробивают время
		clock.setTime(12);
		clock.setTime(1);
		clock.setTime(3);
		
		ondatr.want("walk");
		ondatr.changeStatus(Status.SLEPPY);
		Veranda veranda = new Veranda();
		ondatr.go(veranda);
		ondatr.changeStatus(Status.AMAZEMENT);
		ondatr.stop();
		ondatr.say("What kinds of fiction are these!");
		Sofa sofa = new Sofa();
		ondatr.go(sofa);
	}
}
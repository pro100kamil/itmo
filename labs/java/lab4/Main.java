import things.*;
import places.*;
import people.*;
import exceptions.*;

public class Main {
	public static void main(String[] args) {
		//создадим всех персонажей
		Ondatr ondatr = new Ondatr();
		MumiDad mumidad = new MumiDad();
		Tofsla tofsla = new Tofsla();
		Vifsla vifsla = new Vifsla();
		
		Box box = new Box(); //Ящик безмолвствовал
		
		while (true) {//восстановливает жизни, чтоб хватило сил на передвижение ящика
			try {
				mumidad.moveThing(box); //Муми-папа вытянул ящик
				break;
			} catch (FewLivesException e) {
				System.out.println(e);
				mumidad.chill();
			}	
		}		
		
		//чтобы посмотреть, не похищены ли уже Тофсла и Вифсла

		//Тофсла и Вифсла спали
		tofsla.changeStatus(Status.SLEEPING); 
		vifsla.changeStatus(Status.SLEEPING); 
		
		Suitcase suitcase = new Suitcase(); //чемодан лежал
		
		Person[] all = {ondatr, mumidad, tofsla, vifsla};
		//все встревожились и болтали
		for (Person person : all) {
			person.changeStatus(Status.ALARMED);
		}
		ondatr.talk(mumidad, tofsla, vifsla);
		
		Home mumiHome = new Home();
		Home.LivingRoom lr = mumiHome.new LivingRoom();
		Home.Room room1 = mumiHome.new Room("room1");
		Home.Room room2 = mumiHome.new Room("room2");
		Home.Room room3 = mumiHome.new Room("room3");
		Home.Room room4 = mumiHome.new Room("room4");
		
		//все разошлись по своим комнатам
		ondatr.go(room1);
		mumidad.go(room2);
		tofsla.go(room3);
		vifsla.go(room4);
		
		mumiHome.makeSilence();  //тишина в доме
		
		KeroseneLamp kLamp = new KeroseneLamp();
		Table table = new Table();
		lr.addThings(table);  //стол в гостиной
		table.addThings(kLamp);   //керосиновая лампа на столе
		kLamp.changeStatus(true);  //керосиновая лампа горит
		
		Clock clock = new Clock();
		//часы пробивают время
		clock.setTime(12);
		clock.setTime(1);
		clock.setTime(3);
		
		ondatr.want("walk");  //Ондатру пришла нужда прогуляться на двор
		ondatr.changeStatus(Status.SLEPPY);
		Veranda veranda = new Veranda();
		Sofa sofa = new Sofa();
		
		ondatr.go(sofa, veranda); //пошёл на веранду
		//диван преградил путь
		ondatr.changeStatus(Status.AMAZEMENT);
		ondatr.stop();
		ondatr.say("What kinds of fiction are these!");
		
		//сработал звонок тревожной сигнализации, который установил Муми-папа
		mumidad.setAlarm();  //можно установить выше в коде, оставил тут для наглядности
		mumidad.workAlarm();
		
		mumiHome.makeNoise(); //крики, выстрелы в доме
				
		//все вооружённые
		mumidad.setWeapon(new Person.Weapon() {
			@Override 
			public String toString() {
				return "axes";
			}
		});
		tofsla.setWeapon(new Person.Weapon() {
			@Override 
			public String toString() {
				return "scissors";
			}
		});
		vifsla.setWeapon(new Person.Weapon() {
			@Override 
			public String toString() {
				return "stones";
			}
		});

		//ринулись в гостиную
		for (Person person : all) {
			if (person != ondatr) person.go(lr);
		}
		
		//в удивлении остановились перед Ондатром
		for (Person person : all) {
			if (person != ondatr) {
				person.changeStatus(Status.AMAZEMENT);
				person.stop();	
			}
		}
	}
}
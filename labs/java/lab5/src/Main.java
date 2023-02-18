import models.*;
import commands.*;
import managers.*;

import java.time.LocalDate;
import java.util.LinkedList;

public class Main {
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Нужно запускать файл только с одним аргументом (названием файла, откуда брать коллекцию)!!!");
			System.exit(0);
		}
		String fileName = args[0];  //"files/1.txt"

		JsonManager jManager = new JsonManager();
		
		
		//Worker andrey = new Worker("Andrey", new Coordinates(Integer.valueOf(1), Integer.valueOf(2)),
		//Float.valueOf(100), Position.MANAGER, Status.FIRED, new Person(LocalDate.of(2004, 2, 11), 188, "123456789"));
		LinkedList<Worker> ll = jManager.getLinkedListWorkerFromStrJson(FileManager.getFromFile(fileName));
		//LinkedList<Worker> ll = new LinkedList<>();
		//ll.add(andrey);
		//ll.add(andrey);
		
		//System.out.println(ll);
		
		//System.out.println(jManager.getStrJsonFromWorker(andrey));
		//System.out.println("------------");
		//System.out.println(jManager.getStrJsonFromLinkedListWorker(ll));
		//Worker www = jManager.getWorkerFromStrJson(FileManager.getFromFile(fileName));
	
		
		//InputManager inputManager = new InputManager();
		ConsoleManager consoleManager = new ConsoleManager();
		CollectionManager collectionManager = new CollectionManager(ll);
		CommandManager commandManager = new CommandManager();
		
		UserManager userManager = new UserManager(consoleManager, collectionManager, commandManager);
		userManager.run();
		
		/*//FileManager fManager = new FileManager();
		
		//JsonManager jManager = new JsonManager();
		
		//Worker www = jManager.getWorkerFromStrJson(fManager.getFromFile(fileName));
		
		InputManager inputManager = new InputManager();
		Worker w1 = inputManager.getWorker();
		fManager.writeTextOToFile(fileName, w1.toString());
		fManager.writeToFile(fileName, w1.toString());
		System.out.println();
		System.out.println(jManager.getStrJsonFromWorker(w1));
		w1.getInfo();
		CollectionManager collectionManager = new CollectionManager();
		collectionManager.add(www);
		/*collectionManager.add(w1);
		collectionManager.printInfo();
		collectionManager.printElements();
		System.out.println("--------------------------");
		Command command;
		while (true) {
			command = commandManager.getCommand(consoleManager.getNextStr());
			command.execute(collectionManager);
			System.out.println("--------------------------");
		}*/
		
		
		/*LinkedList<Worker> ll = new LinkedList<>();
		
		String fileName = args[0];
		System.out.println(fileName);
		
		inputManager new Worker("Kamil", new Coordinates(Integer.valueOf(1), Integer.valueOf(2)), Float.valueOf(2.5f), Position.MANAGER, Status.FIRED);
		Worker w2 = new Worker("Andrey", new Coordinates(Integer.valueOf(1), Integer.valueOf(2)), null, Position.MANAGER, Status.FIRED);
	
		ll.add(w1);
		ll.add(w2);
		ll.add(w2);
		ll.add(w2);
		//System.out.println(ll.get(2));
		System.out.println(ll);*/
	}
}

//javac -d classes -cp gson.jar Main.java models/*.java managers/*.java commands/*.java exceptions/*.java json_serializer/*.java
//java -cp classes;gson.jar Main files/1.json

//jar -cfm Main.jar Manifest.txt classes/Main.class classes/models/*.class classes/managers/*.class classes/commands/*.class classes/exceptions/*.class classes/json_serializer/*.class

//jar -cfm Main.jar Manifest.txt -C classes;gson.jar .
//jar -cfm Main.jar Manifest.txt -cp classes;gson.jar
//java -jar Main.jar

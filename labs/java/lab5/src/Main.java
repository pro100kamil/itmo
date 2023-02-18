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
		
		LinkedList<Worker> ll = jManager.getLinkedListWorkerFromStrJson(FileManager.getFromFile(fileName));
	
		ConsoleManager consoleManager = new ConsoleManager();
		CollectionManager collectionManager = new CollectionManager(ll);
		
		InputManager inputManager = new InputManager(consoleManager, collectionManager, fileName);
		inputManager.run();
	}
}

//javac -d classes -cp gson.jar Main.java models/*.java managers/*.java commands/*.java exceptions/*.java json_adapters/*.java
//java -cp classes;gson.jar Main files/1.json


//jar -cfm Main.jar Manifest.txt -C classes .
//java -jar Main.jar

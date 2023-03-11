import models.*;
import managers.*;

import java.util.LinkedList;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
        String fileName = System.getenv("fileNameCollection");  //файл, где хранится начальное состояние коллекции
        if (fileName == null)  {
            System.out.println("Нужно запускать файл с переменной окружения " +
                    "'fileNameCollection' (названием файла, откуда брать коллекцию)!!!");
            System.exit(0);
        }
        LinkedList<Worker> ll = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(fileName));

        TreeMap<String, LinkedList<Worker>> stepCollection =
                JsonManager.getStepCollectionFromStrJson(FileManager.getTextFromFile(CollectionHistory.getFileName()), ll);

        ConsoleManager consoleManager = new ConsoleManager();
        CollectionManager collectionManager = new CollectionManager(ll);
        CollectionHistory collectionHistory = new CollectionHistory(stepCollection);

        InputManager inputManager = new InputManager(consoleManager, collectionManager, collectionHistory, fileName);
        inputManager.run();
    }
}


//scp -P 2222 lab5.jar s367149@helios.cs.ifmo.ru:~/labs
//set fileNameCollection=main.json
//javac -d classes -cp gson.jar Main.java models/*.java managers/*.java commands/*.java exceptions/*.java jsonAdapters/*.java
//java -cp classes;gson.jar Main

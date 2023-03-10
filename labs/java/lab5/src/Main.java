import models.*;
import managers.*;

import java.util.LinkedList;
import java.util.TreeMap;

public class Main {

    public static void main(String[] args) {
//        if (args.length != 1) {
//            System.out.println("Нужно запускать файл только с одним аргументом (названием файла, откуда брать коллекцию)!!!");
//            System.exit(0);
//        }
        //String fileName = args[0];
        String fileName = System.getenv("fileNameWorker");  //файл, где хранится начальное состояние коллекции

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

//javac -d classes -cp gson.jar Main.java models/*.java managers/*.java commands/*.java exceptions/*.java json_adapters/*.java
//java -cp classes;gson.jar Main files/1.json

//jar -cfm Main.jar Manifest.txt -C classes .
//java -jar Main.jar

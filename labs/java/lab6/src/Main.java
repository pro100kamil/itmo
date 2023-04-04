import models.*;
import managers.*;

import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {
        String fileName = System.getenv("fileNameCollection");  //файл, где хранится начальное состояние коллекции
        if (fileName == null)  {
            System.out.println("Нужно запускать файл с переменной окружения " +
                    "'fileNameCollection' (названием файла, откуда брать коллекцию)!!!");
            System.exit(0);
        }
        LinkedList<Worker> start_ll = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(fileName));

        StandardConsole consoleManager = new StandardConsole();
        CollectionManager collectionManager = new CollectionManager(start_ll);
        CollectionHistory collectionHistory = new CollectionHistory();
        collectionHistory.setStart(start_ll);

        InputManager inputManager = new InputManager(consoleManager, collectionManager, collectionHistory, fileName);
        inputManager.run();
    }
}


//scp -P 2222 lab5.jar s367149@helios.cs.ifmo.ru:~/labs   //загрузка джарника на гелиос
//ssh -p 2222 s367149@helios.cs.ifmo.ru  //вход на гелиос
//export fileNameCollection=main.json    //создание переменной окружения

//javac -d classes -cp gson.jar Main.java models/*.java managers/*.java commands/*.java exceptions/*.java jsonAdapters/*.java
//java -cp classes;gson.jar Main

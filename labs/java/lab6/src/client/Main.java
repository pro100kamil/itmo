package client;

import client.managers.InputManager;
import common.models.Worker;
import managers.StandardConsole;
import server.managers.CollectionHistory;
import server.managers.CollectionManager;
import server.managers.FileManager;
import server.managers.JsonManager;

import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        StandardConsole console = new StandardConsole();

        InputManager inputManager = new InputManager(console);

        inputManager.run();

//        boolean fl = true;
//        while (fl) {
//            Client.run();
//        }
    }
}

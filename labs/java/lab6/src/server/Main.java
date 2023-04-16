package server;

import common.consoles.Console;
import common.consoles.StandardConsole;
import server.managers.ServerManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String host = System.getenv("host");
        int port = Integer.parseInt(System.getenv("port"));

        Configuration.setHost(host);
        Configuration.setPort(port);
        Configuration.setStartFileName("main.json");
        Configuration.setHistoryFileName("_.json");

        Console console = new StandardConsole();
        ServerManager serverManager = new ServerManager();
        try {
            serverManager.start();
            console.write("Сервер запущен");
        } catch (IOException e) {
            console.write(e.toString());
            console.write("Не получилось запустить сервер");
            return;
        }
        serverManager.run();
    }
}

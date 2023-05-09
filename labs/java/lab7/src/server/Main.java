package server;

import common.consoles.Console;
import common.consoles.StandardConsole;
import common.managers.ValidateManager;
import server.managers.ServerManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Не найден драйвер!");
            System.exit(1);
        }

        Console console = new StandardConsole();

        String host = System.getenv("host");
        String strPort = System.getenv("port");
        if (host == null || strPort == null || !ValidateManager.isInteger(strPort)) {
            console.write("Неправильные переменные окружения");
            return;
        }
        int port = Integer.parseInt(strPort);

        Configuration.setHost(host);
        Configuration.setPort(port);
        Configuration.setStartFileName("main.json");
        Configuration.setHistoryFileName("_.json");

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

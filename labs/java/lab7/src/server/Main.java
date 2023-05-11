package server;

import common.consoles.Console;
import common.consoles.FileConsole;
import common.consoles.StandardConsole;
import common.managers.ValidateManager;
import server.managers.PasswordManager;
import server.managers.ServerManager;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
//        System.out.println(PasswordManager.getHash("1"));
//        System.out.println(PasswordManager.getHash("2"));
//        System.out.println(PasswordManager.getHash("3"));
//        System.exit(228);
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Не найден драйвер!");
            System.exit(1);
        }

        Console console = new StandardConsole();

        String[] lines = new FileConsole("server_host_port.txt").getLines();
        if (lines.length != 2) {
            console.write("Некорректный файл server_host_port.txt");
            return;
        }

        Configuration.setHost(lines[0].trim());
        Configuration.setPort(Integer.parseInt(lines[1].trim()));

//        String host = System.getenv("host");
//        String strPort = System.getenv("port");
//        if (host == null || strPort == null || !ValidateManager.isInteger(strPort)) {
//            console.write("Неправильные переменные окружения");
//            return;
//        }
//        int port = Integer.parseInt(strPort);
//
//        Configuration.setHost(host);
//        Configuration.setPort(port);
//        Configuration.setHost("127.0.0.1");
//        Configuration.setPort(6969);


        Configuration.setStartFileName("main.json");
        Configuration.setHistoryFileName("_.json");

        String[] strings = new FileConsole("credentials.txt").getLines();
        if (strings.length != 3) {
            console.write("Некорректный файл credentials.txt");
            return;
        }

        Configuration.setDbUrl(strings[0].trim());
        Configuration.setDbLogin(strings[1].trim());
        Configuration.setDbPass(strings[2].trim());

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

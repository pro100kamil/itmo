package server;

import common.consoles.FileConsole;
import common.loggers.Logger;
import common.loggers.StandardLogger;
import server.managers.PasswordManager;
import server.managers.ServerManager;
import server.managers.databaseManagers.DDLManager;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Logger logger = new StandardLogger();

        logger.write("Логгер запущен");

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            logger.write("Не найден драйвер!");
            return;
        }

        String[] lines = new FileConsole("server_host_port.txt").getLines();
        if (lines.length != 2) {
            logger.write("Некорректный файл server_host_port.txt");
            return;
        }

        Configuration.setHost(lines[0].trim());
        Configuration.setPort(Integer.parseInt(lines[1].trim()));

        Configuration.setStartFileName("main.json");
        Configuration.setHistoryFileName("_.json");

        String[] strings = new FileConsole("credentials.txt").getLines();
        if (strings.length != 3) {
            logger.write("Некорректный файл credentials.txt");
            return;
        }

        Configuration.setDbUrl(strings[0].trim());
        Configuration.setDbLogin(strings[1].trim());
        Configuration.setDbPass(strings[2].trim());

//        try {
//            DDLManager ddlmn = new DDLManager(Configuration.getDbUrl(), Configuration.getDbLogin(), Configuration.getDbPass());
//            ddlmn.dropTables();
//            ddlmn.createTables();
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }

        ServerManager serverManager = new ServerManager();
        try {
            serverManager.start();
            logger.write("Сервер запущен");
            logger.writeSeparator();
        } catch (IOException e) {
            logger.write(e.toString());
            logger.write("Не получилось запустить сервер");
            return;
        }
        serverManager.run();
    }
}

package server.managers;

import common.consoles.Console;
import common.consoles.StandardConsole;
import common.models.User;
import common.models.Worker;
import common.network.requests.Request;
import common.network.responses.Response;
import common.network.responses.UpdateCollectionHistoryResponse;
import server.Configuration;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.LinkedList;

/**
 * Класс серверного менеджера.
 * Обрабатывает подключения от клиента. Отправляет ответ на запрос клиенту.
 */
public class ServerManager {
    private static final Console console = new StandardConsole();

    private final Server server;

    private CommandManager commandManager;

    public ServerManager() {
        server = new Server(Configuration.getHost(), Configuration.getPort());
        String dataFileName = Configuration.getStartFileName();

        // из файла:
//        LinkedList<Worker> startWorkers = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(dataFileName));
        // из бд:
        DatabaseManager databaseManager = new DatabaseManager(Configuration.getDbUrl(),
                Configuration.getDbLogin(),
                Configuration.getDbPass());  //pgpass
        LinkedList<Worker> startWorkers;
        try {
            startWorkers = (LinkedList<Worker>) databaseManager.loadWorkers();
        } catch (SQLException e) {
            System.out.println(e.toString());
            System.out.println("Загрузить коллекцию из базы данных не получилось");
            return;
        }
        User user = new User("user1", "user1");
        try {
            databaseManager.dropTables();
            databaseManager.createTables();
            databaseManager.addUser(user);
        } catch (SQLException e) {
            System.out.println(e);
        }
        CollectionManager collectionManager = new CollectionManager(databaseManager, user, startWorkers);

        CollectionHistory collectionHistory = new CollectionHistory();
        CollectionHistory.setDataFileName(dataFileName);
        collectionHistory.setStart(startWorkers);

        commandManager = new CommandManager(collectionManager, collectionHistory);
    }

    public void start() throws IOException {
        server.start();
    }

    public void writeRes(SocketChannel socketChannel, Response response) {
        try {
            server.writeObject(socketChannel, response);  //отправляем клиенту
        } catch (IOException e) {
            console.write("Не получилось передать данные клиенту");
        }
    }

    public void handlerSocketChannel(SocketChannel socketChannel) throws IOException {
        Request request;
        try {
            request = (Request) server.getObject(socketChannel); //получаем запрос от клиента

            //на основе запроса формируем ответ
            Response response = new RequestHandler(commandManager).requestHandler(request);

            //на UpdateCollectionHistoryRequest ответ не требуется
            if (!(response instanceof UpdateCollectionHistoryResponse)) {
                writeRes(socketChannel, response);  //отправляем ответ
            }
        } catch (IOException | ClassNotFoundException e) {
            console.write(e.toString());
            console.write("Принять данные не получилось");
            socketChannel.close();
        }
        catch (ClassCastException e) {
            console.write(e.toString());
            console.write("Передан неправильный тип данных");
        }
        finally {
            socketChannel.close();
        }
    }

    public void run() {
        SocketChannel socketChannel;
        while (true) {
            try {
                socketChannel = server.getSocketChannel();
                if (socketChannel == null) continue;
                handlerSocketChannel(socketChannel);
            } catch (IOException e) {
                console.write(e.toString());
            }
        }
    }
}
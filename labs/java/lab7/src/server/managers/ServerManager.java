package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
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
    private static final Logger logger = new StandardLogger();

    private final Server server;

    private final CommandManager commandManager;

    public ServerManager() {
        server = new Server(Configuration.getHost(), Configuration.getPort());
        String dataFileName = Configuration.getStartFileName();

        DatabaseManager databaseManager = new DatabaseManager(Configuration.getDbUrl(),
                Configuration.getDbLogin(),
                Configuration.getDbPass());  //pgpass
        LinkedList<Worker> startWorkers = null;
        try {
            startWorkers = (LinkedList<Worker>) databaseManager.loadWorkers();
        } catch (SQLException e) {
            logger.write("Загрузить коллекцию из базы данных не получилось");
            logger.writeError(e.toString());
            System.exit(1);
        }
        User user = new User("user1", "user1");
//        user.setId(1);

        try {
            databaseManager.dropTables();
            databaseManager.createTables();
//            databaseManager.addUser(user);
            new AuthManager().register(user);
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
            logger.write("Не получилось передать данные клиенту");
            logger.writeError(e.toString());
        }
    }

    public void handlerSocketChannel(SocketChannel socketChannel) throws IOException {
        Request request;
        try (socketChannel) {
            request = (Request) server.getObject(socketChannel); //получаем запрос от клиента

            logger.write("Получен запрос: " + request.getClass().getName());

            //на основе запроса формируем ответ
            Response response = new RequestHandler(commandManager).requestHandler(request);

            logger.write("Сформирован ответ на запрос: " + response.getClass().getName());

            //на UpdateCollectionHistoryRequest ответ не требуется
            if (!(response instanceof UpdateCollectionHistoryResponse)) {
                writeRes(socketChannel, response);  //отправляем ответ
            }
        } catch (IOException | ClassNotFoundException e) {
            logger.write("Принять данные не получилось");
            logger.writeError(e.toString());
        } catch (ClassCastException e) {
            logger.write("Передан неправильный тип данных");
            logger.writeError(e.toString());
        }
    }

    public void run() {
        SocketChannel socketChannel;
        while (true) {
            try {
                socketChannel = server.getSocketChannel();
                if (socketChannel == null) continue;
                logger.write("Новое подключение");
                handlerSocketChannel(socketChannel);
                logger.writeSeparator();
            } catch (IOException e) {
                logger.writeError(e.toString());
            }
        }
    }
}
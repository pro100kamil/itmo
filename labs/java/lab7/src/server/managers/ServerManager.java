package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.models.Worker;
import server.Configuration;
import server.managers.databaseManagers.WorkerDatabaseManager;
import server.managers.tasks.ReadRequestTask;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.concurrent.ForkJoinPool;

/**
 * Класс серверного менеджера.
 * Обрабатывает подключения от клиента. Отправляет ответ на запрос клиенту.
 */
public class ServerManager {
    private static final Logger logger = new StandardLogger();

    private final Server server;

    private CommandManager commandManager;

    public ServerManager() {
        server = new Server(Configuration.getHost(), Configuration.getPort());
    }

    public void start() throws IOException {
        WorkerDatabaseManager databaseManager = new WorkerDatabaseManager(Configuration.getDbUrl(),
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

        CollectionManager collectionManager = new BlockingCollectionManager(databaseManager, startWorkers);

        CollectionHistory collectionHistory = new CollectionHistory();
//        CollectionHistory.setDataFileName(dataFileName);
        collectionHistory.setStart(startWorkers);

        commandManager = new CommandManager(collectionManager, collectionHistory);

        server.start();
    }

    public void handlerSocketChannel(SocketChannel socketChannel) throws IOException {
        //многопоточка 1
        ReadRequestTask task = new ReadRequestTask(server, socketChannel, commandManager);

        ForkJoinPool pool = ForkJoinPool.commonPool();
        pool.invoke(task);

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
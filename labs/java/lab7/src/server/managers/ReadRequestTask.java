package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.network.requests.Request;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RecursiveAction;


public class ReadRequestTask extends RecursiveAction {
    private static final Logger logger = new StandardLogger();
    private final Server server;
    private final SocketChannel socketChannel;
    private final CommandManager commandManager;

    public ReadRequestTask(Server server, SocketChannel socketChannel, CommandManager commandManager) {
        this.server = server;
        this.socketChannel = socketChannel;
        this.commandManager = commandManager;
    }

    protected void compute() {
        try {
            //чтение
            Request request = (Request) server.getObject(socketChannel); //получаем запрос от клиента

            logger.write("Получен запрос: " + request.getClass().getName() +
                         ". От: " + request.getUser());

            //многопоточка 2
            HandlerRequestTask task = new HandlerRequestTask(request, server, socketChannel, commandManager);

            ExecutorService pool = Executors.newCachedThreadPool();
            pool.submit(task);

        } catch (IOException | ClassNotFoundException e) {
            logger.write("Принять данные не получилось");
            logger.writeError(e.toString());
        } catch (ClassCastException e) {
            logger.write("Передан неправильный тип данных");
            logger.writeError(e.toString());
        }
    }
}

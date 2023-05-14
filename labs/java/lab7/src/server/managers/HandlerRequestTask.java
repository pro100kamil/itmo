package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.network.requests.Request;
import common.network.responses.Response;

import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HandlerRequestTask implements Runnable {
    private static final Logger logger = new StandardLogger();
    private final Request request;
    private final Server server;
    private final SocketChannel socketChannel;
    private final CommandManager commandManager;

    public HandlerRequestTask(Request request, Server server, SocketChannel socketChannel, CommandManager commandManager) {
        this.request = request;
        this.server = server;
        this.socketChannel = socketChannel;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        //обработка (на основе запроса формируем ответ)
        Response response = new RequestHandler(commandManager).requestHandler(request);

        response.setUser(commandManager.getCollectionManager().getUser());

        logger.write("Сформирован ответ на запрос: " + response.getClass().getName());

        //многопоточка 3
        WriteResponseTask task = new WriteResponseTask(response, server, socketChannel);

        ExecutorService pool = Executors.newCachedThreadPool();
        pool.submit(task);
    }
}

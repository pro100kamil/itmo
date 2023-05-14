package server.managers;

import common.loggers.Logger;
import common.loggers.StandardLogger;
import common.network.responses.Response;
import common.network.responses.UpdateCollectionHistoryResponse;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class WriteResponseTask implements Runnable {
    private static final Logger logger = new StandardLogger();
    private final Response response;
    private final Server server;
    private final SocketChannel socketChannel;

    public WriteResponseTask(Response response, Server server, SocketChannel socketChannel) {
        this.server = server;
        this.socketChannel = socketChannel;
        this.response = response;
    }

    @Override
    public void run() {
        //отправка ответа

        //на UpdateCollectionHistoryRequest ответ не требуется
        if (!(response instanceof UpdateCollectionHistoryResponse)) {
            try {
                server.writeObject(socketChannel, response);  //отправляем клиенту
                logger.write("Отправлен ответ: " + response.getClass().getName());
            } catch (IOException e) {
                logger.write("Не получилось передать данные клиенту");
                logger.writeError(e.toString());
            }
        }
    }
}

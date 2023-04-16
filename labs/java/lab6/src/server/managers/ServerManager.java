package server.managers;

import common.consoles.Console;
import common.consoles.StandardConsole;
import common.managers.FileManager;
import common.models.Worker;
import common.requests.Request;
import common.responses.Response;
import server.Configuration;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

/**
 * Класс сервера, позволяет принимать запросы от клиента
 */
public class ServerManager {
    private static final Console console = new StandardConsole();

    private final Server server;

    private final CommandManager commandManager;

    public ServerManager() {
        server = new Server(Configuration.getHost(), Configuration.getPort());
        String dataFileName = Configuration.getStartFileName();

        LinkedList<Worker> startWorkers = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(dataFileName));
        CollectionManager collectionManager = new CollectionManager(startWorkers);

        CollectionHistory collectionHistory = new CollectionHistory();
        CollectionHistory.setDataFileName(dataFileName);
        collectionHistory.setStart(startWorkers);

        commandManager = new CommandManager(collectionManager, collectionHistory, dataFileName);
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

            writeRes(socketChannel, response);  //отправляем ответ
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
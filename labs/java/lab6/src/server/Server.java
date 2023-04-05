package server;

import common.commands.Command;
import common.models.Worker;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.consoles.StringConsole;
import server.managers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Server {
    private ServerSocketChannel serv;
    private SocketChannel socketChannel;
    private static final Console console = new StandardConsole();

    public void start(String host, int port) throws IOException {
        serv = ServerSocketChannel.open();
        serv.bind(new InetSocketAddress(host, port));
    }

    public Object getObject() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
        return ois.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        oos.writeObject(obj);
    }

    public void close() throws IOException {
        serv.close();
    }

    public void run() {
        String dataFileName = "main.json";

        CollectionManager collectionManager = new CollectionManager();
        LinkedList<Worker> start_ll = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(dataFileName));
        collectionManager.setWorkers(start_ll);

        CollectionHistory collectionHistory = new CollectionHistory();
        CommandManager commandManager = new CommandManager(collectionManager,collectionHistory);

        try {
            start("127.0.0.1", 6969);
            console.write("Сервер запущен");
            while (true) {
                try {
                    socketChannel = serv.accept();
                    StringConsole strConsole = new StringConsole();

                    Command command = (Command) getObject(); //получаем команду от клиента

                    collectionManager.sortByName();  //сортируем коллекцию по имени

                    command.setConsole(strConsole);
                    collectionManager.setConsole(strConsole);

                    command.setHistory(commandManager.getHistory());
                    command.setCollectionHistory(collectionHistory);
                    command.setCollectionManager(collectionManager);
                    command.setDataFileName(dataFileName);

                    commandManager.executeCommand(command);  //выполняем её

                    String strRes = strConsole.getAllText();
                    try {
                        //отправляем клиенту
                        writeObject(strRes);
                        socketChannel.close();
                    }
                    catch (IOException e) {
                        console.write("Не получилось передать данные клиенту");
                    }
                } catch (IOException | ClassNotFoundException e) {
                    console.write(e.toString());
                    console.write("Принять данные не получилось");
                } catch (ClassCastException e) {
                    console.write(e.toString());
                    console.write("Передан неправильный тип данных");
                }
            }
        } catch (IOException e) {
            console.write(e.toString());
            console.write("Не получилось запустить сервер");
        }
    }
}

package server;

import managers.StringConsole;
import server.managers.CommandManager;
import common.commands.Command;
import managers.Console;
import managers.StandardConsole;
import common.models.Worker;
import server.managers.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Server {
    private static ServerSocketChannel serv;
    private static SocketChannel socketChannel;
    private final static Console console = new StandardConsole();

    public static void start(String host, int port) throws IOException {
        serv = ServerSocketChannel.open();
        serv.bind(new InetSocketAddress(host, port));

    }

    public static Object getObject() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
        return ois.readObject();
    }

    static void writeObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        oos.writeObject(obj);
    }

    public static void close() throws IOException {
        serv.close();
    }

    public static void main(String[] args) {
        CollectionManager collectionManager = new CollectionManager();
        CollectionHistory collectionHistory = new CollectionHistory();
        CommandManager commandManager = new CommandManager(collectionManager,collectionHistory);

        try {
            start("127.0.0.1", 6969);
            while (true) {
                try {
                    socketChannel = serv.accept();
                    StringConsole strConsole = new StringConsole();
                    //получаем команду от клиента
                    Command command = (Command) getObject();

                    command.setConsole(strConsole);
//                    command.setHistory(h);
                    command.setCollectionHistory(collectionHistory);

                    //сортируем по имени
                    collectionManager.sortByName();

                    command.setCollectionManager(collectionManager);
//                    command.setDataFileName(dataFileName);
                    //выполняем её
                    commandManager.executeCommand(command);

                    String strRes = strConsole.getAllText();
                    //получаем текущее состояние коллекции
                    /*LinkedList<Worker> workers = collectionManager.getLinkedList();
                    //сортируем по имени
                    workers = workers.stream().sorted(Comparator.comparing(Worker::getName))
                            .collect(Collectors.toCollection(LinkedList::new));*/
                    try {
                        //отправляем клиенту
                        writeObject(strRes);
                        socketChannel.close();
                    }
                    catch (IOException e) {
                        console.write("Не получилось передать данные клиенту");
                    }
//                    close();
//                    return;
// утром сделать передачу команд на сервер и ответ на команду
// пока что принимает один запрос и отправляет ответ на этот запрос
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

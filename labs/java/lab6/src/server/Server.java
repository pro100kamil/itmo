package server;

import com.google.gson.stream.JsonToken;
import common.commands.Command;
import common.commands.Update;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.models.Worker;
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
        serv.configureBlocking(false);
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

        LinkedList<Worker> start_ll = JsonManager.getLinkedListWorkerFromStrJson(FileManager.getTextFromFile(dataFileName));

        CollectionManager collectionManager = new CollectionManager(start_ll);

        CollectionHistory collectionHistory = new CollectionHistory();
        CollectionHistory.setDataFileName(dataFileName);
        collectionHistory.setStart(start_ll);

        CommandManager commandManager = new CommandManager(collectionManager, collectionHistory);

        try {
            start("127.0.0.1", 6969);
            console.write("Сервер запущен");
            while (true) {
                try {
                    socketChannel = serv.accept();
                    if (socketChannel == null) continue;
                    StringConsole strConsole = new StringConsole();

                    Command command = (Command) getObject(); //получаем команду от клиента

                    collectionManager.sortByName();  //сортируем коллекцию по имени

                    command.setConsole(strConsole);
                    collectionManager.setConsole(strConsole);

                    command.setHistory(commandManager.getHistory());
                    command.setCollectionHistory(collectionHistory);
                    command.setCollectionManager(collectionManager);
                    command.setDataFileName(dataFileName);

                    //если отправлена команда update без работника, то мы должны провести её серверную валидацию
                    if (command instanceof Update && ((Update) command).getWorker() == null) {
                        try {
                            ((Update) command).serverValidateArgs(command.getArgs());
                            strConsole.write("Введите информацию о работнике.");
                        }
                        catch (NonExistentId e) {
                            strConsole.write(e.toString());
                        }
                    }
                    else {
                        commandManager.executeCommand(command);  //выполняем её
                    }
                    String strRes = strConsole.getAllText();
                    if (strRes.equals("")) strRes = "Команда выполнилась успешно";
                    try {
                        writeObject(strRes);  //отправляем клиенту
                        socketChannel.close();
                    } catch (IOException e) {
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

package client;

import common.commands.Command;
import managers.Console;
import managers.StandardConsole;
import common.models.Coordinates;
import common.models.Position;
import common.models.Status;
import common.models.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client {
    private static Socket sock;
    private final static Console console = new StandardConsole();

    public static void start(String host, int port) throws IOException {
        sock = new Socket(host, port);
    }

    public static Object getObject() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
        return ois.readObject();
    }

    public static void writeObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(obj);
    }

//    public static void run(Command command, String[] args) {
    public static void run(Command command) {
        try {
            start("127.0.0.1", 6969);
            try {
                writeObject(command);  //отправляем команду
                try {
                    //получаем результат в виде строки
                    String strRes = (String) getObject();
                    console.write(strRes);
                    /*
                    //получаем результат в виде коллекции
                    LinkedList<Worker> workers = (LinkedList<Worker>) getObject();

                    workers.forEach(worker -> console.write(worker.toString()));*/

                    close();
                } catch (IOException | ClassNotFoundException e) {
                    console.write(e.toString());
                    console.write("Принять данные не получилось");
                } catch (ClassCastException e) {
                    console.write(e.toString());
                    console.write("Передан неправильный тип данных");
                }
            } catch (IOException e) {
                console.write("Не получилось передать данные на сервер");
            }
        } catch (IOException e) {
            console.write(e.toString());
            console.write("Не получилось подключиться к серверу");
        }
    }

    public static void close() throws IOException {
        sock.close();
    }

    public static void main(String[] args) {
        Worker max = new Worker("Max", new Coordinates(10, 100),
                9999F, null, null, null);

        Worker sasha = new Worker("Sasha", new Coordinates(1, -9),
                null, Position.HEAD_OF_DEPARTMENT, Status.FIRED, null);

        Worker andrey = new Worker("Andrey", new Coordinates(1, -9),
                123F, Position.HEAD_OF_DEPARTMENT, Status.FIRED, null);


        LinkedList<Worker> workers = new LinkedList<>(List.of(max, sasha, andrey));


//        writeObject("127.0.0.1", 6969, "filter_by_salary 100");
    }
}

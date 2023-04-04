package server;

import managers.Console;
import managers.StandardConsole;
import models.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Server {
    private static final Console console = new StandardConsole();
    public static LinkedList<Worker> getWorkers(String host, int port) {
        try {
            ServerSocketChannel serv = ServerSocketChannel.open();
            serv.bind(new InetSocketAddress(host, port));

            SocketChannel socketChannel = serv.accept();

            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());

            Object obj = ois.readObject();
            LinkedList<Worker> workers = (LinkedList<Worker>) obj;
            workers.forEach(System.out::println);

            serv.close();
            return workers;

        } catch (IOException | ClassNotFoundException e) {
            console.write("Принять данные не получилось");
        } catch (ClassCastException e) {
            console.write("Передан неправильный тип данных");
        }
        return new LinkedList<>();
    }
    public static void main(String[] args) {
        LinkedList<Worker> workers = getWorkers("127.0.0.1", 6969);
        workers.forEach(System.out::println);
    }
}

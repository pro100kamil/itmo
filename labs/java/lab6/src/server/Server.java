package server;

import models.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocketChannel serv = ServerSocketChannel.open();
            serv.bind(new InetSocketAddress("127.0.0.1", 6969));

            SocketChannel socketChannel = serv.accept();

            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());

            Object obj = ois.readObject();
            if (obj instanceof LinkedList) {
                LinkedList<Worker> workers = (LinkedList<Worker>) obj;
                workers.forEach(System.out::println);
            }
//            LinkedList<Worker> workers = (LinkedList<Worker>)ois.readObject();
//            Worker worker = (Worker) ois.readObject();


            serv.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

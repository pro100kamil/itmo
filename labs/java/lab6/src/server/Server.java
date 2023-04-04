package server;

import models.Worker;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws Exception {
        try {
            ServerSocketChannel serv = ServerSocketChannel.open();
            serv.bind(new InetSocketAddress("127.0.0.1", 6969));

            SocketChannel socketChannel = serv.accept();

            ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());

            Worker worker = (Worker) ois.readObject();

            System.out.println(worker);

            serv.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
//
//
//        int port = 6969;
//        ServerSocketChannel serv = ServerSocketChannel.open();
//        serv.configureBlocking(false);
//        SocketAddress addr = new InetSocketAddress(port);
//
//        serv.bind(addr);
//
//        while (true) {
//            try {
//                SocketChannel sock = serv.accept();
//                if (sock != null) {
//                    sock.configureBlocking(false);
//                    ByteBuffer buffer = ByteBuffer.allocate(1024);
//                    sock.read(buffer);
//
//                    ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(buffer.array()));
//                    Object worker = ois.readObject();
////        Worker worker = (Worker) ois.readObject();
//                    System.out.println(worker);
//
//                }
//            }
//            catch (Exception e) {
//                System.out.println(e);
//            }
//        }
//        serv.close();
    }
}

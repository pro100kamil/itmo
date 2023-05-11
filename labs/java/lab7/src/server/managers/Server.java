package server.managers;

import common.consoles.Console;
import common.consoles.StandardConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Класс сервера, позволяет принимать запросы от клиента
 */
public class Server {
    private final String host;
    private final int port;
    private ServerSocketChannel serv;
    private static final Console console = new StandardConsole();

    public Server(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        serv = ServerSocketChannel.open();
        serv.configureBlocking(false);
        System.out.println(host);
        System.out.println(port);
        serv.bind(new InetSocketAddress(host, port));
    }

    public SocketChannel getSocketChannel() throws IOException {
        return serv.accept();
    }

    public Object getObject(SocketChannel socketChannel) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socketChannel.socket().getInputStream());
        return ois.readObject();
    }

    public void writeObject(SocketChannel socketChannel, Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(socketChannel.socket().getOutputStream());
        oos.writeObject(obj);
    }

    public void close() throws IOException {
        serv.close();
    }
}

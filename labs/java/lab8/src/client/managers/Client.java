package client.managers;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Класс клиента, позволяет отправлять объекты на сервер, получать объекты с сервера.
 */
public class Client {
    private final String host;
    private final int port;
    private Socket sock;

    public Client(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws IOException {
        sock = new Socket(host, port);
    }

    public Object getObject() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
        return ois.readObject();
    }

    public void writeObject(Object obj) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(obj);
    }

    public void close() throws IOException {
        sock.close();
    }
}

package client;

import models.Coordinates;
import models.Worker;

import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        Worker worker = new Worker("Max", new Coordinates(10, 100),
                9999F, null, null, null);

        String host = "127.0.0.1";
        int port = 6969;
        Socket sock = new Socket(host, port);
        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
        oos.writeObject(worker);
        sock.close();
    }
}

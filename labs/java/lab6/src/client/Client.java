package client;

import models.Coordinates;
import models.Position;
import models.Status;
import models.Worker;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;

public class Client {
    public static void main(String[] args) throws Exception {
        Worker max = new Worker("Max", new Coordinates(10, 100),
                9999F, null, null, null);

        Worker sasha = new Worker("Sasha", new Coordinates(1, -9),
                null, Position.HEAD_OF_DEPARTMENT, Status.FIRED, null);

        LinkedList<Worker> workers = new LinkedList<>(List.of(max, sasha));

        String host = "127.0.0.1";
        int port = 6969;

        Socket sock = new Socket(host, port);

        ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

        oos.writeObject(workers);

        sock.close();
    }
}

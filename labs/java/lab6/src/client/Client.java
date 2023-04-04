package client;

import models.Coordinates;
import models.Position;
import models.Status;
import models.Worker;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Client {

    static void writeObject(String host, int port, Object obj) {
        try {
            Socket sock = new Socket(host, port);

            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());

            oos.writeObject(obj);

            sock.close();
        }
        catch (IOException e) {
            System.out.println("Не получилось передать данные на сервер");
        }
    }

    public static void main(String[] args) throws Exception {
        Worker max = new Worker("Max", new Coordinates(10, 100),
                9999F, null, null, null);

        Worker sasha = new Worker("Sasha", new Coordinates(1, -9),
                null, Position.HEAD_OF_DEPARTMENT, Status.FIRED, null);

        Worker andrey = new Worker("Andrey", new Coordinates(1, -9),
                123F, Position.HEAD_OF_DEPARTMENT, Status.FIRED, null);


        LinkedList<Worker> workers = new LinkedList<>(List.of(max, sasha, andrey));

        //сортируем по имени
        workers = workers.stream().sorted(Comparator.comparing(Worker::getName))
                .collect(Collectors.toCollection(LinkedList::new));

        writeObject("127.0.0.1", 6969, workers);
    }
}

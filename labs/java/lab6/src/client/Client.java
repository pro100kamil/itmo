package client;

import common.commands.Command;
import common.consoles.Console;
import common.consoles.StandardConsole;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private Socket sock;
    private static final Console console = new StandardConsole();

    public void start(String host, int port) throws IOException {
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

    public void run(Command command) {
        try {
            start("127.0.0.1", 6969);
            try {
                writeObject(command);  //отправляем команду
                try {
                    String strRes = (String) getObject();  //получаем результат в виде строки

                    console.write(strRes);

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

    public void close() throws IOException {
        sock.close();
    }
}

package interlayer;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class Interlayer {
    private static final String host = "127.0.0.1";
    private static final int port = 5050;
    private static final ServerConfiguration[] servers = {new ServerConfiguration("127.0.0.1", 1111),
            new ServerConfiguration("127.0.0.1", 1313),
            new ServerConfiguration("127.0.0.1", 6969)};

    public void run() {
        Server server;
        try {
            server = new Server(host, port);
            server.start();
        }
        catch (IOException e) {
            System.out.println("Не получилось запустить прослойку");
            return;
        }
        System.out.println("Прослойка запущена");
        SocketChannel socketChannel;
        while (true) {
            try {
                //принимаем объект
                socketChannel = server.getSocketChannel();
                if (socketChannel == null) continue;
                Object request = server.getObject(socketChannel);

                //перебрать серверы
                for (ServerConfiguration serverConfiguration : servers) {
                    try {
                        //попытаться отправить объект на этот сервер
                        Client client = new Client(serverConfiguration.getHost(), serverConfiguration.getPort());
                        client.start();
                        client.writeObject(request);
                        //получить ответ от сервера и отправить его клиенту
                        Object response = client.getObject();
                        server.writeObject(socketChannel, response);
                        client.close();
                        break;
                    }
                    catch (IOException e) {
                        continue;
                    }
                }
                socketChannel.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(e.toString());
//                continue;
            }
        }
    }
}

package client;

import java.io.OutputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) throws Exception {
        byte arr[] = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Socket sock;
        OutputStream os;
        String host = "127.0.0.1"; int port = 6969;
        sock = new Socket(host, port);
        os = sock.getOutputStream();
        os.write(arr);
    }
}

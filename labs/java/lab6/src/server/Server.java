package server;

import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class Server {
    public static void main(String[] args) throws Exception {
        byte arr[] = new byte[10];
        int len = arr.length;
        int port = 6969;
        ServerSocketChannel serv = ServerSocketChannel.open();
        SocketAddress addr = new InetSocketAddress(port);

        serv.bind(addr);

        SocketChannel sock = serv.accept();
        ByteBuffer buf = ByteBuffer.wrap(arr);
        sock.read(buf);

        for (int j = 0; j < len; j++) {
            arr[j] *= 2;
        }

        buf.flip();
        sock.write(buf);

        for (int j = 0; j < len; j++) {
            System.out.println(arr[j]);
        }
    }
}

package client;

import client.managers.ClientManager;
import client.managers.InputManager;
import common.consoles.StandardConsole;

public class Main {
    public static void main(String[] args) {
        StandardConsole console = new StandardConsole();

        String host = System.getenv("host");
        int port = Integer.parseInt(System.getenv("port"));

        InputManager inputManager = new InputManager(console);

        inputManager.run(new ClientManager(host, port));
    }
}

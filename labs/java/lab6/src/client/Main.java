package client;

import client.managers.InputManager;
import common.consoles.StandardConsole;

public class Main {
    public static void main(String[] args) {
        StandardConsole console = new StandardConsole();

        String host = System.getenv("host");
        int port = Integer.parseInt(System.getenv("port"));

        Configuration.setHost(host);
        Configuration.setPort(port);

        InputManager inputManager = new InputManager(console);

        inputManager.run();
    }
}

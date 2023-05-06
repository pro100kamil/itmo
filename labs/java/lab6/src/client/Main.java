package client;

import client.managers.InputManager;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.managers.ValidateManager;

public class Main {
    public static void main(String[] args) {
        Console console = new StandardConsole();

        String host = System.getenv("host");
        String strPort = System.getenv("port");
        if (host == null || strPort == null || !ValidateManager.isInteger(strPort)) {
            console.write("Неправильные переменные окружения");
            return;
        }
        int port = Integer.parseInt(strPort);

        Configuration.setHost(host);
        Configuration.setPort(port);

        InputManager inputManager = new InputManager(console);

        inputManager.run();
    }
}

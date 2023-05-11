package client;

import client.managers.InputManager;
import common.consoles.Console;
import common.consoles.FileConsole;
import common.consoles.StandardConsole;
import common.managers.ValidateManager;

public class Main {
    public static void main(String[] args) {
        Console console = new StandardConsole();

        String[] lines = new FileConsole("client_host_port.txt").getLines();
        if (lines.length != 2) {
            console.write("Некорректный файл client_host_port.txt");
            return;
        }

        Configuration.setHost(lines[0].trim());
        Configuration.setPort(Integer.parseInt(lines[1].trim()));

        InputManager inputManager = new InputManager(console);

        inputManager.run();
    }
}

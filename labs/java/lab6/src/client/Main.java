package client;

import client.managers.InputManager;
import common.consoles.StandardConsole;

public class Main {
    public static void main(String[] args) {
        StandardConsole console = new StandardConsole();

        InputManager inputManager = new InputManager(console);

        inputManager.run();
    }
}

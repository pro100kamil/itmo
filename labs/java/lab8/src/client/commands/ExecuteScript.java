package client.commands;

import client.managers.ClientManager;
import common.consoles.FileStringConsole;
import common.exceptions.WrongCommandArgsException;
import client.managers.InputManager;
import common.managers.ValidateManager;
import common.consoles.Console;
import common.consoles.FileConsole;
import common.models.UserRole;

/**
 * Команда execute_script file_name.
 * Выполняет команды из файла.
 */
public class ExecuteScript extends ClientCommand {
    private static int depth = 0, maxDepth = 5;
    private ClientManager clientManager;

    public ExecuteScript() {
        super("execute_script", "выполняет команды из файла", false, true);
        minUserRole = UserRole.ADMIN;
    }

    public static void setMaxDepth(int maxDepth) {
        ExecuteScript.maxDepth = maxDepth;
    }

    public ClientManager getClientManager() {
        return clientManager;
    }

    public void setClientManager(ClientManager clientManager) {
        this.clientManager = clientManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isFile(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            String fileName = args[0];
            if (depth >= maxDepth) {
                console.write("Превышена глубина рекурсии!");
                return;
            }
            console.write("Начинаем обработку файла с командами");
            depth++;
            FileStringConsole fileConsole = new FileStringConsole(fileName);
            InputManager newInputManager = new InputManager(fileConsole, clientManager);
            newInputManager.run();
            console.write(fileConsole.getAllText());
            depth--;
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

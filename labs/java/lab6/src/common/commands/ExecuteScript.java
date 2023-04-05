package common.commands;

import common.exceptions.WrongCommandArgsException;
import client.managers.InputManager;
import client.managers.ValidateManager;
import common.consoles.Console;
import common.consoles.FileConsole;

/**
 * Команда execute_script file_name.
 * Выполняет команды из файла.
 */
public class ExecuteScript extends Command {
    private static int depth = 0, maxDepth = 5;

    public ExecuteScript() {
        super("execute_script", "выполняет команды из файла");
    }

    public static void setMaxDepth(int maxDepth) {
        ExecuteScript.maxDepth = maxDepth;
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
            Console fileConsole = new FileConsole(fileName);
            InputManager newInputManager = new InputManager(fileConsole);
            newInputManager.run();
            depth--;
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

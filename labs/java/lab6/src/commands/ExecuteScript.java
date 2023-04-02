package commands;

import exceptions.WrongCommandArgsException;
import managers.FileConsole;

import managers.*;

/**
 * Команда execute_script file_name.
 * Выполняет команды из файла.
 */
public class ExecuteScript extends Command {
    private static int depth = 0, maxDepth = 5;
    private final String dataFileName;
    private final CollectionManager collectionManager;

    public ExecuteScript(String dataFileName, CollectionManager collectionManager, Console console) {
        super("execute_script", "выполняет команды из файла", console);
        this.dataFileName = dataFileName;
        this.collectionManager = collectionManager;
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
            InputManager newInputManager = new InputManager(fileConsole, collectionManager, null, dataFileName);
            newInputManager.run();
            depth--;
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

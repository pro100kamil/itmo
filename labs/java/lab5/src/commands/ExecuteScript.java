package commands;

import exceptions.NotUniqueIdException;
import exceptions.WrongCommandArgsException;
import managers.FileConsole;

import models.Worker;
import managers.*;

/**
 * Команда execute_script file_name
 */
public class ExecuteScript extends Command {
    private static int depth = 0, maxDepth = 5;
    private String dataFileName;
    private CollectionManager collectionManager;


    public ExecuteScript(String dataFileName, CollectionManager collectionManager) {
        super("execute_script", "выполняет команды из файла");
        this.dataFileName = dataFileName;
        this.collectionManager = collectionManager;
    }

    public static void setMaxDepth(int maxDepth) {
        ExecuteScript.maxDepth = maxDepth;
    }

    /**
     * Выполняет команды из файла
     */
    public void execute(String[] args) {
        try {
            if (args.length != 1 || !ValidateManager.isFile(args[0])) {
                throw new WrongCommandArgsException();
            }
            String fileName = args[0];
            if (depth >= maxDepth) {
                System.out.println("Превышена глубина рекурсии!");
                return;
            }
            System.out.println("Начинаем обработку файла с командами");
            depth++;
            Console fileConsole = new FileConsole(fileName);
            InputManager newInputManager = new InputManager(fileConsole, collectionManager, dataFileName);
            newInputManager.run();
            depth--;
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }
    }
}

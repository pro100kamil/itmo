package commands;

import managers.FileConsole;

import models.Worker;
import managers.*;

/**
 * Команда execute_script file_name
 */
public class ExecuteScript extends Command {
    private static int depth = 0, maxDepth = 5;
    private String fileName, dataFileName;
    private CollectionManager collectionManager;


    public ExecuteScript(String fileName, CollectionManager collectionManager, String dataFileName) {
        super("execute_script", "выполняет команды из файла");
        this.fileName = fileName;
        this.collectionManager = collectionManager;
        this.dataFileName = dataFileName;
    }

    /**
     * Выполняет команды из файла
     */
    public void execute() {
        try {
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
        } catch (NullPointerException e) {

        }
    }
}

package managers;

import commands.*;
import exceptions.EndInputException;
import exceptions.EndInputWorkerException;
import models.Worker;
import exceptions.NoSuchCommandException;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс для запуска команд, для сохранения истории
 */
public class CommandManager {
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private String dataFileName;
    private LinkedList<Command> history = new LinkedList<>();
    private TreeMap<String, Command> strCommands = new TreeMap<>(); //название команды, объект класса этой команды

    public CommandManager(CollectionManager collectionManager, InputManager inputManager, String dataFileName) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.dataFileName = dataFileName;
        Command[] allCommands= {new Help(null), new Info(collectionManager),
                new Show(collectionManager), new Add(collectionManager),
                new Update(collectionManager), new Remove(collectionManager), new Clear(collectionManager),
                new Save(dataFileName, collectionManager),
                new ExecuteScript(dataFileName, collectionManager), new Exit(), new Head(collectionManager),
                new RemoveGreater(collectionManager),
                new History(history), new FilterBySalary(collectionManager),
                new PrintDescending(collectionManager), new PrintFieldDescendingPosition(collectionManager)};

        Command helpCommand = new Help(allCommands);

        for (Command command : allCommands) {
            if (command instanceof Help) strCommands.put(command.getName(), helpCommand);
            else strCommands.put(command.getName(), command);
        }

    }

    /**
     * Выполняет команду по строчному представлению команды
     *
     * @param strCommand - строка с командой (команда с аргументами)
     */
    public void executeCommand(String strCommand) throws NoSuchCommandException {
        String[] subsCommand = strCommand.split("\\s+");
        strCommand = subsCommand[0];
        if (!strCommands.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }
        Command res = strCommands.get(strCommand);
        if (res instanceof CommandWithWorker) {
            Worker worker = inputManager.getWorker();
            if (worker == null) return;
            ((CommandWithWorker) res).setWorker(worker);
        }
        if (res instanceof ExecuteScript) {
            try {
                int maxDepth = inputManager.getInteger("Введите максимальную глубину рекурсии: ", true);
                ((ExecuteScript) res).setMaxDepth(maxDepth);
            }
            catch (EndInputException | EndInputWorkerException e) {
                return;
            }
        }
        String[] args = new String[subsCommand.length - 1];
        for (int i = 1; i < subsCommand.length; i++) {
            args[i - 1] = subsCommand[i];
        }
        res.execute(args);
        history.add(res);
    }
}

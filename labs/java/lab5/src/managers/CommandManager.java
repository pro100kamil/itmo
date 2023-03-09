package managers;

import commands.*;
import exceptions.*;
import models.Worker;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Класс для запуска команд, для сохранения истории
 */
public class CommandManager {
    private Console console;
    private CollectionManager collectionManager;
    private InputManager inputManager;
    private String dataFileName;
    private LinkedList<Command> history = new LinkedList<>();
    private TreeMap<String, Command> strCommands = new TreeMap<>(); //название команды, объект класса этой команды

    public CommandManager(CollectionManager collectionManager, InputManager inputManager, String dataFileName) {
        this.collectionManager = collectionManager;
        this.inputManager = inputManager;
        this.dataFileName = dataFileName;
        console = inputManager.getConsole();
        Command[] allCommands= {new Help(null, console), new Info(collectionManager, console),
                new Show(collectionManager, console), new Add(collectionManager, console),
                new Update(collectionManager, console), new Remove(collectionManager, console), new Clear(collectionManager, console),
                new Save(dataFileName, collectionManager, console),
                new ExecuteScript(dataFileName, collectionManager, console), new Exit(console), new Head(collectionManager, console),
                new RemoveGreater(collectionManager, console),
                new History(history, console), new FilterBySalary(collectionManager, console),
                new PrintDescending(collectionManager, console), new PrintFieldDescendingPosition(collectionManager, console)};

        Command helpCommand = new Help(allCommands, console);

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
        String[] args = new String[subsCommand.length - 1];
        for (int i = 1; i < subsCommand.length; i++) {
            args[i - 1] = subsCommand[i];
        }

        if (!strCommands.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }
        Command res = strCommands.get(strCommand);
        if (res instanceof CommandWithWorker) {
            try {
                ((CommandWithWorker) res).validateArgs(args);
            }
            catch (WrongCommandArgsException | NonExistentId e) {
                console.write(e.toString());
                return;
            }
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
        res.execute(args);
        history.add(res);
    }
}

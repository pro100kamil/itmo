package client.managers;

import common.commands.*;
import common.exceptions.*;
import common.models.Worker;
import common.consoles.Console;
import common.consoles.StandardConsole;

import java.util.TreeMap;

/**
 * Класс для запуска команд, для сохранения истории
 */
public class CommandManager {
    private final Console console;
    private final InputManager inputManager;
    private final TreeMap<String, Command> strCommands = new TreeMap<>(); //название команды, объект класса этой команды

    public CommandManager(InputManager inputManager) {
        this.inputManager = inputManager;
        console = inputManager.getConsole();
        Command[] allCommands = {new Help(null), new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new ExecuteScript(), new Exit(), new Head(),
                new RemoveGreater(),
                new History(), new FilterBySalary(),
                new PrintDescending(),
                new PrintFieldDescendingPosition(),
                new Rollback()
        };

        Command helpCommand = new Help(allCommands);

        for (Command command : allCommands) {
            if (command instanceof Help) strCommands.put(command.getName(), helpCommand);
            else strCommands.put(command.getName(), command);
        }
    }

    /**
     * Получает экземпляр класса команды по строчному представлению команды
     *
     * @param strCommand - строка с командой (команда с аргументами)
     * @return Map.Entry<Command, String[]> - корректная команда и строковые аргументы команды
     */
//    public Map.Entry<Command, String[]> getCommand(String strCommand) throws NoSuchCommandException,
    public Command getCommand(String strCommand) throws NoSuchCommandException,
            WrongCommandArgsException,
            NonExistentId,
            EndInputException, EndInputWorkerException {
        String[] subsCommand = strCommand.split("\\s+");
        strCommand = subsCommand[0];
        String[] args = new String[subsCommand.length - 1];
        System.arraycopy(subsCommand, 1, args, 0, subsCommand.length - 1);

        if (!strCommands.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }
        Command res = strCommands.get(strCommand);
        res.validateArgs(args);
        if (res instanceof CommandWithWorker) {
            Worker worker = inputManager.getWorker();
            ((CommandWithWorker) res).setWorker(worker);
        }
        if (res instanceof ExecuteScript) {
            //макс глубина рекурсии спрашивается только тогда, когда мы работаем со стандартным вводом
            if (console instanceof StandardConsole) {
                int maxDepth = inputManager.getInteger("Введите максимальную глубину рекурсии: ", true);
                ExecuteScript.setMaxDepth(maxDepth);
            }
        }
        res.setArgs(args);
        return res;
//        return new AbstractMap.SimpleEntry<>(res, args);
    }
}

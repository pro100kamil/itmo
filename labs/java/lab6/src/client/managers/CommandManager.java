package client.managers;

import common.commands.AbstractCommand;
import common.commands.ExecuteScript;
import common.consoles.Console;
import common.exceptions.*;
import common.models.Worker;
import common.consoles.StandardConsole;

import java.util.TreeMap;

/**
 * Реализация класса CommandManager для клиентской части.
 * Класс для получения команды из её строчного представления.
 */
public class CommandManager {
    private final InputManager inputManager;
    private final Console console;

    private final TreeMap<String, AbstractCommand> strCommands = new TreeMap<>(); //название команды, объект класса этой команды

    public CommandManager(InputManager inputManager, AbstractCommand[] allCommands) {
        this.inputManager = inputManager;
        console = inputManager.getConsole();
        for (AbstractCommand command : allCommands) {
            strCommands.put(command.getName(), command);
        }
    }

    /**
     * Получает экземпляр класса команды по строчному представлению команды
     *
     * @param strCommand - строка с командой (команда с аргументами)
     * @return Command - корректная команда (строковые аргументы команды внутри)
     */
    public AbstractCommand getCommand(String strCommand) throws NoSuchCommandException,
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
        AbstractCommand res = strCommands.get(strCommand);
        res.validateArgs(args);  //клиентская валидация
        if (res.isWithWorker()) {
            //у update работник запросится только тогда, когда пройдёт серверная проверка
            Worker worker = inputManager.getWorker();
            ((server.commands.CommandWithWorker) res).setWorker(worker);
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
    }
}

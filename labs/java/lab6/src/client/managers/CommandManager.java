package client.managers;

import client.commands.Exit;
import client.commands.Help;
import common.commands.AbstractCommand;
import client.commands.ExecuteScript;
import common.consoles.Console;
import common.exceptions.*;
import common.models.Worker;
import common.consoles.StandardConsole;

import java.util.TreeMap;

/**
 * Реализация класса CommandManager для клиентской части.
 * Занимается получением команды из её строчного представления.
 */
public class CommandManager {
    private final InputManager inputManager;
    private final Console console;
    private static final Help help = new Help();
    private static final AbstractCommand[] clientCommands = {new Exit(), new ExecuteScript(), help};
    private final TreeMap<String, AbstractCommand> strCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    public CommandManager(InputManager inputManager, AbstractCommand[] allCommands) {
        this.inputManager = inputManager;
        console = inputManager.getConsole();
        for (AbstractCommand command : allCommands) {
            strCommands.put(command.getName(), command);
        }
        for (AbstractCommand command : clientCommands) {
            strCommands.put(command.getName(), command);
        }
        help.setCommands(strCommands.values().toArray(new AbstractCommand[0]));
    }

    public void setWorker(AbstractCommand command) throws EndInputWorkerException, EndInputException {
        Worker worker = inputManager.getWorker();
        command.setWorker(worker);
    }

    /**
     * Получает экземпляр класса команды по строчному представлению команды.
     * Если аргументы команды некорректные - бросаются соответствующие исключения.
     *
     * @param strCommand - строка с командой (команда с аргументами)
     * @return AbstractCommand - корректная команда (строковые аргументы команды внутри)
     */
    public AbstractCommand getCommand(String strCommand) throws NoSuchCommandException,
            WrongCommandArgsException,
            NonExistentId,
            EndInputException, EndInputWorkerException {
        String[] subsCommand = strCommand.split("\\s+");
        strCommand = subsCommand[0];
        String[] args = new String[subsCommand.length - 1];  //args = subsCommand[1:]
        System.arraycopy(subsCommand, 1, args, 0, subsCommand.length - 1);

        if (!strCommands.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }
        AbstractCommand command = strCommands.get(strCommand);
        command.validateArgs(args);  //клиентская валидация
//        if (command.isWithWorker()) {
//            setWorker(command);
//        }
        if (command instanceof ExecuteScript) {
            //макс глубина рекурсии спрашивается только тогда, когда мы работаем со стандартным вводом
            if (console instanceof StandardConsole) {
                int maxDepth = inputManager.getInteger("Введите максимальную глубину рекурсии: ", true);
                ExecuteScript.setMaxDepth(maxDepth);
            }
        }
        command.setArgs(args);
        return command;
    }
}

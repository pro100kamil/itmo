package client.managers;

import client.commands.*;
import common.commands.AbstractCommand;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.exceptions.*;

import java.util.TreeMap;

/**
 * Реализация класса CommandManager для клиентской части.
 * Занимается получением команды из её строчного представления.
 */
public class CommandManager {
    private final InputManager inputManager;
    private final Console console;
    private static final Help help = new Help();
    private static final AbstractCommand[] clientCommands = {new Exit(),
            new ExecuteScript(),
            new UserInfo(),
            help};
    private final TreeMap<String, AbstractCommand> strCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    public CommandManager(InputManager inputManager, AbstractCommand[] allCommands) {
        this.inputManager = inputManager;
        console = inputManager.getConsole();
        setCommands(allCommands);
    }

    public void setCommands(AbstractCommand[] serverCommands) {
        strCommands.clear();
        for (AbstractCommand command : serverCommands) {
            strCommands.put(command.getName(), command);
        }
        for (AbstractCommand command : clientCommands) {
            strCommands.put(command.getName(), command);
        }
        help.setCommands(strCommands.values().toArray(new AbstractCommand[0]));
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
        if (command instanceof ClientCommand) {
            command.validateArgs(args);  //клиентская валидация
        }
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

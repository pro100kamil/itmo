package client.managers;

import client.commands.*;
import common.commands.AbstractCommand;
import common.commands.ClientCommandDescription;
import common.commands.CommandDescription;
import common.consoles.Console;
import common.consoles.StandardConsole;
import common.exceptions.*;
import common.models.UserRole;

import java.io.IOException;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * Реализация класса CommandManager для клиентской части.
 * Занимается получением команды из её строчного представления.
 */
public class CommandManager {
    private final InputManager inputManager;
    private final Console console;
    private static final Help help = new Help();
    private final ClientCommand[] clientCommands = {new Exit(),
            new ExecuteScript(),
            new UserInfo(),
            help};

    private final TreeMap<String, ClientCommand> strToClientCommand = new TreeMap<>(
            Arrays.stream(clientCommands).collect(Collectors.toMap(ClientCommand::getName, command -> command)));

    private final TreeMap<String, CommandDescription> strToCommandDescription = new TreeMap<>();
    //название команды: объект класса обёртки этой команды

    public CommandManager(InputManager inputManager) {
        this.inputManager = inputManager;
        console = inputManager.getConsole();

        for (AbstractCommand command : clientCommands) {
            strToCommandDescription.put(command.getName(), new ClientCommandDescription(command));
        }
        help.setCommands(strToCommandDescription.values().toArray(new CommandDescription[0]));
    }

    public void setCommands(CommandDescription[] serverCommandDescriptions, UserRole userRole) {
        strToCommandDescription.clear();
        for (CommandDescription command : serverCommandDescriptions) {
            strToCommandDescription.put(command.getName(), command);
        }
        for (AbstractCommand command : clientCommands) {
            if (command.getMinUserRole().ordinal() <= userRole.ordinal()) {
                strToCommandDescription.put(command.getName(), new ClientCommandDescription(command));
            }
        }
        help.setCommands(strToCommandDescription.values().toArray(new CommandDescription[0]));
    }

    public void setCommandsForUnauthorizedUser(CommandDescription[] serverCommandDescriptions) {
        strToCommandDescription.clear();
        for (CommandDescription command : serverCommandDescriptions) {
            strToCommandDescription.put(command.getName(), command);
        }
        for (AbstractCommand command : clientCommands) {
            if (!command.isOnlyUsers()) {
                strToCommandDescription.put(command.getName(), new ClientCommandDescription(command));
            }
        }
        help.setCommands(strToCommandDescription.values().toArray(new CommandDescription[0]));
    }

    /**
     * Получает экземпляр класса команды по строчному представлению команды.
     * Если аргументы команды некорректные - бросаются соответствующие исключения.
     *
     * @param strCommand - строка с командой (команда с аргументами)
     * @return CommandDescription - корректная команда (строковые аргументы команды внутри)
     */
    public CommandDescription getCommandDescription(String strCommand) throws NoSuchCommandException,
            WrongCommandArgsException,
            NonExistentId,
            EndInputException, EndInputWorkerException,
            UnavailableCommandException, UnavailableModelException {
        String[] subsCommand = strCommand.split("\\s+");
        strCommand = subsCommand[0];
        String[] args = new String[subsCommand.length - 1];  //args = subsCommand[1:]
        System.arraycopy(subsCommand, 1, args, 0, subsCommand.length - 1);

        if (!strToCommandDescription.containsKey(strCommand)) { //если нет такой команды
            throw new NoSuchCommandException();
        }
        CommandDescription commandDescription = strToCommandDescription.get(strCommand);

        if (commandDescription.isOnlyUsers() && inputManager.getClientManager().getUser() == null) {
            throw new UnavailableCommandException();
        }

        if (commandDescription instanceof ClientCommandDescription) {
            ClientCommand command = strToClientCommand.get(commandDescription.getName());
            command.validateArgs(args);  //клиентская валидация

            if (command instanceof ExecuteScript) {
                //макс глубина рекурсии спрашивается только тогда, когда мы работаем со стандартным вводом
                if (console instanceof StandardConsole) {
                    int maxDepth = inputManager.getInteger("Введите максимальную глубину рекурсии: ", true);
                    ExecuteScript.setMaxDepth(maxDepth);
                    ((ExecuteScript) command).setClientManager(inputManager.getClientManager());
                }
            }
        }

        commandDescription.setArgs(args);
        return commandDescription;
    }

    /**
     * Выполняет клиентскую команду (аргументы уже хранятся внутри описания команды)
     *
     * @param commandDescription - описание клиентской команды
     */
    public void executeCommand(ClientCommandDescription commandDescription) throws IOException {
        ClientCommand command = strToClientCommand.get(commandDescription.getName());
        command.setArgs(commandDescription.getArgs());
        if (command instanceof UserInfo) {
            ((UserInfo) command).setUser(inputManager.getClientManager().getUser());
        }
        command.setConsole(console);
        command.execute(command.getArgs());

        //обновляем историю состояний коллекции
        inputManager.getClientManager().writeUpdateCollectionRequest();
    }
}

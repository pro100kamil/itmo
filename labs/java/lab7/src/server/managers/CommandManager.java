package server.managers;

import common.commands.AbstractCommand;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import server.commands.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeMap;

/**
 * Реализация класса CommandManager для серверной части.
 * Задаёт все команды.
 * Занимается запуском команды, серверной валидацией сохранением истории.
 */
public class CommandManager {
    private static ServerCommand[] serverCommands;

    private final static TreeMap<String, ServerCommand> strCommands = new TreeMap<>();
    //название команды, объект класса этой команды

    private final CollectionManager collectionManager;
    private final CollectionHistory collectionHistory;
    private final String dataFileName;
    private final LinkedList<ServerCommand> history = new LinkedList<>();

    public CommandManager(CollectionManager collectionManager,
                          CollectionHistory collectionHistory, String dataFileName) {
        this.collectionManager = collectionManager;
        this.collectionHistory = collectionHistory;
        this.dataFileName = dataFileName;

        CommandManager.serverCommands = new ServerCommand[]{new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new Head(), new RemoveGreater(),
                new History(), new FilterBySalary(),
                new PrintDescending(),
                new PrintFieldDescendingPosition(),
                new Rollback(),
                new Auth(), new Register()
        };

        for (ServerCommand command : serverCommands) {
            strCommands.put(command.getName(), command);
        }
    }

    public static AbstractCommand[] getServerCommands() {
        return serverCommands;
    }

    public static AbstractCommand[] getAbstractCommands() {
        return Arrays.stream(serverCommands)
                .map(command -> new AbstractCommand(command.getName(),
                        command.getDescription(), command.isWithWorker()))
                .toArray(AbstractCommand[]::new);
    }

    public static ServerCommand getServerCommandFromAbstractCommand(AbstractCommand command) {
        ServerCommand serverCommand = strCommands.get(command.getName());
        serverCommand.setArgs(command.getArgs());
        serverCommand.setWithWorker(command.isWithWorker());
        serverCommand.setWorker(command.getWorker());
        return serverCommand;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CollectionHistory getCollectionHistory() {
        return collectionHistory;
    }

    public LinkedList<ServerCommand> getHistory() {
        return history;
    }

    /**
     * Проводит серверную валидацию команды (обычная валидация + проверка id, если надо)
     *
     * @param command - конкретная команда
     */
    public void serverValidateCommand(ServerCommand command) throws NonExistentId, WrongCommandArgsException {
        command.setCollectionManager(collectionManager);
        command.serverValidateArgs(command.getArgs());
    }

    /**
     * Добавить новое состояние в историю коллекции
     */
    public void addStateCollection() {
        collectionHistory.addStateCollection(collectionManager.getLinkedList());
    }

    /**
     * Выполняет команду (аргументы уже хранятся внутри команды)
     *
     * @param command - конкретная команда
     */
    public void executeCommand(ServerCommand command, StringConsole strConsole) {
        command.setConsole(strConsole);
        command.setHistory(getHistory());
        command.setCollectionHistory(collectionHistory);
        command.setCollectionManager(collectionManager);
        command.setDataFileName(dataFileName);

        String[] args = command.getArgs();
        //выполнение команды
        command.execute(args);
        //collectionHistory == null, если у нас работа с файлом
        if (collectionHistory != null) {
            //откат лишь отменяет команды, при нём не появляется новое состояние
            if (!(command instanceof Rollback)) {
                addStateCollection();
            }
            history.add(command);
        }
    }
}

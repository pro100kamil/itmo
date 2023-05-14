package server.managers;

import common.commands.AbstractCommand;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.UnavailableCommandException;
import common.exceptions.WrongCommandArgsException;
import server.commands.*;

import java.util.Arrays;
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
    private final LinkedList<ServerCommand> history = new LinkedList<>();

    public CommandManager(CollectionManager collectionManager,
                          CollectionHistory collectionHistory) {
        this.collectionManager = collectionManager;
        this.collectionHistory = collectionHistory;

        CommandManager.serverCommands = new ServerCommand[]{new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new Head(), new RemoveGreater(),
                new History(), new FilterBySalary(),
                new PrintDescending(),
                new PrintFieldDescendingPosition(),
                new Rollback(),
                new Auth(), new Register(), new Logout()
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
                        command.getDescription(), command.isWithWorker(), command.isOnlyUsers()))
                .toArray(AbstractCommand[]::new);
    }

    public static ServerCommand getServerCommandFromAbstractCommand(AbstractCommand command) {
        ServerCommand serverCommand = strCommands.get(command.getName());
        serverCommand.setArgs(command.getArgs());
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
     * Добавить новое состояние в историю коллекции
     */
    public void addStateCollection() {
        collectionHistory.addStateCollection(collectionManager.getLinkedList());
    }

    /**
     * Проводит валидацию серверной команды
     *
     * @param command - конкретная команда
     */
    public void serverValidateCommand(ServerCommand command) throws NonExistentId, WrongCommandArgsException,
            UnavailableCommandException {
        //если команда только для зарегистрированных пользователей, а текущий пользователь не вошёл в аккаунт,
        //то не даём ему провести валидацию и выполнить команду
        if (command.isOnlyUsers() && collectionManager.getUser() == null) {
            throw new UnavailableCommandException();
        }
        command.setCollectionManager(collectionManager);
        command.validateArgs(command.getArgs());
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

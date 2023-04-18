package server.managers;

import common.commands.AbstractCommand;
import common.consoles.StringConsole;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import server.commands.*;

import java.util.LinkedList;

/**
 * Реализация класса CommandManager для серверной части.
 * Задаёт все команды.
 * Занимается запуском команды, серверной валидацией сохранением истории.
 */
public class CommandManager {
    private static AbstractCommand[] allCommands;
    private final CollectionManager collectionManager;
    private final CollectionHistory collectionHistory;
    private final String dataFileName;
    private final LinkedList<Command> history = new LinkedList<>();

    public CommandManager(CollectionManager collectionManager,
                          CollectionHistory collectionHistory, String dataFileName) {
        this.collectionManager = collectionManager;
        this.collectionHistory = collectionHistory;
        this.dataFileName = dataFileName;

        CommandManager.allCommands = new AbstractCommand[]{new Info(),
                new Show(), new Add(),
                new Update(), new Remove(), new Clear(),
                new Head(), new RemoveGreater(),
                new History(), new FilterBySalary(),
                new PrintDescending(),
                new PrintFieldDescendingPosition(),
                new Rollback()
        };
    }

    public static AbstractCommand[] getAllCommands() {
        return allCommands;
    }

    public CollectionManager getCollectionManager() {
        return collectionManager;
    }

    public CollectionHistory getCollectionHistory() {
        return collectionHistory;
    }

    public LinkedList<Command> getHistory() {
        return history;
    }

    /**
     * Проводит серверную валидацию команды (обычная валидация + проверка id, если надо)
     *
     * @param command - конкретная команда
     */
    public void serverValidateCommand(Command command) throws NonExistentId, WrongCommandArgsException {
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
    public void executeCommand(Command command, StringConsole strConsole) {
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

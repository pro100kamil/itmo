package server.managers;

import common.commands.*;
import managers.Console;

import java.util.LinkedList;

/**
 * Класс для запуска команд, для сохранения истории
 */
public class CommandManager {
//    private final Console console;
    private final CollectionManager collectionManager;
    private final CollectionHistory collectionHistory;
    private final LinkedList<Command> history = new LinkedList<>();

    public CommandManager(CollectionManager collectionManager, CollectionHistory collectionHistory) {
        this.collectionManager = collectionManager;
        this.collectionHistory = collectionHistory;
//        this.console = console;
    }

    /**
     * Выполняет команду (аргументы уже хранятся внутри команды)
     *
     * @param command - конкретная команда
     */
    public void executeCommand(Command command) {
        String[] args = command.getArgs();
        //выполнение команды
        command.execute(args);
        //collectionHistory == null, если у нас работа с файлом
        if (collectionHistory != null) {
            //откат лишь отменяет команды, при нём не появляется новое состояние
            if (!(command instanceof Rollback)) {
                collectionHistory.addStateCollection(collectionManager.getLinkedList());
            }
            history.add(command);
        }
    }
}


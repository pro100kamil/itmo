package server.commands;

import common.commands.AbstractCommand;
import common.consoles.Console;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import server.managers.CollectionHistory;
import server.managers.CollectionManager;

import java.util.LinkedList;

/**
 * Абстрактный класс команды, которая выполняется на сервере.
 */
public abstract class ServerCommand extends AbstractCommand {
    protected Console console;

    protected CollectionManager collectionManager;
    protected CollectionHistory collectionHistory;
    protected String dataFileName;
    protected LinkedList<ServerCommand> history;

    protected ServerCommand(String name, String description) {
        super(name, description);
    }

    public void setConsole(Console console) {
        this.console = console;
    }

    public void setCollectionManager(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
    }

    public void setCollectionHistory(CollectionHistory collectionHistory) {
        this.collectionHistory = collectionHistory;
    }

    public void setDataFileName(String dataFileName) {
        this.dataFileName = dataFileName;
    }

    public void setHistory(LinkedList<ServerCommand> history) {
        this.history = history;
    }

    /**
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     * Эта проверка происходит на сервере.
     *
     * @param args - аргументы команды
     */
    public void serverValidateArgs(String[] args) throws NonExistentId, WrongCommandArgsException {
        validateArgs(args);
    }

    /**
     * Выполняет команду
     *
     * @param args - аргументы команды
     */
    public abstract void execute(String[] args);
}

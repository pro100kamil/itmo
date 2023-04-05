package common.commands;

import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import managers.Console;
import server.managers.CollectionHistory;
import server.managers.CollectionManager;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Абстрактный класс команды
 */
public abstract class Command implements Serializable {
    private final String name; //название команды
    private final String description;  //описание команды
    protected Console console;
    protected String[] args;  //аргументы при запуске команды

    protected CollectionManager collectionManager;
    protected CollectionHistory collectionHistory;
    protected String dataFileName;
    protected LinkedList<Command> history;

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

    public void setHistory(LinkedList<Command> history) {
        this.history = history;
    }

    protected Command(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    /**
     * Проверяет аргументы на корректность. При неправильных аргументах бросает соответсвующее исключение.
     *
     * @param args - аргументы команды
     */
    public abstract void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId;

    /**
     * Выполняет команду
     *
     * @param args - аргументы команды
     */
    public abstract void execute(String[] args);

    public void setArgs(String[] args) {
        this.args = args;
    }

    public String[] getArgs() {
        return args;
    }

    @Override
    public String toString() {
        return name + ": " + description;
    }
}

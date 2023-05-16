package server.commands;

import common.commands.AbstractCommand;
import common.consoles.Console;
import common.exceptions.NonExistentId;
import common.exceptions.WrongCommandArgsException;
import common.models.User;
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
    protected LinkedList<ServerCommand> history;

    protected User user;

    public ServerCommand(String name, String description, boolean withWorker, boolean onlyUsers) {
        super(name, description, withWorker, onlyUsers);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public void setHistory(LinkedList<ServerCommand> history) {
        this.history = history;
    }

    /**
     * Выполняет команду
     *
     * @param args - аргументы команды
     */
    public abstract void execute(String[] args);
}

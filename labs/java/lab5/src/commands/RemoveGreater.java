package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;
import managers.ValidateManager;
import models.Worker;
import managers.CollectionManager;

/**
 * Команда remove_greater
 */
public class RemoveGreater extends CommandWithWorker {
    private Worker worker;
    private CollectionManager collectionManager;

    public RemoveGreater(CollectionManager collectionManager, Console console) {
        super("remove_greater", "удаляет работников, которые больше заданного", console);
        this.collectionManager = collectionManager;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    /**
     * Удаляет работников, которые больше заданного
     */
    public void execute(String[] args) {
        collectionManager.removeGreater(worker);
    }

}

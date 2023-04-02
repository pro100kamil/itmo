package commands;

import exceptions.NonExistentId;
import exceptions.WrongCommandArgsException;
import managers.Console;
import managers.ValidateManager;
import models.Worker;
import managers.CollectionManager;

/**
 * Команда update.
 * Обновляет работника по id на основе заданного работника.
 */
public class Update extends CommandWithWorker {
    private Worker worker;
    private final CollectionManager collectionManager;

    public Update(CollectionManager collectionManager, Console console) {
        super("update", "обновляет работника по id на основе заданного работника", console);
        this.collectionManager = collectionManager;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException, NonExistentId {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
        if (!collectionManager.existsId(Integer.parseInt(args[0]))) {
            throw new NonExistentId();
        }
    }

    @Override
    public void execute(String[] args) {
        collectionManager.update(Integer.parseInt(args[0]), worker);
    }
}

package commands;

import exceptions.WrongCommandArgsException;
import managers.Console;
import models.Worker;
import managers.CollectionManager;
import exceptions.NotUniqueIdException;

/**
 * Команда add
 */
public class Add extends CommandWithWorker {
    private Worker worker;
    private CollectionManager collectionManager;

    public Add(CollectionManager collectionManager, Console console) {
        super("add", "добавляет элемент в коллекцию", console);
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
     * Добавляет работника в коллекцию
     */
    public void execute(String[] args) {
        try {
            collectionManager.add(worker);
        } catch (NotUniqueIdException e) {
            console.write(e.toString());
        }
    }
}

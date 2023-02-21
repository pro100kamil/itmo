package commands;

import exceptions.WrongCommandArgsException;
import models.Worker;
import managers.CollectionManager;
import exceptions.NotUniqueIdException;

/**
 * Команда add
 */
public class Add extends CommandWithWorker {
    private Worker worker;
    private CollectionManager collectionManager;

    public Add(CollectionManager collectionManager) {
        super("add", "добавляет элемент в коллекцию");
        this.collectionManager = collectionManager;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Добавляет работника в коллекцию
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.add(worker);
        } catch (NotUniqueIdException | WrongCommandArgsException e) {
            System.out.println(e);
        }
    }
}

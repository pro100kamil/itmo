package commands;

import exceptions.NotUniqueIdException;
import exceptions.WrongCommandArgsException;
import managers.ValidateManager;
import models.Worker;
import managers.CollectionManager;

/**
 * Команда update
 */
public class Update extends CommandWithWorker {
    private Worker worker;
    private CollectionManager collectionManager;

    public Update(CollectionManager collectionManager) {
        super("update", "обновляет работника по id на основе заданного работника");
        this.collectionManager = collectionManager;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    /**
     * Обновляет работника по id на основе заданное работника
     */
    public void execute(String[] args) {
        try {
            if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
                throw new WrongCommandArgsException();
            }
            Integer id = Integer.parseInt(args[0]);
            collectionManager.update(id, worker);
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }

}

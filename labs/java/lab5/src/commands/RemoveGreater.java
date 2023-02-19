package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда remove_greater
 */
public class RemoveGreater extends Command {
    private Worker elem;
    private CollectionManager collectionManager;

    public RemoveGreater(Worker elem, CollectionManager collectionManager) {
        super("remove_greater", "удаляет работников, которые больше заданного");
        this.elem = elem;
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет работников, которые больше заданного
     */
    public void execute() {
        try {
            collectionManager.removeGreater(elem);
        } catch (NullPointerException e) {

        }
    }

}

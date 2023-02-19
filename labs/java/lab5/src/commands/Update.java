package commands;

import models.Worker;
import managers.CollectionManager;

/**
 * Команда update
 */
public class Update extends Command {
    private Integer id;
    private Worker elem;
    private CollectionManager collectionManager;

    public Update(Integer id, Worker elem, CollectionManager collectionManager) {
        super("update", "обновляет работника по id на основе заданного работника");
        this.id = id;
        this.elem = elem;
        this.collectionManager = collectionManager;
    }

    /**
     * Обновляет работника по id на основе заданное работника
     */
    public void execute() {
        try {
            collectionManager.update(id, elem);
        } catch (NullPointerException e) {

        }
    }

}

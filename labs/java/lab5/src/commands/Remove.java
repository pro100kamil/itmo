package commands;

import managers.CollectionManager;

/**
 * Команда remove_by_id
 */
public class Remove extends Command {
    private Integer id;
    private CollectionManager collectionManager;

    public Remove(Integer id, CollectionManager collectionManager) {
        super("remove_by_id", "удаляет работника по id из коллекции");
        this.id = id;
        this.collectionManager = collectionManager;
    }

    /**
     * Удаляет работника по id из коллекции
     */
    public void execute() {
        try {
            collectionManager.remove(id);
        } catch (NullPointerException e) {

        }
    }


}

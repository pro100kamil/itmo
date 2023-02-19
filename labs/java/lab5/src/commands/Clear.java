package commands;

import managers.CollectionManager;

/**
 * Команда clear
 */
public class Clear extends Command {
    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager) {
        super("clear", "очищает коллекцию");
        this.collectionManager = collectionManager;
    }

    /**
     * Очищает коллекцию
     */
    public void execute() {
        try {
            collectionManager.clear();
        } catch (NullPointerException e) {

        }
    }

}

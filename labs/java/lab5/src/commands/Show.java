package commands;

import managers.CollectionManager;

/**
 * Команда show
 */
public class Show extends Command {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager) {
        super("show", "выводит элементы коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит элементы коллекции
     */
    public void execute() {
        try {
            collectionManager.printElements();
        } catch (NullPointerException e) {

        }
    }
}

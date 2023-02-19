package commands;

import managers.CollectionManager;

/**
 * Команда head
 */
public class Head extends Command {
    private CollectionManager collectionManager;

    public Head(CollectionManager collectionManager) {
        super("head", "выводит первый элемент коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит первый элемент коллекции
     *
     * @param collectionManager менеджер коллекции, отвечающий за основную коллекцию
     */
    public void execute() {
        if (collectionManager.isEmpty()) {
            System.out.println("Коллекция пустая, поэтому нет первого элемента!");
        } else {
            try {
                System.out.println(collectionManager.getHead());
            } catch (NullPointerException e) {

            }
        }
    }
}

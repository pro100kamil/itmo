package commands;

import exceptions.WrongCommandArgsException;
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
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            if (collectionManager.isEmpty()) {
                System.out.println("Коллекция пустая, поэтому нет первого элемента!");
            } else {
                System.out.println(collectionManager.getHead());
            }
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }
}

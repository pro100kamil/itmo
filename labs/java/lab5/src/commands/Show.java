package commands;

import exceptions.WrongCommandArgsException;
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
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printElements();
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }
}

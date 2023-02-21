package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;

/**
 * Команда info
 */
public class Info extends Command {
    private CollectionManager collectionManager;

    public Info(CollectionManager collectionManager) {
        super("info", "выводит информацию о коллекции");
        this.collectionManager = collectionManager;
    }

    /**
     * Выводит информацию о коллекции
     */
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.printInfo();
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }

}

package commands;

import exceptions.WrongCommandArgsException;
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
    public void execute(String[] args) {
        try {
            if (args.length != 0) {
                throw new WrongCommandArgsException();
            }
            collectionManager.clear();
        } catch (WrongCommandArgsException e) {
            System.out.println(e);
        }

    }

}

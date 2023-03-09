package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда clear
 */
public class Clear extends Command {

    private CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager, Console console) {
        super("clear", "очищает коллекцию", console);
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
            console.write(e.toString());
        }

    }

}

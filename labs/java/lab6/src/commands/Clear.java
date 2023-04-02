package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда clear.
 * Очищает коллекцию.
 */
public class Clear extends Command {

    private final CollectionManager collectionManager;

    public Clear(CollectionManager collectionManager, Console console) {
        super("clear", "очищает коллекцию", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            collectionManager.clear();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

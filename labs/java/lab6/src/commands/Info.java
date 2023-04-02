package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда info.
 * Выводит информацию о коллекции.
 */
public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(CollectionManager collectionManager, Console console) {
        super("info", "выводит информацию о коллекции", console);
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
            collectionManager.printInfo();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

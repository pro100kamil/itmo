package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда show.
 * Выводит элементы коллекции.
 */
public class Show extends Command {
    private CollectionManager collectionManager;

    public Show(CollectionManager collectionManager, Console console) {
        super("show", "выводит элементы коллекции", console);
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
            collectionManager.printElements();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;
import managers.ValidateManager;

/**
 * Команда remove_by_id id.
 * Удаляет работника по id из коллекции.
 */
public class Remove extends Command {
    private final CollectionManager collectionManager;

    public Remove(CollectionManager collectionManager, Console console) {
        super("remove_by_id", "удаляет работника по id из коллекции", console);
        this.collectionManager = collectionManager;
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 1 || !ValidateManager.isInteger(args[0])) {
            throw new WrongCommandArgsException();
        }
    }

    @Override
    public void execute(String[] args) {
        try {
            validateArgs(args);
            collectionManager.remove(Integer.parseInt(args[0]));
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

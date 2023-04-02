package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда head.
 * Выводит первый элемент коллекции.
 */
public class Head extends Command {
    private final CollectionManager collectionManager;

    public Head(CollectionManager collectionManager, Console console) {
        super("head", "выводит первый элемент коллекции", console);
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
            if (collectionManager.isEmpty()) {
                console.write("Коллекция пустая, поэтому нет первого элемента!");
            } else {
                console.write(collectionManager.getHead().toString());
            }
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

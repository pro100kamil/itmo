package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда print_descending.
 * Выводит элементы коллекции в порядке убывания (работники сортируются по размеру зарплаты).
 */
public class PrintDescending extends Command {
    private final CollectionManager collectionManager;

    public PrintDescending(CollectionManager collectionManager, Console console) {
        super("print_descending", "выводит элементы коллекции в порядке убывания", console);
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
            collectionManager.printDescending();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

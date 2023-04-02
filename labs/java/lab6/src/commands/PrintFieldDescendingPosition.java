package commands;

import exceptions.WrongCommandArgsException;
import managers.CollectionManager;
import managers.Console;

/**
 * Команда printFieldDescendingPosition.
 * Выводит значения поля position всех элементов в порядке убывания.
 */
public class PrintFieldDescendingPosition extends Command {
    private final CollectionManager collectionManager;

    public PrintFieldDescendingPosition(CollectionManager collectionManager, Console console) {
        super("print_field_descending_position", "выводит значения поля position всех элементов в порядке убывания", console);
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
            collectionManager.printFieldDescendingPosition();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

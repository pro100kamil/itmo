package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда printFieldDescendingPosition.
 * Выводит значения поля position всех элементов в порядке убывания.
 */
public class PrintFieldDescendingPosition extends ServerCommand {

    public PrintFieldDescendingPosition() {
        super("print_field_descending_position",
                "выводит значения поля position всех элементов в порядке убывания", false, true);
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

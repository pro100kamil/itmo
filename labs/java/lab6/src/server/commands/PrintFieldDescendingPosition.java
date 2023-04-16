package server.commands;

import common.exceptions.WrongCommandArgsException;
import server.commands.Command;

/**
 * Команда printFieldDescendingPosition.
 * Выводит значения поля position всех элементов в порядке убывания.
 */
public class PrintFieldDescendingPosition extends Command {

    public PrintFieldDescendingPosition() {
        super("print_field_descending_position", "выводит значения поля position всех элементов в порядке убывания");
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

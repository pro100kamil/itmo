package common.commands;

import common.exceptions.NotUniqueIdException;
import common.exceptions.WrongCommandArgsException;
import common.models.Worker;

/**
 * Команда add.
 * Добавляет работника в коллекцию.
 */
public class Add extends CommandWithWorker {

    public Add() {
        super("add", "добавляет элемент в коллекцию");
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
            collectionManager.add(worker);
        } catch (NotUniqueIdException e) {
            console.write(e.toString());
        }
    }
}

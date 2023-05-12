package server.commands;

import common.exceptions.NotUniqueIdException;
import common.exceptions.WrongCommandArgsException;

/**
 * Команда add.
 * Добавляет работника в коллекцию.
 */
public class Add extends ServerCommand {

    public Add() {
        super("add", "добавляет элемент в коллекцию", true, true);
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
            collectionManager.add(worker);
        } catch (NotUniqueIdException | WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

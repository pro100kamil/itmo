package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда info.
 * Выводит информацию о коллекции.
 */
public class Info extends ServerCommand {

    public Info() {
        super("info", "выводит информацию о коллекции", false, true);
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

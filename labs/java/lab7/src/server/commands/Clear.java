package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда clear.
 * Очищает коллекцию.
 */
public class Clear extends ServerCommand {
    public Clear() {
        super("clear", "очищает коллекцию", false, true);
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
            collectionManager.clear();
        } catch (WrongCommandArgsException e) {
            console.write(e.toString());
        }
    }
}

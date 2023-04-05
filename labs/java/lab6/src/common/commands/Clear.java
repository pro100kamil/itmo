package common.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда clear.
 * Очищает коллекцию.
 */
public class Clear extends Command {
    public Clear() {
        super("clear", "очищает коллекцию");
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

package server.commands;

import common.exceptions.WrongCommandArgsException;

/**
 * Команда remove_greater.
 * Удаляет работников, которые больше заданного.
 */
public class RemoveGreater extends CommandWithWorker {

    public RemoveGreater() {
        super("remove_greater", "удаляет работников, которые больше заданного");
    }

    @Override
    public void validateArgs(String[] args) throws WrongCommandArgsException {
        if (args.length != 0) {
            throw new WrongCommandArgsException();
        }
    }

    public void execute(String[] args) {
        collectionManager.removeGreater(worker);
    }
}
